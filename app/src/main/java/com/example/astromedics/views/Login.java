package com.example.astromedics.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.astromedics.App;
import com.example.astromedics.R;
import com.example.astromedics.model.Person;
import com.example.astromedics.services.UserService;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.util.SharedPreferencesUtils;
import com.example.astromedics.util.TokenService;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.example.astromedics.views.therapist.HomeTherapist;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener, DialogLogin.NoticeDialogListener {

    private EditText username, password;

    private static final int GOOGLE_INTENT_CODE = 2;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private Person personToCreateGoolge;
    private final String LOG_TAG = "LOGIN";
    private FirebaseAuth mAuth;
    private Button loginWithFirebase;
    private Button loginWithGoogle;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAuth=FirebaseAuth.getInstance();
        this.userService=App.get().getUserService();
        setContentView(R.layout.activity_login);
        this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(this,
                                                          this.gso);

        username = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        this.loginWithFirebase = findViewById(R.id.login_button);
        this.loginWithGoogle = findViewById(R.id.login_button_google);
        this.loginWithFirebase.setOnClickListener(this);
        this.loginWithGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_google:
                signInGoogle();
                break;
            case R.id.login_button:
                loginFirebase();
                break;
        }
    }

    private void signInGoogle() {
        Intent intent = this.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,
                               GOOGLE_INTENT_CODE);
    }

    private void loginFirebase() {
        String username = this.username.getText()
                                       .toString();
        String password = this.password.getText()
                                       .toString();
        if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(this,
                           "Invalid fields",
                           Toast.LENGTH_LONG)
                 .show();
            return;
        }
        if (username.isEmpty()) {
            Toast.makeText(this,
                           "Username required",
                           Toast.LENGTH_LONG)
                 .show();
        }
        if (password.isEmpty()) {
            Toast.makeText(this,
                           "Password required",
                           Toast.LENGTH_LONG)
                 .show();
        }
        this.mAuth.signInWithEmailAndPassword(username,
                                              password)
                  .addOnCompleteListener(this,
                                         new OnCompleteListener<AuthResult>() {
                                             @Override
                                             public void onComplete(@NonNull Task<AuthResult> task) {
                                                 if (task.isSuccessful()) {
                                                     Log.d(LOG_TAG,
                                                           "User logged in with firebase auth correctly");
                                                     FirebaseUser user = mAuth.getCurrentUser();
                                                     Log.d(LOG_TAG,
                                                           "Email logged in: " + user.getEmail());
                                                     getUserFromDbAndRedirect(user.getEmail());
                                                 } else {
                                                     Toast.makeText(Login.this,
                                                                    "Authentication failed",
                                                                    Toast.LENGTH_LONG)
                                                          .show();
                                                 }
                                             }
                                         });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,
                               resultCode,
                               data);
        switch (requestCode) {
            case GOOGLE_INTENT_CODE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            homologateLoginFirebase(GoogleAuthProvider.getCredential(account.getIdToken(),
                                                                     null));
        } catch (ApiException e) {
            Log.w(LOG_TAG,
                  "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void homologateLoginFirebase(AuthCredential credential) {
        this.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i(LOG_TAG,"Credentials homologated");
                            Login.this.userService.getUser(mAuth.getCurrentUser().getEmail()).subscribe(person -> {
                                if(personToCreateGoolge==null){
                                    personToCreateGoolge=new Person(mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getDisplayName());
                                    showDialogDoctorAndCreateUser();
                                }else{
                                    redirectAndPersistLocal(personToCreateGoolge);
                                }
                            },err->{
                                Log.e(LOG_TAG,"Error fetching user: "+err.getMessage());
                            });
                        }else{
                            Log.i(LOG_TAG,"Fail Homologatig creds");
                        }
                    }
                });
    }

    private void showDialogDoctorAndCreateUser() {
        DialogLogin login = new DialogLogin();
        login.show(getSupportFragmentManager(),
                   "loginDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        personToCreateGoolge.setDoctor(true);
        insertUser();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        personToCreateGoolge.setDoctor(false);
        insertUser();
    }

    private void insertUser() {
        TokenService.getTokenObservable().subscribe(token->{
            SharedPreferencesUtils.persistPref("token",token);
            this.userService.saveUser(personToCreateGoolge,token).subscribe(res->{
                Log.i(LOG_TAG,"Insert successfull");
                redirectAndPersistLocal(personToCreateGoolge);
            },err->{
                Log.e(LOG_TAG,"Insert Failure "+err.getMessage());
            });
        },err->{
            Log.e(LOG_TAG,"Error getting token "+err.getMessage());
        });
    }

    private void getUserFromDbAndRedirect(String email) {

    /*
		this.userService.getUser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(person -> {
            if (person != null) {
                Log.i(LOG_TAG,"User found: "+person.toString());
                redirectAndPersistLocal(person);
            }
        },error ->{
            Log.e(LOG_TAG, "error getting user: "+error.getMessage());
        });
    */
        try {
            Person person = Repository.getInstance()
                                      .getPersonRepository()
                                      .get(Session.getInstance().getEmail());
            if (person instanceof Therapist) {
                Intent intent = new Intent(getApplicationContext(),
                                           HomeTherapist.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(),
                                           HomeUserActivity.class);
                startActivity(intent);
            }
        } catch (Exception ex) {
            Toast.makeText(Login.this,
                           "Error en autenticacion",
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }

    private void redirectAndPersistLocal(Person personFromDataBase) {
        SharedPreferencesUtils.persistPref("userLoggedIn",
                                           personFromDataBase,
                                           this);
        if (personFromDataBase.isDoctor()) {
            Intent intent = new Intent(this,
                                       HomeTherapist.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this,
                                       HomeUserActivity.class);
            startActivity(intent);
        }
    }

}
