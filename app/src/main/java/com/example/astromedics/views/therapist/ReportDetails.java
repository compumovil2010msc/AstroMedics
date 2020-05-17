package com.example.astromedics.views.therapist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.views.common.TherapistDetails;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReportDetails extends AppCompatActivity {
    private ReportDetails instance = this;
    public static String MEDICAL_CONSULTATION = "MedicalConsultation";

    private Therapist therapist;
    private Pacient pacient;
    private MedicalConsultation medicalConsultation;

    private FloatingActionButton floatingActionButton;
    TextView pacientNameTextView, emphasisTextView, therapistTextView, locationTextView, dateTextView, startDateTextView, endDateTextView, calificationTextView,
            evolutionCreationDate, evolutionContent, evolutionEmphasis, reportCreationDate, reportContent, reportEmphasis;
    CardView evolutionCardView, reportCardView;
    private String creationType;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);
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
            pacient = Repository.getInstance()
                                .getPacientRepository()
                                .getPacient(medicalConsultation);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                           ex.getMessage(),
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }

    private void inflateViews() {
        floatingActionButton = findViewById(R.id.report_details_action_button);
        emphasisTextView = findViewById(R.id.report_details_emphasis);
        therapistTextView = findViewById(R.id.report_details_therapist_name);
        pacientNameTextView = findViewById(R.id.report_details_pacient_name);
        locationTextView = findViewById(R.id.report_details_location_name);
        dateTextView = findViewById(R.id.report_details_date);
        startDateTextView = findViewById(R.id.report_details_start_date);
        endDateTextView = findViewById(R.id.report_details_end_date);
        calificationTextView = findViewById(R.id.report_details_calification);
        evolutionCardView = findViewById(R.id.cardViewEvolution);
        reportCardView = findViewById(R.id.cardViewReport);
        evolutionCreationDate = findViewById(R.id.report_details_evolution_visualization_report_creation_date);
        evolutionContent = findViewById(R.id.report_details_evolution_visualization_content);
        evolutionEmphasis = findViewById(R.id.report_details_evolution_visualization_emphasis);
        reportCreationDate = findViewById(R.id.report_details_report_visualization_report_creation_date);
        reportContent = findViewById(R.id.report_details_report_visualization_content);
        reportEmphasis = findViewById(R.id.report_details_report_visualization_emphasis);
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

        pacientNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                                           PacientDetails.class);
                intent.putExtra(PacientDetails.PACIENT,
                                pacient);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                                           ReportCreationActivity.class);
                intent.putExtra(ReportCreationActivity.MEDICAL_CONSULTATION,
                                medicalConsultation);
                intent.putExtra(ReportCreationActivity.THERAPIST,
                                therapist);
                intent.putExtra(ReportCreationActivity.TYPE,
                                creationType);
                startActivity(intent);
            }
        });
    }

    private void setViewsValues() {
        emphasisTextView.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                             getApplicationContext()));
        if (therapist != null) {
            therapistTextView.setText(therapist.getName());
        }

        if (pacient != null) {
            pacientNameTextView.setText(pacient.getName());
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
            calificationTextView.setText(String.valueOf(medicalConsultation.getCalification()));
        } else {
            calificationTextView.setText(getString(R.string.report_details_no_calification));
        }

        if (medicalConsultation.getEvolution() != null) {
            evolutionCardView.setVisibility(View.VISIBLE);
            evolutionCreationDate.setText(new ApplicationDateFormat().toString(medicalConsultation.getEvolution()
                                                                                                  .getCreationDate()));
            evolutionContent.setText(medicalConsultation.getEvolution()
                                                        .getContent());
            evolutionEmphasis.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                                  getApplicationContext()));
        }

        if (medicalConsultation.getReport() != null) {
            reportCardView.setVisibility(View.VISIBLE);
            reportCreationDate.setText(new ApplicationDateFormat().toString(medicalConsultation.getReport()
                                                                                               .getCreationDate()));
            reportContent.setText(medicalConsultation.getReport()
                                                     .getContent());
            reportEmphasis.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                               getApplicationContext()));
        }

        if(medicalConsultation.getEvolution() == null){
            floatingActionButton.setVisibility(View.VISIBLE);
            this.creationType = ReportCreationActivity.TYPE_EVOLUTION;
        } else if (medicalConsultation.getReport() == null){
            floatingActionButton.setVisibility(View.VISIBLE);
            this.creationType = ReportCreationActivity.TYPE_REPORT;
        }
    }


    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(getApplicationContext(),
                                      HomeTherapist.class);
        startActivity(setIntent);
    }
}
