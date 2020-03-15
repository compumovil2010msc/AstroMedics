package com.example.astromedics.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.astromedics.R;
import com.example.astromedics.adapters.PageAdapter;
import com.example.astromedics.views.fragments.BookAppointment;
import com.example.astromedics.views.fragments.History;
import com.example.astromedics.views.fragments.Settings;
import com.google.android.material.tabs.TabLayout;

public class TherapistDetailsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_details);

        tabLayout = findViewById(R.id.therapist_tab_layout);
        viewPager = findViewById(R.id.therapist_view_pager);
        nestedScrollView = findViewById(R.id.therapist_nested_scroll_view);

        nestedScrollView.setFillViewport(true);

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

        setUpViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager){
        PageAdapter pageAdapter=new PageAdapter(getSupportFragmentManager());
        Fragment agendarCita=new BookAppointment();
        Fragment historial=new History();
        pageAdapter.addFragment(agendarCita);
        pageAdapter.addFragment(historial);
        viewPager.setAdapter(pageAdapter);
    }
}
