package com.example.astromedics.views.pacient.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.TherapistAvailableAppointmentAdapter;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.Therapist;

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

        List<String> availableDays = new ArrayList<>();
        ApplicationDateFormat applicationDateFormat = new ApplicationDateFormat();

        for(Date date: therapist.getAvailableDays()){
            availableDays.add(applicationDateFormat.toString(date));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                                                android.R.layout.simple_spinner_item,
                                                                availableDays.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentDaysSpinner.setAdapter(adapter);


        appointmentListRecyclerView.setHasFixedSize(true);
        appointmentListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        therapistAvailableAppointmentAdapter = new TherapistAvailableAppointmentAdapter(therapist.getAppointments());
        appointmentListRecyclerView.setAdapter(therapistAvailableAppointmentAdapter);
    }

    private void addListeners() {

    }
}
