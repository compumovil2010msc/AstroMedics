package com.example.astromedics.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;

public class AccountTypeSelection extends AppCompatActivity {

    Button pacientButton, therapistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type_selection);
        inflateViews();
        addListeners();
    }

    private void inflateViews() {
        pacientButton = findViewById(R.id.activity_account_type_pacient_button);
        therapistButton = findViewById(R.id.activity_account_type_therapist_button);
    }

    private void addListeners() {
        pacientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                                           CreatePacientActivity.class);
                startActivity(intent);
            }
        });
        therapistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                                           CreateTherapistActivity.class);
                startActivity(intent);
            }
        });
    }
}
