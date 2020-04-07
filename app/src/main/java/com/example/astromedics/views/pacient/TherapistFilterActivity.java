package com.example.astromedics.views.pacient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.model.Localization;
import com.example.astromedics.views.fragments.DatePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TherapistFilterActivity extends AppCompatActivity {
    public static String LOCATION = "localization";
    private Localization localization;
    EditText startDate, endDate;
    private FloatingActionButton button;
    private EditText locationNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_filter);
        obtainObjects();
        inflateViews();
        addListeners();
        setInitialValues();
    }

    private void obtainObjects() {
        localization = (Localization) getIntent().getSerializableExtra(LOCATION);
    }

    private void inflateViews(){
        locationNameEditText = findViewById(R.id.therapist_filter_location_name);
        button = findViewById(R.id.therapist_filter_confirmFilters);
        startDate = findViewById(R.id.therapist_filter_startDate);
        endDate = findViewById(R.id.therapist_filter_endDate);
    }

    private void addListeners(){
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(startDate);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(endDate);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),
                                           TherapistSearchResultActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setInitialValues(){
        locationNameEditText.setText(localization.getName());
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                editText.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(),
                         "datePicker");
    }

}
