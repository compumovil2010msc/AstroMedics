package com.example.astromedics.views.pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Localization;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class TherapistFilterActivity extends AppCompatActivity {
    public static String LOCATION = "localization";
    private Localization localization;
    private FloatingActionButton button;
    private EditText locationNameEditText, startDateEditText, endDateEditText;
    private CardView calendarCardView;
    private CalendarView calendarView;
    private TherapistDate therapistDate;
    private Date startDate, endDate;

    enum TherapistDate {
        START_DATE,
        END_DATE
    }

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

    private void inflateViews() {
        locationNameEditText = findViewById(R.id.therapist_filter_location_name);
        startDateEditText = findViewById(R.id.therapist_filter_start_date);
        endDateEditText = findViewById(R.id.therapist_filter_end_date);
        button = findViewById(R.id.therapist_filter_confirmFilters);
        calendarView = findViewById(R.id.therapist_filter_calendar_view);
        calendarCardView = findViewById(R.id.therapist_filter_caldendar_card_view);
    }

    private void addListeners() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Date selectedDate = new ApplicationDateFormat().createDate(year, month + 1, day + 1);

                if (therapistDate == TherapistDate.START_DATE) {
                    calendarView.setDate(selectedDate.getTime());
                    startDate = selectedDate;
                    endDate = null;
                    startDateEditText.setText(new ApplicationDateFormat().toString(startDate));
                    endDateEditText.setText("");
                    endDateEditText.setEnabled(true);
                    calendarCardView.setVisibility(View.INVISIBLE);
                } else {
                    endDate = selectedDate;
                    endDateEditText.setText(new ApplicationDateFormat().toString(endDate));
                    calendarCardView.setVisibility(View.INVISIBLE);
                }
            }
        });

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setMinDate(0);
                calendarView.setMinDate(new Date().getTime());
                calendarCardView.setVisibility(View.VISIBLE);
                therapistDate = TherapistDate.START_DATE;
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setMinDate(0);
                calendarView.setMinDate(startDate.getTime());
                calendarCardView.setVisibility(View.VISIBLE);
                therapistDate = TherapistDate.END_DATE;
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

    private void setInitialValues() {
        locationNameEditText.setText(localization.getName());
    }
}
