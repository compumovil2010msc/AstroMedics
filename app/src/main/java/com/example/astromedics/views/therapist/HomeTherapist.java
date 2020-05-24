package com.example.astromedics.views.therapist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.astromedics.R;
import com.example.astromedics.adapters.PageAdapter;
import com.example.astromedics.notifications.TherapistNotificationService;
import com.example.astromedics.views.MainActivity;
import com.example.astromedics.views.common.fragments.UserSettings;
import com.example.astromedics.views.therapist.fragments.Reports;
import com.example.astromedics.views.therapist.fragments.TherapistBookAppointment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeTherapist extends AppCompatActivity {

    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_therapist);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        final ViewPager viewPager = findViewById(R.id.viewPagerUser);

        // ejemplo para agregar una segunda base de datos junto a la original
        //.setApplicationId("1:530266078999:android:481c4ecf3253701e") // Required for Analytics.
        //.setApiKey("AIzaSyBRxOyIj5dJkKgAVPXRLYFkdZwh2Xxq51k") // Required for Auth.
        //.setDatabaseUrl("https://project-1765055333176374514.firebaseio.com/") // Required for RTDB.

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("autenticacion-ea619") // Required for Analytics.
//                .setApiKey("AIzaSyDW18AE508FHwZ89xhxtCuQ6JZqa1e66xc")
//                .setDatabaseUrl("https://autenticacion-ea619.firebaseio.com") // Required for RTDB.
//                .setStorageBucket("autenticacion-ea619.appspot.com")
//                .build();
//        FirebaseApp.initializeApp(this); // ya que no se esta usando para nada mas, hacia falta inicializarla
        // de forma que pudiera poner la segunda base de datos, para poder realizar las pruebas
//        FirebaseApp.initializeApp(getApplicationContext(), options, "temporal");

//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> se agrega bd secundaria");
        startService(
                new Intent(this, TherapistNotificationService.class)
        );

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
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
        Fragment configuracion = new UserSettings();
        pageAdapter.addFragment(agendarCita);
        pageAdapter.addFragment(reportes);
        pageAdapter.addFragment(configuracion);
        viewPager.setAdapter(pageAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(getApplicationContext(),
                                      MainActivity.class);
        startActivity(setIntent);
    }
}
