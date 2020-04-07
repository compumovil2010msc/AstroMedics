package com.example.astromedics.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.old.TherapistAvailableAppointmentAdapter;
import com.example.astromedics.model.old.TherapistAvailableAppointment;
import com.example.astromedics.views.pacient.HomeUserActivity;

import java.util.ArrayList;

public class TherapistAppointmentsAvailable extends Fragment {

    private ListView listView;
    private TherapistAvailableAppointmentAdapter mAdapter;

    public TherapistAppointmentsAvailable() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_therapist_appointments_available, container, false);

        listView = (ListView) view.findViewById(R.id.therapist_available_appointment_list);

        ArrayList<TherapistAvailableAppointment> availableAppointments = new ArrayList<>();
        availableAppointments.add(new TherapistAvailableAppointment(1, 0, 2, 0));
        availableAppointments.add(new TherapistAvailableAppointment(2, 0, 3, 0));
        availableAppointments.add(new TherapistAvailableAppointment(3, 0, 4, 0));
        availableAppointments.add(new TherapistAvailableAppointment(4, 0, 5, 0));

        mAdapter = new TherapistAvailableAppointmentAdapter(view.getContext(), availableAppointments);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(view.getContext(), HomeUserActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
