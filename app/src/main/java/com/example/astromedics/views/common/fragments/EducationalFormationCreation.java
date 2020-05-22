package com.example.astromedics.views.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.EducationalFormation;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.Date;

public class EducationalFormationCreation extends Fragment {

    private int index;
    private IEducationalFormationButtonListener educationalFormationButtonListener;
    private EditText insitutionEditText, titleObtainedEditText, startDateEditText, endDateEditText;
    private Date initialDate, endDate;
    ImageButton newButton, closeButton;
    private MaterialCheckBox isGraduatedCheckBox;

    public EducationalFormationCreation(int index, IEducationalFormationButtonListener educationalFormationButtonListener) {
        this.index = index;
        this.educationalFormationButtonListener = educationalFormationButtonListener;
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

    public EducationalFormation getEducationalFormation() throws Exception {
        if (insitutionEditText.getText()
                              .toString()
                              .equals("") ||
                titleObtainedEditText.getText()
                                     .toString()
                                     .equals("") ||
                initialDate == null ||
                (endDate == null && !isGraduatedCheckBox.isChecked())) {
            throw new Exception(getString(R.string.educational_formation_creation_exception));
        }


        return new EducationalFormation(0,
                                        insitutionEditText.getText()
                                                          .toString(),
                                        titleObtainedEditText.getText()
                                                             .toString(),
                                        initialDate,
                                        endDate,
                                        !isGraduatedCheckBox.isChecked());
    }

    private void inflateViews(View view) {
        insitutionEditText = view.findViewById(R.id.educational_formation_creation_institution);
        titleObtainedEditText = view.findViewById(R.id.educational_formation_creation_title_obtained);
        startDateEditText = view.findViewById(R.id.educational_formation_creation_start_date);
        endDateEditText = view.findViewById(R.id.educational_formation_creation_end_date);
        isGraduatedCheckBox = view.findViewById(R.id.educational_formation_creation_not_graduated);
        newButton = view.findViewById(R.id.educational_formation_creation_new_button);
        closeButton = view.findViewById(R.id.educational_formation_creation_close_button);
    }

    private void addInitialValues() {
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
                endDate = null;
                endDateEditText.setText("");
                endDateEditText.setEnabled(!isGraduatedCheckBox.isChecked() && initialDate != null);
            }
        });
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationalFormationButtonListener.onInitialCalendarSolitude(new OnCalendarSelectionListener() {
                    @Override
                    public void onCalendarSelection(Date date) {
                        initialDate = date;
                        endDate = null;
                        endDateEditText.setText("");
                        endDateEditText.setEnabled(!isGraduatedCheckBox.isChecked() && initialDate != null);
                        startDateEditText.setText(new ApplicationDateFormat().toString(date));
                    }
                });
            }
        });
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationalFormationButtonListener.onFinalCalendarSolitude(initialDate,
                                                                           new OnCalendarSelectionListener() {
                                                                               @Override
                                                                               public void onCalendarSelection(Date date) {
                                                                                   endDate = date;
                                                                                   endDateEditText.setText(new ApplicationDateFormat().toString(date));
                                                                               }
                                                                           });
            }
        });
    }

    public interface IEducationalFormationButtonListener {
        void onNewButtonPressed(int index);

        void onCloseButtonPressed(int index);

        void onInitialCalendarSolitude(OnCalendarSelectionListener onCalendarSelectionListener);

        void onFinalCalendarSolitude(Date initialDate, OnCalendarSelectionListener onCalendarSelectionListener);
    }

    public static interface OnCalendarSelectionListener {
        void onCalendarSelection(Date date);
    }
}
