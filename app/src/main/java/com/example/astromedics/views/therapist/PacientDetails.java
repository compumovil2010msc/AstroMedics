package com.example.astromedics.views.therapist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.Pacient;

public class PacientDetails extends AppCompatActivity {
    public static String PACIENT = "paciente";

    Pacient pacient;
    ImageView photoImageView;
    CardView pacientCardView;
    TextView titleTextView, nameTextView, emailTextView, phoneNumberTextView, cellNumberTextView, addressTextView, memberSinceTextView, bloodTypeTextView, heightTextView, weightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient_details);
        obtainObjects();
        inflateViews();
        setViewsValues();
    }

    private void obtainObjects() {
        pacient = (Pacient) getIntent().getSerializableExtra(PACIENT);
    }


    private void inflateViews() {
        pacientCardView = findViewById(R.id.pacient_details_pacient_card_view);
        photoImageView = findViewById(R.id.pacient_details_photo);
        titleTextView = findViewById(R.id.pacient_details_title);
        nameTextView = findViewById(R.id.pacient_details_name);
        emailTextView = findViewById(R.id.pacient_details_email);
        phoneNumberTextView = findViewById(R.id.pacient_details_phone_number);
        cellNumberTextView = findViewById(R.id.pacient_details_cell_number);
        addressTextView = findViewById(R.id.pacient_details_address);
        memberSinceTextView = findViewById(R.id.pacient_details_member_since);
        bloodTypeTextView = findViewById(R.id.pacient_details_pacient_blood_type);
        heightTextView = findViewById(R.id.pacient_details_pacient_height);
        weightTextView = findViewById(R.id.pacient_details_weight);
    }

    private void setViewsValues() {
        try {
            titleTextView.setText(getString(R.string.pacient_details_pacient));
            pacientCardView.setVisibility(View.VISIBLE);
            bloodTypeTextView.setText(pacient.getMedicalRecord()
                                             .getBloodType());
            heightTextView.setText(String.valueOf(pacient.getMedicalRecord()
                                                         .getHeight()));
            weightTextView.setText(String.valueOf(pacient.getMedicalRecord()
                                                         .getWeight()));

            new DownloadImageTask(photoImageView)
                    .execute(pacient.getPhotoURL());
            nameTextView.setText(pacient.getName());
            emailTextView.setText(pacient.getEmail());
            phoneNumberTextView.setText(String.valueOf(pacient.getHouseNumber()));
            cellNumberTextView.setText(String.valueOf(pacient.getPhoneNumber()));
            addressTextView.setText(pacient.getAddress());
            memberSinceTextView.setText(new ApplicationDateFormat().toString(pacient.getAdmissionDate()));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                           ex.getMessage(),
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }
}
