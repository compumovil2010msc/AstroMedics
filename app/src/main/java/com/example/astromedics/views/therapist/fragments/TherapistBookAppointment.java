package com.example.astromedics.views.therapist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.astromedics.R;
import com.example.astromedics.adapters.TherapistMedicalConsultationAdapter;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.common.BookAppointmentDetails;
import com.example.astromedics.views.pacient.BookAppointmentLocationSelectionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TherapistBookAppointment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private List<MedicalConsultation> medicalConsultations;
    LinearLayout noAppointmentsLinearLayout;
    private ListView medicalConsultationListView;

    public TherapistBookAppointment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_therapist_book_appointment,
                                           container,
                                           false);
        initViews(view);
        getMedicalConsultations();
        setListeners(view);
        return view;
    }

    private void initViews(View view) {
        noAppointmentsLinearLayout = view.findViewById(R.id.therapist_book_appointment_no_appointments);
        floatingActionButton = view.findViewById(R.id.therapist_book_appointment_add_appointment);
        medicalConsultationListView = view.findViewById(R.id.therapist_book_appointment_appointments);
    }

    private void setListeners(final View view) {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),
                                           BookAppointmentLocationSelectionActivity.class);
                startActivity(intent);
            }
        });

        TherapistMedicalConsultationAdapter customAdapter = new TherapistMedicalConsultationAdapter(
                view.getContext(),
                medicalConsultations
        );

        medicalConsultationListView.setAdapter(customAdapter);

        medicalConsultationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),
                                           BookAppointmentDetails.class);
                intent.putExtra(BookAppointmentDetails.MEDICAL_CONSULTATION,
                                medicalConsultations.get(position));
                startActivity(intent);
            }
        });
    }

    void getMedicalConsultations() {
        medicalConsultations = new ArrayList<>();

        try {
            for (Appointment appointment : Repository.getInstance()
                                                     .getTherapistRepository()
                                                     .getTherapist(Session.getInstance()
                                                                          .getEmail())
                                                     .getAppointments()) {
                if (appointment.getEndDate()
                               .after(new Date()) && appointment.getMedicalConsultation() != null) {
                    medicalConsultations.add(appointment.getMedicalConsultation());
                }
            }
        } catch (Exception ex) {
            medicalConsultations = new ArrayList<>();
        }

        if (medicalConsultations == null || medicalConsultations.size() == 0) {
            noAppointmentsLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
