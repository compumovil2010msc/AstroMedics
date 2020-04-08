package com.example.astromedics.views.pacient.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.astromedics.R;
import com.example.astromedics.adapters.MedicalConsultationAdapter;
import com.example.astromedics.helpers.PermissionHandler;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.pacient.BookAppointmentDetails;
import com.example.astromedics.views.pacient.BookAppointmentLocationSelectionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookAppointment extends Fragment {
    private PermissionHandler permissionHandler;
    private FloatingActionButton floatingActionButton;
    private List<MedicalConsultation> medicalConsultations;
    private ListView medicalConsultationListView;

    public BookAppointment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_book_appointment,
                                           container,
                                           false);
        initViews(view);
        getMedicalConsultations();
        setListeners(view);
        return view;
    }

    private void initViews(View view) {
        floatingActionButton = view.findViewById(R.id.addAppointment);
        medicalConsultationListView = view.findViewById(R.id.listViewAgenda);
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

        MedicalConsultationAdapter customAdapter = new MedicalConsultationAdapter(
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

        for (MedicalConsultation medicalConsultation : Repository.getInstance()
                                                                 .getPacientRepository()
                                                                 .getPacient(Session.getInstance()
                                                                                    .getEmail())
                                                                 .getMedicalHistory()) {
            if (medicalConsultation.getAppointment()
                                   .getEndDate()
                                   .after(new Date())) {
                medicalConsultations.add(medicalConsultation);
            }
        }
    }
}
