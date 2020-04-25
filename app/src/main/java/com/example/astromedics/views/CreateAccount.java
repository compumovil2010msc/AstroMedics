package com.example.astromedics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.astromedics.App;
import com.example.astromedics.R;
import com.example.astromedics.model.Person;
import com.example.astromedics.services.UserService;
import com.example.astromedics.util.SharedPreferencesUtils;
import com.example.astromedics.util.TokenService;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.example.astromedics.views.therapist.HomeTherapist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password_1;
    private EditText password_2;
    private EditText name;
    private Button buttonCorreoPass;
    private CheckBox checkBoxAsMedico;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        this.userService=App.get().getUserService();
        this.mAuth=FirebaseAuth.getInstance();
        this.name=findViewById(R.id.name_register);
        this.buttonCorreoPass=findViewById(R.id.register_email_pass);
        this.checkBoxAsMedico=findViewById(R.id.check_soy_medico);
        this.email=findViewById(R.id.email_register);
        this.password_1=findViewById(R.id.password_register_1);
        this.password_2 =findViewById(R.id.password_register_2);

        this.buttonCorreoPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_email_pass:
                registerEmailPass();
                break;
        }
    }

    private void registerEmailPass() {
        final String email=this.email.getText().toString();
        String pass_1=this.password_1.getText().toString();
        String pass_2=this.password_2.getText().toString();
        final String name=this.name.getText().toString();
        if(email.isEmpty() || pass_1.isEmpty() || pass_2.isEmpty() || name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Información incompleta, verificar", Toast.LENGTH_LONG).show();
            return;
        }
        if(!pass_1.contentEquals(pass_2)){
            Toast.makeText(getApplicationContext(), "Contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return;
        }
        this.mAuth.createUserWithEmailAndPassword(email,pass_1).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Registro exitoso!", Toast.LENGTH_LONG).show();
                            Person p =new Person(email,name,checkBoxAsMedico.isChecked());
                            createPersonBackend(p);
                        }else{
                            Toast.makeText(getApplicationContext(), "El registro ha fallado", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }

    private void createPersonBackend(Person p){
        TokenService.getTokenObservable().subscribe(token->{
            Log.i("CREATE_USER","Token received: "+token);
            SharedPreferencesUtils.persistPref("token",token);
            this.userService.saveUser(p,token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(res->{
                Log.i("CREATE_USER","Insert successfull");
                SharedPreferencesUtils.persistPref("userLoggedIn",p);
                redirect(p.isDoctor());
            },err->{
                Log.i("CREATE_USER","Insert failure: "+err.getMessage());
            });
        });
    }

    private void redirect(boolean doctor) {
        if(doctor){
            Intent intent=new Intent(this, HomeTherapist.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this, HomeUserActivity.class);
            startActivity(intent);
        }
    }

}
