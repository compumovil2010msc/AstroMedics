package com.example.astromedics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class TherapistMedicalConsultationAdapter extends ArrayAdapter<MedicalConsultation> {

    private Context mContext;
    private List<MedicalConsultation> medicalConsultations = new ArrayList<>();

    public TherapistMedicalConsultationAdapter(Context context, List<MedicalConsultation> items) {
        super(context,
              0,
              items);
        mContext = context;
        medicalConsultations = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext)
                                 .inflate(R.layout.list_view_medical_consultation,
                                          parent,
                                          false);
        }
        MedicalConsultation medicalConsultation = medicalConsultations.get(position);

        try {
            Pacient pacient = Repository.getInstance()
                                        .getPacientRepository()
                                        .getPacient(medicalConsultation);
            if (medicalConsultation != null && pacient != null && medicalConsultation.getAppointment() != null) {
                ((TextView) view.findViewById(R.id.medical_consultation_name))
                        .setText(pacient.getName());

                ((TextView) view.findViewById(R.id.medical_consultation_emphasis))
                        .setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                             getContext()));

                ((TextView) view.findViewById(R.id.medical_consultation_date))
                        .setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                         .getStartDate()));

                new DownloadImageTask((ImageView) view.findViewById(R.id.medical_consultation_photo))
                        .execute(pacient.getPhotoURL());
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(),
                           ex.getMessage(),
                           Toast.LENGTH_SHORT)
                 .show();
        }

        return view;
    }
}
