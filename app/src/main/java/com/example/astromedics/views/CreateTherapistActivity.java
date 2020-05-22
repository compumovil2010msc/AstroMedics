package com.example.astromedics.views;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.astromedics.R;
import com.example.astromedics.views.common.fragments.EducationalFormationCreation;

import java.util.HashMap;
import java.util.Map;

public class CreateTherapistActivity extends AppCompatActivity {
    private int index = 1;
    Map<Integer, EducationalFormationCreation> educationalFormationFragments;
    LinearLayout cardContainer;
    ScrollView scrollView;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_therapist);
        educationalFormationFragments = new HashMap<>();
        fragmentManager = getSupportFragmentManager();
        inflateViews();
        addEducationalFormation();
    }

    private void inflateViews() {
        cardContainer = findViewById(R.id.activity_create_therapist_container);
        scrollView = findViewById(R.id.activity_create_therapist_floating_scroll_view);
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
                                                                                                    public void onCalendarSolitude() {

                                                                                                    }
                                                                                                },
                                                                                                "Formación");

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
}
