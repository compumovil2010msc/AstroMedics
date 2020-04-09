package com.example.astromedics.views.common;

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
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.session.Session;

public class AccountDetailsActivity extends AppCompatActivity {
    Person person;
    ImageView photoImageView;
    CardView pacientCardView, therapistCardView;
    TextView titleTextView, nameTextView, emailTextView, phoneNumberTextView, cellNumberTextView, addressTextView, memberSinceTextView, bloodTypeTextView, heightTextView, weightTextView,
            numberOfAppointmentsTextView, calificationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        inflateViews();
        setViewsValues();
    }

    private void inflateViews() {
        pacientCardView = findViewById(R.id.account_details_pacient_card_view);
        therapistCardView = findViewById(R.id.account_details_therapist_card_view);
        photoImageView = findViewById(R.id.account_details_photo);
        titleTextView = findViewById(R.id.account_details_title);
        nameTextView = findViewById(R.id.account_details_name);
        emailTextView = findViewById(R.id.account_details_email);
        phoneNumberTextView = findViewById(R.id.account_details_phone_number);
        cellNumberTextView = findViewById(R.id.account_details_cell_number);
        addressTextView = findViewById(R.id.account_details_address);
        memberSinceTextView = findViewById(R.id.account_details_member_since);
        bloodTypeTextView = findViewById(R.id.account_details_pacient_blood_type);
        heightTextView = findViewById(R.id.account_details_pacient_height);
        weightTextView = findViewById(R.id.account_details_weight);
        numberOfAppointmentsTextView = findViewById(R.id.account_details_therapist_number_of_appointments);
        calificationTextView = findViewById(R.id.account_details_therapist_calification);
    }

    private void setViewsValues() {
        try {
            person = Repository.getInstance()
                               .getPersonRepository()
                               .get(Session.getInstance()
                                           .getEmail());

            if (person instanceof Pacient) {
                titleTextView.setText(getString(R.string.account_details_pacient));
                pacientCardView.setVisibility(View.VISIBLE);
                bloodTypeTextView.setText(((Pacient) person).getMedicalRecord()
                                                            .getBloodType());
                heightTextView.setText(String.valueOf(((Pacient) person)
                                                              .getMedicalRecord()
                                                              .getHeight()));
                weightTextView.setText(String.valueOf(((Pacient) person)
                                                              .getMedicalRecord()
                                                              .getWeight()));
            } else {
                titleTextView.setText(getString(R.string.account_details_therapist));
                therapistCardView.setVisibility(View.VISIBLE);
                numberOfAppointmentsTextView.setText(String.valueOf(((Therapist) person)
                                                                            .getNumberOfCompletedMedicalConsultations()));
                calificationTextView.setText(String.valueOf(((Therapist) person)
                                                                    .getAverageCalification()));
            }

            new DownloadImageTask(photoImageView)
                    .execute(person.getPhotoURL());
            nameTextView.setText(person.getName());
            emailTextView.setText(person.getEmail());
            phoneNumberTextView.setText(String.valueOf(person.getHouseNumber()));
            cellNumberTextView.setText(String.valueOf(person.getPhoneNumber()));
            addressTextView.setText(person.getAddress());
            memberSinceTextView.setText(new ApplicationDateFormat().toString(person.getAdmissionDate()));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                           ex.getMessage(),
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }
}
