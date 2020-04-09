package com.example.astromedics.views.therapist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.MedicalConsultationReportAdapter;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.views.common.BookAppointmentDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PacientDetails extends AppCompatActivity {
    public static String PACIENT = "paciente";

    List<MedicalConsultation> medicalConsultations;
    Pacient pacient;
    ImageView photoImageView;
    CardView medicalHistoryCardView;
    RecyclerView medicalConsultationRecyclerView;
    TextView titleTextView, nameTextView, emailTextView, phoneNumberTextView, cellNumberTextView, addressTextView, memberSinceTextView, bloodTypeTextView, heightTextView, weightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient_details);
        obtainObjects();
        inflateViews();
        getMedicalConsultations();
        setViewsValues();
    }

    private void obtainObjects() {
        pacient = (Pacient) getIntent().getSerializableExtra(PACIENT);
    }

    private void inflateViews() {
        photoImageView = findViewById(R.id.pacient_details_photo);
        titleTextView = findViewById(R.id.pacient_details_title);
        nameTextView = findViewById(R.id.pacient_details_name);
        emailTextView = findViewById(R.id.pacient_details_email);
        phoneNumberTextView = findViewById(R.id.pacient_details_phone_number);
        cellNumberTextView = findViewById(R.id.pacient_details_cell_number);
        addressTextView = findViewById(R.id.pacient_details_address);
        memberSinceTextView = findViewById(R.id.pacient_details_member_since);
        bloodTypeTextView = findViewById(R.id.pacient_details_pacient_blood_type);
        heightTextView = findViewById(R.id.pacient_details_pacient_height);
        weightTextView = findViewById(R.id.pacient_details_weight);
        medicalConsultationRecyclerView = findViewById(R.id.pacient_details_medical_history_list);
    }

    void getMedicalConsultations() {
        medicalConsultations = new ArrayList<>();

        try {
            for (MedicalConsultation medicalConsultation : pacient.getMedicalHistory()) {
                if (medicalConsultation.getAppointment()
                                       .getEndDate()
                                       .before(new Date())) {
                    for (int i = 0; i < 10; i++) {
                        medicalConsultations.add(medicalConsultation);
                    }
                }
            }
        } catch (Exception ex) {
            medicalConsultations = new ArrayList<>();
        }

        if (medicalConsultations == null || medicalConsultations.size() == 0) {
            medicalHistoryCardView.setVisibility(View.GONE);
        }
    }

    private void setViewsValues() {
        try {
            titleTextView.setText(getString(R.string.pacient_details_pacient));
            bloodTypeTextView.setText(pacient.getMedicalRecord()
                                             .getBloodType());
            heightTextView.setText(String.valueOf(pacient.getMedicalRecord()
                                                         .getHeight()));
            weightTextView.setText(String.valueOf(pacient.getMedicalRecord()
                                                         .getWeight()));

            new DownloadImageTask(photoImageView)
                    .execute(pacient.getPhotoURL());
            nameTextView.setText(pacient.getName());
            emailTextView.setText(pacient.getEmail());
            phoneNumberTextView.setText(String.valueOf(pacient.getHouseNumber()));
            cellNumberTextView.setText(String.valueOf(pacient.getPhoneNumber()));
            addressTextView.setText(pacient.getAddress());
            memberSinceTextView.setText(new ApplicationDateFormat().toString(pacient.getAdmissionDate()));
            MedicalConsultationReportAdapter customAdapter = new MedicalConsultationReportAdapter(medicalConsultations);
            customAdapter.setOnItemClickListener(new MedicalConsultationReportAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getApplicationContext(),
                                               BookAppointmentDetails.class);
                    intent.putExtra(BookAppointmentDetails.MEDICAL_CONSULTATION,
                                    medicalConsultations.get(position));
                    startActivity(intent);
                }
            });
            medicalConsultationRecyclerView.setHasFixedSize(true);
            medicalConsultationRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            medicalConsultationRecyclerView.setAdapter(customAdapter);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                           ex.getMessage(),
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }
}
