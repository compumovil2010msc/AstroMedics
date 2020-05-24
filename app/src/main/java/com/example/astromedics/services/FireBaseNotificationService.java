package com.example.astromedics.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.dto.PersonNotifier;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;
import com.example.astromedics.views.common.BookAppointmentDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseNotificationService extends IntentService {

    public static String CHANNEL_ID = "MyApp";
    int notificationId = 0;

    public FireBaseNotificationService() {
        super("FireBaseNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String PATH_NOTIFICATIONS = "PersonNotifier/";
            FirebaseDatabase database;
            DatabaseReference myRef;
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference(PATH_NOTIFICATIONS);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        PersonNotifier notifier = singleSnapshot.getValue(PersonNotifier.class);
                        String id = singleSnapshot.getKey();
                        if (notifier.getEmail()
                                    .equals(Session.getInstance()
                                                   .getEmail()) && !notifier.isAlreadyNotified()) {
                            createNotification(notifier.getMedicalConsultationId());
                            notifier.setAlreadyNotified(true);

                            String PATH_NOTIFICATIONS = "PersonNotifier/";
                            FirebaseDatabase database;
                            DatabaseReference myRef;
                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference(PATH_NOTIFICATIONS + id);
                            myRef.setValue(notifier);


                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void createNotification(int medicalConsultationId) {
        try {
            MedicalConsultation medicalConsultation = new MedicalConsultation();
            medicalConsultation.setMedicalConsultationId(medicalConsultationId);
            Pacient pacient = Repository.getInstance()
                                        .getPacientRepository()
                                        .getPacient(medicalConsultation);
            for (MedicalConsultation currentMedicalConsultation : pacient.getMedicalHistory()) {
                if (currentMedicalConsultation.getMedicalConsultationId() == medicalConsultationId) {
                    medicalConsultation = currentMedicalConsultation;
                }
            }


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                                                                                 CHANNEL_ID);
            mBuilder.setSmallIcon(R.drawable.planet);
            mBuilder.setContentTitle("Nueva Consulta Terapeutica");
            mBuilder.setContentText(pacient.getName() + ", " + new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                                                       .getStartDate()));
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            Intent intent = new Intent(this,
                                       BookAppointmentDetails.class);
            intent.putExtra(BookAppointmentDetails.MEDICAL_CONSULTATION,
                            medicalConsultation);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                                                                    0,
                                                                    intent,
                                                                    0);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setAutoCancel(true);
            notificationManager.notify(notificationId++,
                                       mBuilder.build());
        } catch (Exception ex) {

        }
    }
}
