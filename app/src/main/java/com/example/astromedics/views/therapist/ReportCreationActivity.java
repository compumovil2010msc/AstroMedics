package com.example.astromedics.views.therapist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.model.Evolution;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.views.pacient.BookAppointmentHistoryDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReportCreationActivity extends AppCompatActivity {

    private TextView emphasisTextView, writtenByTextView, infoTextView;
    private EditText contentTextView;
    private FloatingActionButton floatingActionButton;
    public static String MEDICAL_CONSULTATION = "medicalConsultation", THERAPIST = "therapist", TYPE = "type", TYPE_EVOLUTION = "evolution", TYPE_REPORT = "report";
    private String type;

    MedicalConsultation medicalConsultation;
    Therapist therapist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_creation);
        obtainObjects();
        inflateViews();
        setListeners();
        setInitialValues();
    }

    private void obtainObjects() {
        medicalConsultation = (MedicalConsultation) getIntent().getSerializableExtra(MEDICAL_CONSULTATION);
        therapist = (Therapist) getIntent().getSerializableExtra(THERAPIST);
        type = (String) getIntent().getSerializableExtra(TYPE);
    }

    private void inflateViews() {
        floatingActionButton = findViewById(R.id.report_creation_action_button);
        emphasisTextView = findViewById(R.id.report_creation_emphasis);
        writtenByTextView = findViewById(R.id.report_creation_written_by);
        infoTextView = findViewById(R.id.report_creation_info);
        contentTextView = findViewById(R.id.report_creation_content);
    }

    private void setInitialValues() {
        emphasisTextView.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                             getApplicationContext()));
        writtenByTextView.setText(therapist.getName());
        infoTextView.setText(type.equals(TYPE_EVOLUTION) ? getString(R.string.report_creation_info_evolution) : getString(R.string.report_creation_info_report));
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = contentTextView.getText()
                                                .toString();

                try {
                    if (content != null && !content.equals("")) {
                        if (type.equals(TYPE_EVOLUTION)) {
                            medicalConsultation = Repository.getInstance().getTherapistRepository().setEvolution(therapist, medicalConsultation, content);
                        } else {
                            medicalConsultation = Repository.getInstance().getTherapistRepository().setReport(therapist, medicalConsultation, content);
                        }

                        Intent intent = new Intent(getApplicationContext(),
                                                   ReportDetails.class);
                        intent.putExtra(ReportDetails.MEDICAL_CONSULTATION,
                                        medicalConsultation);
                        startActivity(intent);
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),
                                   ex.getMessage(),
                                   Toast.LENGTH_SHORT)
                         .show();
                }
            }
        });
    }
}
