package com.example.astromedics.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.astromedics.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TherapistNotificationService extends IntentService {

    private static final String CHANNEL_ID = "AstromedicsNotificationChannel";
    private static final int NOTIFICATION_ID = 5;

    public TherapistNotificationService() {
        super(CHANNEL_ID);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listenToChanges();
    }

    @Override
    protected void onHandleIntent(Intent intent) { }

    public void listenToChanges() {
        FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser() ;
        if(fu == null) {
            System.out.println("el usuario es null");
            return;
        }
        // email y medicalconsultationID
        // conseguir paciente getinstance

        String path = "citas/" + fu.getUid() ;
        Log.i("NotificationService", "escuchando cambios en :" + fu.getUid());
        DatabaseReference dateReference = FirebaseDatabase.getInstance().getReference(path);
        dateReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // hacer algo con la info del dataSnapshot
                // tal vez se podria conseguir el usuario desde ahi, de forma que pueda salir en la notificacion
                Log.i("NotificationService", ">>>>>>>>>>>>>>>> cambio algo <<<<<<<<<<<<<<<<<<<<<<<");
                Log.i("NotificationService", ">>>>>>>>>>>>>>>> " + dataSnapshot + " <<<<<<<<<<<<<<<<<<<<<<<");

                showNotification("[usuario] ha pedido una cita, entra a la aplicacion para ver los detalles");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    void showNotification(String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.planet)
                .setContentTitle("Astromedics")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
