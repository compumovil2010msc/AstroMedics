package com.example.astromedics.views;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.views.common.fragments.EducationalFormationCreation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTherapistActivity extends AppCompatActivity {
    private int index = 1;
    Map<Integer, EducationalFormationCreation> educationalFormationFragments;
    LinearLayout cardContainer;
    ScrollView scrollView;
    CardView calendarCardView;
    CalendarView calendarView;
    FragmentManager fragmentManager;
    EducationalFormationCreation.OnCalendarSelectionListener currentOnCalendarSelectionListener;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_therapist);
        educationalFormationFragments = new HashMap<>();
        fragmentManager = getSupportFragmentManager();
        inflateViews();
        addEducationalFormation();
        addListeners();
    }

    private void inflateViews() {
        cardContainer = findViewById(R.id.activity_create_therapist_container);
        scrollView = findViewById(R.id.activity_create_therapist_floating_scroll_view);
        calendarCardView = findViewById(R.id.activity_create_therapist_calendar_card_view);
        calendarView = findViewById(R.id.activity_create_therapist_calendar_view);
        floatingActionButton = findViewById(R.id.activity_create_therapist_floating_action_button);
    }

    private void addListeners() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Date pickedDate = new ApplicationDateFormat().createDate(year,
                                                                         month + 1,
                                                                         day);
                currentOnCalendarSelectionListener.onCalendarSelection(pickedDate);
                calendarCardView.setVisibility(View.INVISIBLE);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getEducationalFormation();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),
                                   ex.getMessage(),
                                   Toast.LENGTH_LONG)
                         .show();
                }
            }
        });
    }

    private void addEducationalFormation() {
        EducationalFormationCreation newEducationalFormation = new EducationalFormationCreation(index,
                                                                                                new EducationalFormationCreation.IEducationalFormationButtonListener() {

                                                                                                    @Override
                                                                                                    public void onNewButtonPressed(int i) {
                                                                                                        addEducationalFormation();
                                                                                                        scrollView.postDelayed(new Runnable() {
                                                                                                                                   @Override
                                                                                                                                   public void run() {
                                                                                                                                       scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                                                                                                   }
                                                                                                                               },
                                                                                                                               300);
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCloseButtonPressed(int i) {
                                                                                                        removeEducationalFormation(i);
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onInitialCalendarSolitude(
                                                                                                            EducationalFormationCreation.OnCalendarSelectionListener onCalendarSelectionListener) {
                                                                                                        calendarView.setMinDate(0);
                                                                                                        calendarView.setMaxDate(Long.MAX_VALUE);
                                                                                                        calendarView.setMaxDate(new Date().getTime());
                                                                                                        calendarCardView.setVisibility(View.VISIBLE);
                                                                                                        currentOnCalendarSelectionListener = onCalendarSelectionListener;
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onFinalCalendarSolitude(
                                                                                                            Date initialDate,
                                                                                                            EducationalFormationCreation.OnCalendarSelectionListener onCalendarSelectionListener) {
                                                                                                        calendarView.setMinDate(0);
                                                                                                        calendarView.setMaxDate(Long.MAX_VALUE);
                                                                                                        calendarView.setMaxDate(new Date().getTime());
                                                                                                        calendarView.setMinDate(initialDate.getTime());
                                                                                                        calendarCardView.setVisibility(View.VISIBLE);
                                                                                                        currentOnCalendarSelectionListener = onCalendarSelectionListener;
                                                                                                    }
                                                                                                });

        educationalFormationFragments.put(new Integer(index),
                                          newEducationalFormation);
        fragmentManager.beginTransaction()
                       .add(cardContainer.getId(),
                            educationalFormationFragments.get(new Integer(index)),
                            String.valueOf(index))
                       .commit();
        index++;
    }

    private void removeEducationalFormation(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager
                .findFragmentByTag(String.valueOf(i));
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
        educationalFormationFragments.remove(new Integer(index));
    }

    private List<EducationalFormation> getEducationalFormation() throws Exception {
        List<EducationalFormation> returnable = new ArrayList<>();

        for (EducationalFormationCreation educationalFormationCreation : educationalFormationFragments.values()) {
            returnable.add(educationalFormationCreation.getEducationalFormation());
        }

        return returnable;
    }
}
