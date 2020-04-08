package com.example.astromedics.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class TherapistAvailableAppointmentAdapter extends RecyclerView.Adapter<TherapistAvailableAppointmentAdapter.TherapistAvailableAppointmentViewHolder> {

    private List<Appointment> therapists = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public TherapistAvailableAppointmentAdapter(List<Appointment> items) {
        therapists = items;
        this.onItemClickListener = null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TherapistAvailableAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_view_available_appointment,
                                           parent,
                                           false);
        return new TherapistAvailableAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TherapistAvailableAppointmentViewHolder holder, int position) {
        holder.setValues(therapists.get(position), onItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return therapists.size();
    }

    public static class TherapistAvailableAppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView hourTextView;
        private View view;

        public TherapistAvailableAppointmentViewHolder(View view) {
            super(view);
            this.view = view;
            inflateViews();
        }

        public void inflateViews() {
            hourTextView = view.findViewById(R.id.available_appointment_hour);
        }

        public void setValues(Appointment appointment, OnItemClickListener onItemClickListener, int position) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            ApplicationDateFormat applicationDateFormat = new ApplicationDateFormat();
            hourTextView.setText(applicationDateFormat.getHoursAndMinutes(appointment.getStartDate()) + " - " + applicationDateFormat.getHoursAndMinutes(appointment.getEndDate()));
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
