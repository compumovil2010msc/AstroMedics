package com.example.astromedics.views.pacient;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.astromedics.R;
import com.example.astromedics.adapters.PageAdapter;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.views.fragments.TherapistAppointmentsAvailable;
import com.example.astromedics.views.pacient.fragments.TherapistInfo;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.Date;

public class TherapistAvailableAppointmentsActivity extends AppCompatActivity {
    public static String THERAPIST = "therapist";
    public static String LOCATION = "localization";
    public static String EMPHASIS = "emphasis";
    public static String START_DATE = "start_date";
    public static String END_DATE = "end_date";

    private Therapist therapist;
    private Localization localization;
    private Therapist.Emphasis emphasis;
    private Date startDate, endDate;

    ImageView therapistPhotoImageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_available_appointments);
        obtainObjects();
        inflateViews();
        addInitialValues();
        nestedScrollView.setFillViewport(true);
        addListeners();
        setUpViewPager(viewPager);
    }

    private void obtainObjects() {
        therapist = (Therapist) getIntent().getSerializableExtra(THERAPIST);
        localization = (Localization) getIntent().getSerializableExtra(LOCATION);
        startDate = (Date) getIntent().getSerializableExtra(START_DATE);
        endDate = (Date) getIntent().getSerializableExtra(END_DATE);
        emphasis = (Therapist.Emphasis) getIntent().getSerializableExtra(EMPHASIS);
    }

    private void inflateViews() {
        therapistPhotoImageView = findViewById(R.id.therapist_available_appointment_photo);
        collapsingToolbarLayout = findViewById(R.id.therapist_available_appointment_collapsing_tool_bar);
        tabLayout = findViewById(R.id.therapist_available_appointment_tab_layout);
        viewPager = findViewById(R.id.therapist_available_appointment_view_pager);
        nestedScrollView = findViewById(R.id.therapist_available_appointment_nested_scroll_view);
    }

    private void addInitialValues() {
        collapsingToolbarLayout.setTitle(therapist.getName());
        new DownloadImageTask(therapistPhotoImageView)
                .execute(therapist.getPhotoURL());
    }

    private void addListeners() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setUpViewPager(ViewPager viewPager) {
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        Fragment appointmentsAvailable = new TherapistAppointmentsAvailable();
        Fragment therapistInfo = new TherapistInfo(therapist);
        pageAdapter.addFragment(appointmentsAvailable);
        pageAdapter.addFragment(therapistInfo);
        viewPager.setAdapter(pageAdapter);
    }
}
