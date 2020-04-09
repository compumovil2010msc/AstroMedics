package com.example.astromedics.views.pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.common.TherapistDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookAppointmentHistoryDetails extends AppCompatActivity {
    private BookAppointmentHistoryDetails instance = this;
    public static String MEDICAL_CONSULTATION = "MedicalConsultation";

    private Therapist therapist;
    private MedicalConsultation medicalConsultation;
    private boolean currentlySaving = false;

    private FloatingActionButton floatingActionButton;
    EditText calificationEditText;
    TextView emphasisTextView, therapistTextView, locationTextView, dateTextView, startDateTextView, endDateTextView, calificationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_history_details);
        obtainObjects();
        inflateViews();
        setViewsValues();
        setListeners();
    }

    private void obtainObjects() {
        medicalConsultation = (MedicalConsultation) getIntent().getSerializableExtra(MEDICAL_CONSULTATION);
        try {
            therapist = Repository.getInstance()
                                  .getTherapistRepository()
                                  .getTherapist(medicalConsultation);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                           ex.getMessage(),
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }

    private void inflateViews() {
        floatingActionButton = findViewById(R.id.book_appointment_history_details_action_button);
        emphasisTextView = findViewById(R.id.book_appointment_history_details_emphasis);
        therapistTextView = findViewById(R.id.book_appointment_history_details_therapist_name);
        locationTextView = findViewById(R.id.book_appointment_history_details_location_name);
        dateTextView = findViewById(R.id.book_appointment_history_details_date);
        startDateTextView = findViewById(R.id.book_appointment_history_details_start_date);
        endDateTextView = findViewById(R.id.book_appointment_history_details_end_date);
        calificationTextView = findViewById(R.id.book_appointment_history_details_calification);
        calificationEditText = findViewById(R.id.book_appointment_history_details_calification_editable);
    }

    private void setViewsValues() {
        emphasisTextView.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                             getApplicationContext()));
        if (therapist != null) {
            therapistTextView.setText(therapist.getName());
        }
        locationTextView.setText(medicalConsultation.getLocalization()
                                                    .getName());
        dateTextView.setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                     .getStartDate()));
        startDateTextView.setText(new ApplicationDateFormat().getHoursAndMinutes(medicalConsultation.getAppointment()
                                                                                                    .getStartDate()));
        endDateTextView.setText(new ApplicationDateFormat().getHoursAndMinutes(medicalConsultation.getAppointment()
                                                                                                  .getEndDate()));

        if (medicalConsultation.getCalification() > 0) {
            calificationTextView.setVisibility(View.VISIBLE);
            calificationTextView.setText(String.valueOf(medicalConsultation.getCalification()));

            if (medicalConsultation.getReport() != null) {
                floatingActionButton.setVisibility(View.VISIBLE);
            }
        } else {
            calificationEditText.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_save_black_24dp));
            currentlySaving = true;
        }
    }

    private void setListeners() {
        therapistTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                                           TherapistDetails.class);
                intent.putExtra(TherapistDetails.THERAPIST,
                                therapist);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentlySaving) {

                    if (calificationEditText.getText() != null && !calificationEditText.getText()
                                                                                       .toString()
                                                                                       .equals("")) {
                        double calification = Double.parseDouble(calificationEditText.getText()
                                                                                     .toString());
                        calification = Double.parseDouble(String.format("%.2f",
                                                                        calification));
                        try {
                            medicalConsultation = Repository.getInstance()
                                                            .getPacientRepository()
                                                            .setCalification(
                                                                    Repository.getInstance()
                                                                              .getPacientRepository()
                                                                              .getPacient(Session.getInstance()
                                                                                                 .getEmail()),
                                                                    medicalConsultation,
                                                                    calification);
                            Intent intent = new Intent(getApplicationContext(),
                                                       BookAppointmentHistoryDetails.class);
                            intent.putExtra(BookAppointmentHistoryDetails.MEDICAL_CONSULTATION,
                                            medicalConsultation);
                            startActivity(intent);
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(),
                                           ex.getMessage(),
                                           Toast.LENGTH_SHORT)
                                 .show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                       getString(R.string.book_appointment_history_fill_the_fields),
                                       Toast.LENGTH_SHORT)
                             .show();
                    }
                } else if (medicalConsultation.getReport() != null) {
                    Intent intent = new Intent(getApplicationContext(),
                                               ReportVisualizationActivity.class);
                    intent.putExtra(ReportVisualizationActivity.MEDICAL_CONSULTATION,
                                    medicalConsultation);
                    intent.putExtra(ReportVisualizationActivity.THERAPIST,
                                    therapist);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(getApplicationContext(),
                                      HomeUserActivity.class);
        startActivity(setIntent);
    }
}
