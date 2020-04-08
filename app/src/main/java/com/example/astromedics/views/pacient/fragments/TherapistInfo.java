package com.example.astromedics.views.pacient.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.BuildConfig;
import com.example.astromedics.R;
import com.example.astromedics.adapters.EducationalFormationAdapter;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Therapist;

public class TherapistInfo extends Fragment {
    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".THERAPIST_INFO_TAG";

    Therapist therapist;
    RecyclerView educationalFormationRecyclerView;
    TextView therapistNameTextView, memberSinceTextView, numberOfConsultationTextView, calificationTextView;

    public TherapistInfo(Therapist therapist) {
        this.therapist = therapist;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_therapist_info,
                                     container,
                                     false);
        inflateViews(view);
        setInitialValues();
        return view;
    }

    private void inflateViews(View view) {
        educationalFormationRecyclerView = view.findViewById(R.id.therapist_info_educational_formation);
        therapistNameTextView = view.findViewById(R.id.therapist_info_name);
        memberSinceTextView = view.findViewById(R.id.therapist_info_member_since);
        numberOfConsultationTextView = view.findViewById(R.id.therapist_info_number_of_medical_consultations);
        calificationTextView = view.findViewById(R.id.therapist_info_calification);
    }

    private void setInitialValues() {
        therapistNameTextView.setText(therapist.getName());
        memberSinceTextView.setText(new ApplicationDateFormat().toString(therapist.getAdmissionDate()));
        numberOfConsultationTextView.setText(String.valueOf(therapist.getNumberOfCompletedMedicalConsultations()));
        calificationTextView.setText(String.valueOf(therapist.getAverageCalification()));
        EducationalFormationAdapter educationalFormationAdapter = new EducationalFormationAdapter(therapist.getEducationalFormation());
        educationalFormationRecyclerView.setHasFixedSize(true);
        educationalFormationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        educationalFormationRecyclerView.setAdapter(educationalFormationAdapter);
    }
}
