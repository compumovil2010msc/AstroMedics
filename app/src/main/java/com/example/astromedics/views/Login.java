package com.example.astromedics.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.astromedics.R;

public class Login extends AppCompatActivity {

    private Button iniciarSesion;
    private EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        iniciarSesion = findViewById(R.id.button_authenticate);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                if(user.equals("paciente@gmail.com")) {
                    Intent intent=new Intent(view.getContext(),HomeUser.class);
                    startActivity(intent);
                } else if (user.equals("terapeuta@gmail.com")){

                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo realizar la autenticacion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
