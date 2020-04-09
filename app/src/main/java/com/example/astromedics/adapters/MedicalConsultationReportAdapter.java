package com.example.astromedics.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MedicalConsultationReportAdapter extends RecyclerView.Adapter<MedicalConsultationReportAdapter.MedicalConsultationReportViewHolder> {

    private List<MedicalConsultation> medicalConsultations = new ArrayList<>();
    private MedicalConsultationReportAdapter.OnItemClickListener onItemClickListener;

    public MedicalConsultationReportAdapter(List<MedicalConsultation> items) {
        medicalConsultations = items;
        this.onItemClickListener = null;
    }

    public void setOnItemClickListener(MedicalConsultationReportAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MedicalConsultationReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_view_medical_consultation_report,
                                           parent,
                                           false);
        return new MedicalConsultationReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalConsultationReportViewHolder holder, int position) {
        holder.setValues(medicalConsultations.get(position),
                         onItemClickListener,
                         position);
    }

    @Override
    public int getItemCount() {
        return medicalConsultations.size();
    }

    public static class MedicalConsultationReportViewHolder extends RecyclerView.ViewHolder {
        public TextView therapistNameTextView, emphasisTextView, dateTextView;
        private View view;

        public MedicalConsultationReportViewHolder(View view) {
            super(view);
            this.view = view;
            inflateViews();
        }

        public void inflateViews() {
            therapistNameTextView = view.findViewById(R.id.medical_consultation_name);
            emphasisTextView = view.findViewById(R.id.medical_consultation_emphasis);
            dateTextView = view.findViewById(R.id.medical_consultation_date);
        }

        public void setValues(MedicalConsultation medicalConsultation, OnItemClickListener onItemClickListener,
                              int position) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            try {
                Therapist therapist = Repository.getInstance()
                                                .getTherapistRepository()
                                                .getTherapist(medicalConsultation);
                if (medicalConsultation != null && therapist != null && medicalConsultation.getAppointment() != null) {
                    therapistNameTextView.setText(therapist.getName());
                    emphasisTextView.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                                                        view.getContext()));
                    dateTextView.setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                                 .getStartDate()));
                }
            } catch (Exception ex) {
                Toast.makeText(view.getContext(),
                               ex.getMessage(),
                               Toast.LENGTH_SHORT)
                     .show();
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
