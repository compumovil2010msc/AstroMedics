package com.example.astromedics.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.example.astromedics.views.therapist.HomeTherapist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChangePasswordActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    EditText currentPasswordEditText, newPasswordEditText, repeatNewPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        inflateViews();
        setListeners();
    }

    private void inflateViews() {
        floatingActionButton = findViewById(R.id.change_password_floating_action_button);
        currentPasswordEditText = findViewById(R.id.change_password_current_password);
        newPasswordEditText = findViewById(R.id.change_password_new_password);
        repeatNewPasswordEditText = findViewById(R.id.change_password_repeat_new_password);
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordEditText.getText()
                                                                .toString();
                String newPassword = newPasswordEditText.getText()
                                                        .toString();
                String repeatNewPassword = repeatNewPasswordEditText.getText()
                                                                    .toString();

                if (!currentPassword.equals("")
                        && !newPassword.equals("")
                        && !repeatNewPassword.equals("")) {
                    try {
                        Person person = Repository.getInstance()
                                                  .getPersonRepository()
                                                  .get(Session.getInstance()
                                                              .getEmail());

                        Repository.getInstance()
                                  .getPersonRepository()
                                  .changePassword(person,
                                                  currentPassword,
                                                  newPassword,
                                                  repeatNewPassword);

                        if (person instanceof Pacient) {
                            Intent setIntent = new Intent(getApplicationContext(),
                                                          HomeUserActivity.class);
                            startActivity(setIntent);
                        } else {
                            Intent setIntent = new Intent(getApplicationContext(),
                                                          HomeTherapist.class);
                            startActivity(setIntent);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(),
                                       ex.getMessage(),
                                       Toast.LENGTH_SHORT)
                             .show();
                    }
                }
            }
        });
    }
}
