package com.example.astromedics.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astromedics.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reports extends Fragment {

    public Reports() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }
}
