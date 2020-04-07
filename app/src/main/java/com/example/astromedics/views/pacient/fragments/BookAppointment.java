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
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.old.ConsultaMedica;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.pacient.AgendaDetalle;
import com.example.astromedics.views.pacient.BookAppointmentLocationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookAppointment extends Fragment {
    FloatingActionButton floatingActionButton;
    List<MedicalConsultation> medicalConsultations;
    ListView medicalConsultationListView;

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
                                           BookAppointmentLocationActivity.class);
                startActivity(intent);
            }
        });

        MedicalConsultationAdapter customAdapter = new MedicalConsultationAdapter(
                view.getContext(),
                R.layout.list_view_medical_consultation,
                medicalConsultations
        );

        medicalConsultationListView.setAdapter(customAdapter);

        medicalConsultationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    void getMedicalConsultations() {
        medicalConsultations = Repository.getInstance()
                                         .getPacientRepository()
                                         .getPacient(Session.getInstance()
                                                            .getEmail())
                                         .getMedicalHistory();
    }
}
