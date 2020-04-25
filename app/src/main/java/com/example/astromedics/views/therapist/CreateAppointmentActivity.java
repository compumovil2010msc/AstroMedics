package com.example.astromedics.views.therapist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class CreateAppointmentActivity extends AppCompatActivity {
    private FloatingActionButton button;
    private Button acceptedTime1Button, acceptedTime2Button;
    private Date selectedDate, startTime, endTime;
    private EditText dateEditText, startTimeEditText, endTimeEditText;
    private CardView calendarCardView, timePicker1CardView, timePicker2CardView;
    private TimePicker timePicker1, timePicker2;
    private CalendarView calendarView;
    private Time time;
    ConstraintLayout constraintLayout;
    private int selectedYear, selectedMonth, selectedDay, startHour, endHour, startMinutes, endMinutes;

    enum Time {
        START,
        END
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
        inflateViews();
        addListeners();
    }

    private void inflateViews() {
        constraintLayout = findViewById(R.id.create_appointment_layout);
        dateEditText = findViewById(R.id.create_appointment_date);
        startTimeEditText = findViewById(R.id.create_appointment_start_date);
        endTimeEditText = findViewById(R.id.create_appointment_end_hour);
        button = findViewById(R.id.create_appointment_confirm);
        calendarView = findViewById(R.id.create_appointment_calendar_view);
        calendarCardView = findViewById(R.id.create_appointment_calendar_card_view);
        timePicker1 = findViewById(R.id.create_appointment_time_picker1);
        timePicker1CardView = findViewById(R.id.create_appointment_time_picker_card_view1);
        timePicker2 = findViewById(R.id.create_appointment_time_picker2);
        timePicker2CardView = findViewById(R.id.create_appointment_time_picker_card_view2);
        acceptedTime1Button = findViewById(R.id.create_appointment_accept_time1);
        acceptedTime2Button = findViewById(R.id.create_appointment_accept_time2);
    }

    private void addListeners() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Date pickedDate = new ApplicationDateFormat().createDate(year,
                                                                         month + 1,
                                                                         day);
                selectedDate = pickedDate;
                dateEditText.setText(new ApplicationDateFormat().toString(selectedDate));
                calendarCardView.setVisibility(View.INVISIBLE);
                selectedYear = year;
                selectedMonth = month + 1;
                selectedDay = day;
            }
        });

        acceptedTime1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker1CardView.setVisibility(View.INVISIBLE);
            }
        });

        acceptedTime2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker2CardView.setVisibility(View.INVISIBLE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long differenceInMinutes = (endTime.getTime() - startTime.getTime()) / 1000 / 60;

                if (differenceInMinutes >= 60 && differenceInMinutes <= 180) {
                    if (selectedDate != null) {
                        try {
                            Date appointmentStartDate = new ApplicationDateFormat().createDate(selectedYear,
                                                                                               selectedMonth,
                                                                                               selectedDay,
                                                                                               startHour,
                                                                                               startMinutes);
                            Date appointmentEndDate = new ApplicationDateFormat().createDate(selectedYear,
                                                                                             selectedMonth,
                                                                                             selectedDay,
                                                                                             endHour,
                                                                                             endMinutes);

                            Repository.getInstance()
                                      .getTherapistRepository()
                                      .createAppointment(Repository.getInstance()
                                                                   .getTherapistRepository()
                                                                   .getTherapist(Session.getInstance()
                                                                                        .getEmail()),
                                                         appointmentStartDate,
                                                         appointmentEndDate);
                            Intent setIntent = new Intent(getApplicationContext(),
                                                          HomeTherapist.class);
                            startActivity(setIntent);
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(),
                                           ex.getMessage(),
                                           Toast.LENGTH_SHORT)
                                 .show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                       getString(R.string.create_appointmente_invalid_date),
                                       Toast.LENGTH_SHORT)
                             .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                                   getString(R.string.create_appointmente_invalid_time),
                                   Toast.LENGTH_SHORT)
                         .show();
                }
            }
        });

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minutes) {
                ApplicationDateFormat applicationDateFormat = new ApplicationDateFormat();
                startTime = applicationDateFormat.createDate(hour,
                                                             minutes);
                startHour = hour;
                startMinutes = minutes;
                endTimeEditText.setEnabled(true);
                startTimeEditText.setText(applicationDateFormat.getHoursAndMinutes(startTime));
            }
        });

        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minutes) {
                ApplicationDateFormat applicationDateFormat = new ApplicationDateFormat();
                endTime = applicationDateFormat.createDate(hour,
                                                           minutes);
                endHour = hour;
                endMinutes = minutes;
                endTimeEditText.setText(applicationDateFormat.getHoursAndMinutes(endTime));
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setMinDate(0);
                calendarView.setMinDate(new Date().getTime());
                calendarCardView.setVisibility(View.VISIBLE);
            }
        });

        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = Time.START;
                timePicker1CardView.setVisibility(View.VISIBLE);
            }
        });

        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = Time.END;
                timePicker2CardView.setVisibility(View.VISIBLE);
            }
        });
    }
}
