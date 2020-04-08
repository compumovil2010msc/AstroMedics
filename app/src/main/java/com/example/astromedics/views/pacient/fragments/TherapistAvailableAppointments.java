package com.example.astromedics.views.pacient.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.TherapistAvailableAppointmentAdapter;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.model.dto.ApplicationDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TherapistAvailableAppointments extends Fragment {
    private Therapist therapist;
    private Localization localization;
    private Therapist.Emphasis emphasis;
    private Date startDate, endDate;

    private RecyclerView appointmentListRecyclerView;
    private Spinner appointmentDaysSpinner;
    private TherapistAvailableAppointmentAdapter therapistAvailableAppointmentAdapter;

    List<ApplicationDate> availableDays;
    List<Appointment> availableAppointmentsPerDay;

    public TherapistAvailableAppointments(Therapist therapist, Localization localization, Therapist.Emphasis emphasis, Date startDate, Date endDate) {
        this.therapist = therapist;
        this.localization = localization;
        this.emphasis = emphasis;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_therapist_available_appointments,
                                     container,
                                     false);
        inflateViews(view);
        setInitialValues();
        addListeners();
        return view;
    }

    private void inflateViews(View view) {
        appointmentDaysSpinner = view.findViewById(R.id.therapist_available_appointments_day_list);
        appointmentListRecyclerView = view.findViewById(R.id.therapist_available_appointment_hour_list);
    }

    private void setInitialValues() {
        availableDays = new ArrayList<>();
        availableAppointmentsPerDay = new ArrayList<>();

        for (Date date : therapist.getAvailableDays(startDate,
                                                    endDate)) {
            availableDays.add(new ApplicationDate(date));
        }

        ArrayAdapter<ApplicationDate> adapter = new ArrayAdapter<ApplicationDate>(getContext(),
                                                                                  android.R.layout.simple_spinner_item,
                                                                                  availableDays.toArray(new ApplicationDate[0]));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentDaysSpinner.setAdapter(adapter);

        appointmentListRecyclerView.setHasFixedSize(true);
        appointmentListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        therapistAvailableAppointmentAdapter = new TherapistAvailableAppointmentAdapter(availableAppointmentsPerDay);
        appointmentListRecyclerView.setAdapter(therapistAvailableAppointmentAdapter);
    }

    private void addListeners() {
        appointmentDaysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Date selectedDate = availableDays.get(i)
                                                 .getDate();
                availableAppointmentsPerDay = therapist.getAvailableAppointmentsByDate(selectedDate);
                therapistAvailableAppointmentAdapter = new TherapistAvailableAppointmentAdapter(availableAppointmentsPerDay);
                appointmentListRecyclerView.setAdapter(therapistAvailableAppointmentAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
