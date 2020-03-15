package com.example.astromedics.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astromedics.R;
import com.example.astromedics.views.BookAppointmentLocationActivity;
import com.example.astromedics.views.TherapistDetailsActivity;
import com.example.astromedics.views.TherapistFilterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookAppointment extends Fragment {
    FloatingActionButton floatingActionButton;

    public BookAppointment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        floatingActionButton = view.findViewById(R.id.addAppointment);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), TherapistDetailsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
