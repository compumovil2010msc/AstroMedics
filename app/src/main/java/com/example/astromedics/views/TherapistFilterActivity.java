package com.example.astromedics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.astromedics.R;
import com.example.astromedics.views.fragments.DatePickerFragment;

import java.util.Calendar;

public class TherapistFilterActivity extends AppCompatActivity {

    EditText etPlannedDate;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_filter);

         etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
         etPlannedDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 switch (view.getId()) {
                     case R.id.etPlannedDate:
                         showDatePickerDialog();
                         break;
                 }
             }
         });
     }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etPlannedDate.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
