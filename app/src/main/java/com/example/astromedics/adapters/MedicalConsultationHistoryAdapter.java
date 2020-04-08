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
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MedicalConsultationHistoryAdapter extends ArrayAdapter<MedicalConsultation> {

    private Context mContext;
    private List<MedicalConsultation> medicalConsultations = new ArrayList<>();

    public MedicalConsultationHistoryAdapter(Context context, List<MedicalConsultation> items) {
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
                                 .inflate(R.layout.list_view_medical_consultation_history,
                                          parent,
                                          false);
        }
        MedicalConsultation medicalConsultation = medicalConsultations.get(position);

        try {
            Therapist therapist = Repository.getInstance()
                                            .getTherapistRepository()
                                            .getTherapist(medicalConsultation);
            if (medicalConsultation != null && therapist != null && medicalConsultation.getAppointment() != null) {
                ((TextView) view.findViewById(R.id.medical_consultation_history_name))
                        .setText(therapist.getName());

                ((TextView) view.findViewById(R.id.medical_consultation_history_emphasis))
                        .setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                             getContext()));

                ((TextView) view.findViewById(R.id.medical_consultation_history_date))
                        .setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                         .getStartDate()));


                ((TextView) view.findViewById(R.id.medical_consultation_history_note))
                        .setText(medicalConsultation.getCalification() == 0
                                         ? getContext().getString(R.string.history_no_calification)
                                         : getContext().getString(R.string.history_calification) + " " + String.valueOf(medicalConsultation.getCalification()));

                if (medicalConsultation.getReport() != null) {
                    ((TextView) view.findViewById(R.id.medical_consultation_history_report_available)).setVisibility(View.VISIBLE);
                    ((ImageView) view.findViewById(R.id.medical_consultation_history_icon)).setImageDrawable(getContext().getDrawable(R.drawable.ic_attachment_black_24dp));
                }
                if (medicalConsultation.getCalification() == 0) {
                    ((ImageView) view.findViewById(R.id.medical_consultation_history_icon)).setImageDrawable(getContext().getDrawable(R.drawable.ic_warning_black_24dp));
                }

                new DownloadImageTask((ImageView) view.findViewById(R.id.medical_consultation_history_photo))
                        .execute(therapist.getPhotoURL());
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
