package com.example.astromedics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MedicalConsultationAdapter extends ArrayAdapter<MedicalConsultation> {

    private Context mContext;
    private List<MedicalConsultation> medicalConsultations = new ArrayList<>();

    public MedicalConsultationAdapter(Context context, List<MedicalConsultation> items) {
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
        Therapist therapist = Repository.getInstance()
                                        .getTherapistRepository()
                                        .getTherapist(medicalConsultation);

        if (medicalConsultation != null && therapist != null && medicalConsultation.getAppointment() != null) {
            ((TextView) view.findViewById(R.id.medical_consultation_name))
                    .setText(therapist.getName());

            ((TextView) view.findViewById(R.id.medical_consultation_emphasis))
                    .setText(Therapist.Emphasis.speech_therapy.toString(medicalConsultation.getEmphasis(),
                                                                        getContext()));

            ((TextView) view.findViewById(R.id.medical_consultation_date))
                    .setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                     .getStartDate()));

            new DownloadImageTask((ImageView) view.findViewById(R.id.medical_consultation_photo))
                    .execute(therapist.getPhotoURL());
        }
        return view;
    }
}
