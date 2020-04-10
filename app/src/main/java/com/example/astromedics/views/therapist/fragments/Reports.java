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
import com.example.astromedics.adapters.MedicalConsultationHistoryAdapter;
import com.example.astromedics.adapters.MedicalConsultationTherapistHistoryAdapter;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.pacient.BookAppointmentHistoryDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Reports extends Fragment {
    private List<MedicalConsultation> medicalConsultations;
    LinearLayout noReportsLinearLayout;
    private ListView medicalConsultationListView;

    public Reports() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reports,
                                           container,
                                           false);
        initViews(view);
        getMedicalConsultations();
        setListeners(view);
        return view;
    }

    private void initViews(View view) {
        noReportsLinearLayout = view.findViewById(R.id.reports_no_reports);
        medicalConsultationListView = view.findViewById(R.id.reports_appointments);
    }

    private void setListeners(final View view) {
        MedicalConsultationTherapistHistoryAdapter customAdapter = new MedicalConsultationTherapistHistoryAdapter(
                view.getContext(),
                medicalConsultations
        );

        medicalConsultationListView.setAdapter(customAdapter);

        medicalConsultationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),
                                           BookAppointmentHistoryDetails.class);
                intent.putExtra(BookAppointmentHistoryDetails.MEDICAL_CONSULTATION,
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
                               .before(new Date()) && appointment.getMedicalConsultation() != null) {
                    medicalConsultations.add(appointment.getMedicalConsultation());
                }
            }
            Collections.sort(medicalConsultations);
        } catch (Exception ex) {
            medicalConsultations = new ArrayList<>();
        }

        if (medicalConsultations == null || medicalConsultations.size() == 0) {
            noReportsLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}