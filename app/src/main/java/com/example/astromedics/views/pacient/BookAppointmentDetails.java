package com.example.astromedics.views.pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookAppointmentDetails extends AppCompatActivity {
    public static String MEDICAL_CONSULTATION = "MedicalConsultation";
    private Therapist therapist;
    private MedicalConsultation medicalConsultation;
    private Appointment appointment;

    private FloatingActionButton floatingActionButton;
    ImageView photoImageView;
    TextView emphasisTextView, therapistTextView, locationTextView, dateTextView, startDateTextView, endDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_details);
        obtainObjects();
        inflateViews();
        setViewsValues();
        setListeners();
    }

    private void obtainObjects() {
        medicalConsultation = (MedicalConsultation) getIntent().getSerializableExtra(MEDICAL_CONSULTATION);
        therapist = Repository.getInstance()
                              .getTherapistRepository()
                              .getTherapist(medicalConsultation);
        appointment = therapist.getAppointment(medicalConsultation);
    }

    private void inflateViews() {
        floatingActionButton = findViewById(R.id.book_appointment_details_action_button);
        photoImageView = findViewById(R.id.book_appointment_details_image);
        emphasisTextView = findViewById(R.id.book_appointment_details_emphasis);
        therapistTextView = findViewById(R.id.book_appointment_details_therapist_name);
        locationTextView = findViewById(R.id.book_appointment_details_location_name);
        dateTextView = findViewById(R.id.book_appointment_details_date);
        startDateTextView = findViewById(R.id.book_appointment_details_start_date);
        endDateTextView = findViewById(R.id.book_appointment_details_end_date);
    }

    private void setViewsValues() {
        new DownloadImageTask(photoImageView)
                .execute(therapist.getPhotoURL());
        emphasisTextView.setText(medicalConsultation.getEmphasis());
        therapistTextView.setText(therapist.getName());
        locationTextView.setText(medicalConsultation.getLocalization()
                                                    .getName());
        dateTextView.setText(new ApplicationDateFormat().toString(therapist.getAppointment(medicalConsultation)
                                                                           .getStartDate()));
        startDateTextView.setText(new ApplicationDateFormat().getHoursAndMinutes(therapist.getAppointment(medicalConsultation)
                                                                                          .getStartDate()));
        endDateTextView.setText(new ApplicationDateFormat().getHoursAndMinutes(therapist.getAppointment(medicalConsultation)
                                                                                        .getEndDate()));
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookAppointmentLocationDisplayActivity.class);
                intent.putExtra(BookAppointmentLocationDisplayActivity.LOCATION, medicalConsultation.getLocalization());
                startActivity(intent);
            }
        });

    }
}
