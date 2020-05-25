package com.example.astromedics;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.example.astromedics.helpers.FileHandler;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.services.FireBaseNotificationService;
import com.example.astromedics.services.UserService;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.MainActivity;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.example.astromedics.views.therapist.HomeTherapist;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    public static String CHANNEL_ID = "MyApp";
    int notificationId = 0;
    protected static final int MY_STORAGE_PERMISSION = 503;
    private FirebaseAuth mAuth;
    private UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAuth = FirebaseAuth.getInstance();
        MediaManager.init(getApplicationContext());
        FileHandler.getInstance(getApplicationContext());
        this.userService = App.get()
                              .getUserService();
        Log.i("SPLASH_ACT",
              this.mAuth.getCurrentUser() == null ? "NOT USER LOGGED" : "USER LOOGED: " + this.mAuth.getCurrentUser()
                                                                                                    .getEmail());

        if (ContextCompat.checkSelfPermission(this,
                                              Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                                                  Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {
            ActivityCompat.requestPermissions(this,
                                              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                              MY_STORAGE_PERMISSION);
        }
    }

    private void init() {
        if (this.mAuth.getCurrentUser() != null) {
            verifyData(this.mAuth.getCurrentUser()
                                 .getEmail());
        } else {
            Intent intentMain = new Intent(this,
                                           MainActivity.class);
            startActivity(intentMain);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(SplashActivity.this,
                                   "Es necesario otorgar permisos de escritura para iniciar la aplicacion",
                                   Toast.LENGTH_SHORT)
                         .show();
                }
                break;
        }
    }

    private void verifyData(String email) {
    /*
	if(!SharedPreferencesUtils.hasSharedPreferenceKey("userLoggedIn")){
            this.userService.getUser(email).subscribe(person -> {
                if(person!=null){
                    redirect(person.isDoctor());
                }
            },err->{
                Log.e("SPLASH_ACT","error getting user: "+err.getMessage());
            });
        }else{
            Person pInShared=SharedPreferencesUtils.getSharedPref("userLoggedIn",Person.class);
            Log.i("SPLASH_ACT","Splash user in shared preferences: "+pInShared==null?"null":pInShared.toString());
            redirect(pInShared.isDoctor());
        }
    */
        try {
            login();
        } catch (Exception ex) {
            Toast.makeText(SplashActivity.this,
                           "Error en autenticacion",
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }

    private void login() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                public void run() {
                                    try {
                                        createNotificationChannel();
                                        Person person = Repository.getInstance()
                                                                  .getPersonRepository()
                                                                  .get(Session.getInstance()
                                                                              .getEmail());
                                        if (person instanceof Therapist) {
                                            Intent intent = new Intent(getApplicationContext(),
                                                                       HomeTherapist.class);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(getApplicationContext(),
                                                                       HomeUserActivity.class);
                                            startActivity(intent);
                                        }
                                    } catch (Exception ex) {
                                        login();
                                    }
                                }
                            },
                            2000);
    }

    private void createNotificationChannel() {
        CharSequence name = "channel";
        String description = "channel description";

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                                              name,
                                                              NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Intent intent = new Intent(SplashActivity.this,
                                   FireBaseNotificationService.class);
        startService(intent);
    }
}
