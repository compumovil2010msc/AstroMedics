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
    private static final String CHANNEL_ID = "AstromedicsNotificationChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_therapist);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        final ViewPager viewPager = findViewById(R.id.viewPagerUser);

        // TODO: borrar esto, esta para probar el funcionamiento de notificaciones
        showNotification("[usuario] ha pedido una cita, entra a la aplicacion para ver los detalles");
        listenChanges();

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
//        startService(
//                new Intent(this, TherapistNotificationService.class)
//        );

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

    public void listenChanges() {
        // el usuario en este punto nunca deberia ser null, ya que esto comienza al entrar a HomeTherapist
        FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser() ;

        if(fu == null)
        {
            System.out.println("el usuario es null");
            return;
        }

        // TODO: quitar pruebas
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        // referencia
//        authFirebaseRef.getRoot().child("sos/emergencyservices"‌​).addValueEventListe‌​ner(new ValueEventListener() { };
        ref.getRoot().child("citas").setValue("hola");
        ref.getRoot().child("citas/loquesea").setValue("hola");
//        ref.setValue("Hello World!");
        return ;


        // Attach a listener to read the data at our posts reference
        // TODO: encontrar como conseguir permiso para la modificacion/lectura de archivos de la bd
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("citas/" + fu.getUid() );
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                Post post = dataSnapshot.getValue(Post.class);
////                System.out.println(post);
//                // hacer algo con la info del dataSnapshot
//                showNotification("[usuario] ha pedido una cita, entra a la aplicacion para ver los detalles");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
//        ref.setValue("Hello World!");
    }

    // para conectarse a la base de datos secundaria
    DatabaseReference setNewDatabase(String referencePath) {
        System.out.println(referencePath);
        FirebaseApp app = FirebaseApp.getInstance("temporal");
        final FirebaseDatabase db = FirebaseDatabase.getInstance(app);
        return db.getReference(referencePath);
    }

    void showNotification(String texto) {
        Log.i("mensajes", "=====================================oiga, han cambiado los datos");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.planet)
                .setContentTitle("Astromedics")
                .setContentText(texto)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(5, builder.build());
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
