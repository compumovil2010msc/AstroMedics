package com.example.astromedics.views.therapist;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.astromedics.R;
import com.example.astromedics.adapters.PageAdapter;
import com.example.astromedics.views.fragments.Reports;
import com.example.astromedics.views.fragments.Settings;
import com.example.astromedics.views.fragments.TherapistBookAppointment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeTherapist extends AppCompatActivity {

    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_therapist);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        final ViewPager viewPager = findViewById(R.id.viewPagerUser);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_calendar:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_reports:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_settings:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                }
                                                                );
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu()
                                        .getItem(0)
                                        .setChecked(false);
                }
                Log.d("page",
                      "onPageSelected: " + position);
                bottomNavigationView.getMenu()
                                    .getItem(position)
                                    .setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu()
                                                   .getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUpViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        Fragment agendarCita = new TherapistBookAppointment();
        Fragment reportes = new Reports();
        Fragment configuracion = new Settings();
        pageAdapter.addFragment(agendarCita);
        pageAdapter.addFragment(reportes);
        pageAdapter.addFragment(configuracion);
        viewPager.setAdapter(pageAdapter);
    }
}
