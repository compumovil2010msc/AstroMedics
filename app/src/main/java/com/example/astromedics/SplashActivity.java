package com.example.astromedics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.astromedics.model.Person;
import com.example.astromedics.services.UserService;
import com.example.astromedics.util.SharedPreferencesUtils;
import com.example.astromedics.views.HomeTherapist;
import com.example.astromedics.views.HomeUser;
import com.example.astromedics.views.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAuth=FirebaseAuth.getInstance();
        Intent intentMain=new Intent(this, MainActivity.class);

        Log.i("SPLASH_ACT",this.mAuth.getCurrentUser()==null?"NOT USER LOGGED":"USER LOOGED: "+this.mAuth.getCurrentUser().getEmail());
        if(this.mAuth.getCurrentUser()!=null){
            verifyData(this.mAuth.getCurrentUser().getEmail());
        }else{
            startActivity(intentMain);
        }
    }

    private void verifyData(String email){
        if(!SharedPreferencesUtils.hasPreference("userLoggedIn",this)){
            Call<Person> call=App.get().getUserService().getUser(email);
            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if(response.body()!=null){
                        redirect(response.body().isDoctor());
                    }
                }
                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.e("SPLASH_ACT", "error getting user: "+t.getMessage());
                }
            });
        }else{
            Person pInShared=SharedPreferencesUtils.getSharedPref("userLoggedIn",Person.class,this);
            Log.i("SPLASH_ACT","Splash user in shared preferences: "+pInShared==null?"null":pInShared.toString());
            redirect(pInShared.isDoctor());
        }
    }

    private void redirect(boolean doctor) {
        if(doctor){
            Intent intent=new Intent(this, HomeTherapist.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this,HomeUser.class);
            startActivity(intent);
        }
    }
}
