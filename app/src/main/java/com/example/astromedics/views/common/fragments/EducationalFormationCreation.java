package com.example.astromedics.views.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.astromedics.R;
import com.google.android.material.checkbox.MaterialCheckBox;

public class EducationalFormationCreation extends Fragment {

    private int index;
    private String title;
    private IEducationalFormationButtonListener educationalFormationButtonListener;
    private TextView titleTextView;
    private EditText insitutionEditText, titleObtainedEditText, startDateEditText, endDateEditText;
    ImageButton newButton, closeButton;
    private MaterialCheckBox isGraduatedCheckBox;

    public EducationalFormationCreation(int index, IEducationalFormationButtonListener educationalFormationButtonListener, String title) {
        this.index = index;
        this.educationalFormationButtonListener = educationalFormationButtonListener;
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_educational_formation_creation,
                                     container,
                                     false);
        inflateViews(view);
        addInitialValues();
        addListeners();
        return view;
    }

    private void inflateViews(View view) {
        titleTextView = view.findViewById(R.id.educational_formation_creation_title);
        insitutionEditText = view.findViewById(R.id.educational_formation_creation_institution);
        titleObtainedEditText = view.findViewById(R.id.educational_formation_creation_title_obtained);
        startDateEditText = view.findViewById(R.id.educational_formation_creation_start_date);
        endDateEditText = view.findViewById(R.id.educational_formation_creation_end_date);
        isGraduatedCheckBox = view.findViewById(R.id.educational_formation_creation_not_graduated);
        newButton = view.findViewById(R.id.educational_formation_creation_new_button);
        closeButton = view.findViewById(R.id.educational_formation_creation_close_button);
    }

    private void addInitialValues() {
        titleTextView.setText(title);
        closeButton.setVisibility(index == 1 ? View.GONE : View.VISIBLE);
    }

    private void addListeners() {
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationalFormationButtonListener.onNewButtonPressed(index);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationalFormationButtonListener.onCloseButtonPressed(index);
            }
        });
        isGraduatedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                endDateEditText.setText("");
                endDateEditText.setEnabled(!isGraduatedCheckBox.isChecked());
            }
        });
    }

    public interface IEducationalFormationButtonListener {
        void onNewButtonPressed(int index);

        void onCloseButtonPressed(int index);

        void onCalendarSolitude();
    }
}
