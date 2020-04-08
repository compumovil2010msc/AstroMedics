package com.example.astromedics.views.pacient.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.TherapistAvailableAppointmentAdapter;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.model.dto.ApplicationDate;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.pacient.HomeUserActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TherapistAvailableAppointments extends Fragment {
    private Therapist therapist;
    private Appointment selectedAppointment;
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
                therapistAvailableAppointmentAdapter.setOnItemClickListener(new TherapistAvailableAppointmentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        selectedAppointment = availableAppointmentsPerDay.get(position);

                        new AlertDialog.Builder(getContext())
                                .setTitle(getString(R.string.therapist_available_appointment_confirm_dialog_info))
                                .setMessage(getString(R.string.therapist_available_appointment_confirm_dialog_message)
                                                    .replace("[THERAPIST]",
                                                             therapist.getName())
                                                    .replace("[DAY]",
                                                             new ApplicationDateFormat().toString(selectedAppointment.getStartDate()))
                                                    .replace("[TIME]",
                                                             new ApplicationDateFormat().getHoursAndMinutes(selectedAppointment.getStartDate())))
                                .setIcon(getResources().getDrawable(R.drawable.ic_today_black_24dp,
                                                                    null))
                                .setPositiveButton(getString(R.string.therapist_available_appointment_confirm_dialog_yes),
                                                   new DialogInterface.OnClickListener() {

                                                       public void onClick(DialogInterface dialog, int whichButton) {
                                                           try {
                                                               MedicalConsultation createdMedicalConsultarion = Repository.getInstance()
                                                                                                                          .getPacientRepository()
                                                                                                                          .createMedicalConsultation(Repository.getInstance()
                                                                                                                                                               .getPacientRepository()
                                                                                                                                                               .getPacient(Session.getInstance()
                                                                                                                                                                                  .getEmail()),
                                                                                                                                                     therapist,
                                                                                                                                                     emphasis,
                                                                                                                                                     localization,
                                                                                                                                                     selectedAppointment);

                                                               Intent intent = new Intent(getContext(),
                                                                                          HomeUserActivity.class);
                                                               startActivity(intent);
                                                           } catch (Exception ex) {
                                                               Toast.makeText(getContext(),
                                                                              ex.getMessage(),
                                                                              Toast.LENGTH_SHORT)
                                                                    .show();
                                                           }
                                                       }
                                                   })
                                .setNegativeButton(getString(R.string.therapist_available_appointment_confirm_dialog_no),
                                                   null)
                                .show();
                    }
                });
                appointmentListRecyclerView.setAdapter(therapistAvailableAppointmentAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
