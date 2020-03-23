package com.example.astromedics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.astromedics.R;
import com.example.astromedics.views.fragments.DatePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class TherapistFilterActivity extends AppCompatActivity {
    EditText startDate, endDate ;
    private FloatingActionButton button;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_filter);

         button = findViewById(R.id.confirmFilters);
         startDate = findViewById(R.id.startDate);
         endDate = findViewById(R.id.endDate);

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
                 Intent intent=new Intent(v.getContext(), TherapistSearchResultActivity.class);
                 startActivity(intent);
             }
         });
     }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month+1) + " / " + year;
                editText.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
