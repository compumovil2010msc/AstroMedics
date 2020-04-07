package com.example.astromedics.views.general;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.interfaces.PersonRepository;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.repository.test.TestPersonRepository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.example.astromedics.views.therapist.HomeTherapist;

public class Login extends AppCompatActivity {
    private Button loginButton;
    private EditText emailEditText, passwordEditText;
    private PersonRepository personRepository = new TestPersonRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inflateViews();
        setListeners();
    }

    private void inflateViews() {
        emailEditText = findViewById(R.id.email_login);
        passwordEditText = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.button_authenticate);
    }

    private void setListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText()
                                            .toString();
                String password = passwordEditText.getText()
                                                  .toString();
                Person person = Repository.getInstance()
                                          .getPersonRepository()
                                          .login(email,
                                                 password);
                if (person == null){
                    Toast.makeText(getApplicationContext(),
                                   getString(R.string.activity_login_failed),
                                   Toast.LENGTH_SHORT)
                         .show();
                } else if(person instanceof Pacient){
                    Session.getInstance().setEmail(person.getEmail());
                    Intent intent = new Intent(view.getContext(),
                                               HomeUserActivity.class);
                    startActivity(intent);
                } else if(person instanceof Therapist){
                    Session.getInstance().setEmail(person.getEmail());
                    Intent intent = new Intent(view.getContext(),
                                               HomeTherapist.class);
                    startActivity(intent);
                }
            }
        });
    }
}
