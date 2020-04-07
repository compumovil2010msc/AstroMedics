package com.example.astromedics.views.pacient;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.helpers.PermissionHandler;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookAppointmentDetails extends AppCompatActivity {
    private BookAppointmentDetails instance = this;
    public static String MEDICAL_CONSULTATION = "MedicalConsultation";
    private PermissionHandler permissionHandler = new PermissionHandler(this);
    protected static final int MY_LOCATION_PERMISSION = 503;

    private Therapist therapist;
    private MedicalConsultation medicalConsultation;

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
        emphasisTextView.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(), getApplicationContext()));
        therapistTextView.setText(therapist.getName());
        locationTextView.setText(medicalConsultation.getLocalization()
                                                    .getName());
        dateTextView.setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                     .getStartDate()));
        startDateTextView.setText(new ApplicationDateFormat().getHoursAndMinutes(medicalConsultation.getAppointment()
                                                                                                    .getStartDate()));
        endDateTextView.setText(new ApplicationDateFormat().getHoursAndMinutes(medicalConsultation.getAppointment()
                                                                                                  .getEndDate()));
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(instance,
                                                  new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                  MY_LOCATION_PERMISSION);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_PERMISSION:
                Intent intent = new Intent(getApplicationContext(),
                                           BookAppointmentLocationDisplayActivity.class);
                intent.putExtra(BookAppointmentLocationDisplayActivity.LOCATION,
                                medicalConsultation.getLocalization());
                startActivity(intent);
                break;
        }
    }
}
