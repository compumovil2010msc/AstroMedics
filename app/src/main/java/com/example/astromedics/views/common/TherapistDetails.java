package com.example.astromedics.views.common;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.views.pacient.fragments.TherapistInfo;

public class TherapistDetails extends AppCompatActivity {
    public static String THERAPIST = "therapist";

    private ImageView photoImageView;
    private Therapist therapist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_details);
        obtainObjects();
        inflateViews();
        setInitialValues();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.therapist_details_container,
                     new TherapistInfo(therapist),
                     TherapistInfo.FRAGMENT_TAG)
                .disallowAddToBackStack()
                .commit();
    }

    private void obtainObjects() {
        therapist = (Therapist) getIntent().getSerializableExtra(THERAPIST);
    }

    private void inflateViews() {
        photoImageView = findViewById(R.id.therapist_details_photo);
    }

    private void setInitialValues() {
        new DownloadImageTask(photoImageView)
                .execute(therapist.getPhotoURL());
    }
}
