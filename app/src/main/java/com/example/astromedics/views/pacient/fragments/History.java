package com.example.astromedics.views.pacient.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.MedicalConsultationAdapter;
import com.example.astromedics.adapters.MedicalConsultationHistoryAdapter;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.pacient.BookAppointmentDetails;
import com.example.astromedics.views.pacient.BookAppointmentLocationSelectionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History extends Fragment {
    private List<MedicalConsultation> medicalConsultations;
    LinearLayout noAppointmentsLinearLayout;
    private ListView medicalConsultationListView;

    public History() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_history,
                                           container,
                                           false);
        initViews(view);
        getMedicalConsultations();
        setListeners(view);
        return view;
    }

    private void initViews(View view) {
        noAppointmentsLinearLayout = view.findViewById(R.id.history_no_appointments);
        medicalConsultationListView = view.findViewById(R.id.history_appointments);
    }

    private void setListeners(final View view) {
        MedicalConsultationHistoryAdapter customAdapter = new MedicalConsultationHistoryAdapter(
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
            for (MedicalConsultation medicalConsultation : Repository.getInstance()
                                                                     .getPacientRepository()
                                                                     .getPacient(Session.getInstance()
                                                                                        .getEmail())
                                                                     .getMedicalHistory()) {
                if (medicalConsultation.getAppointment()
                                       .getEndDate()
                                       .before(new Date())) {
                    medicalConsultations.add(medicalConsultation);
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