package com.example.astromedics.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.EducationalFormation;

import java.util.ArrayList;
import java.util.List;

public class EducationalFormationAdapter extends RecyclerView.Adapter<EducationalFormationAdapter.EducationalFormationViewHolder> {

    private List<EducationalFormation> educationalFormation = new ArrayList<>();

    public EducationalFormationAdapter(List<EducationalFormation> items) {
        educationalFormation = items;
    }

    @NonNull
    @Override
    public EducationalFormationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_view_educational_formation,
                                           parent,
                                           false);
        return new EducationalFormationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationalFormationViewHolder holder, int position) {
        holder.setValues(educationalFormation.get(position));
    }

    @Override
    public int getItemCount() {
        return educationalFormation.size();
    }

    public static class EducationalFormationViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, institutionTextView, startDateTextView, endDateTextView, graduatedTextView;
        private View view;

        public EducationalFormationViewHolder(View view) {
            super(view);
            this.view = view;
            inflateViews();
        }

        public void inflateViews() {
            titleTextView = view.findViewById(R.id.educational_formation_title);
            institutionTextView = view.findViewById(R.id.educational_formation_institution);
            startDateTextView = view.findViewById(R.id.educational_formation_start_date);
            endDateTextView = view.findViewById(R.id.educational_formation_end_date);
            graduatedTextView = view.findViewById(R.id.educational_formation_graduated);
        }

        public void setValues(EducationalFormation educationalFormation) {
            titleTextView.setText(educationalFormation.getTitle());
            institutionTextView.setText(educationalFormation.getInstitution());
            startDateTextView.setText(new ApplicationDateFormat().toString(educationalFormation.getStartDate()));
            endDateTextView.setText(educationalFormation.getEnDate() != null ? new ApplicationDateFormat().toString(educationalFormation.getEnDate()): "No graduado");
            graduatedTextView.setText(educationalFormation.isGraduated()
                                              ? view.getContext()
                                                    .getString(R.string.educational_formation_graduated_yes)
                                              : view.getContext()
                                                    .getString(R.string.educational_formation_graduated_no));
        }
    }
}
