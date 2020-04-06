package com.example.astromedics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.model.old.TherapistAvailableAppointment;

import java.util.ArrayList;
import java.util.List;

public class TherapistAvailableAppointmentAdapter extends ArrayAdapter<TherapistAvailableAppointment> {

    private Context mContext;
    private List<TherapistAvailableAppointment> therapists = new ArrayList<>();

    public TherapistAvailableAppointmentAdapter(Context context, List<TherapistAvailableAppointment> items) {
        super(context, 0, items);
        mContext = context;
        therapists = items;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.therapist_available_appointment, parent, false);
        }

        TherapistAvailableAppointment currentAppointment = therapists.get(position);

        TextView hour = (TextView) listItem.findViewById(R.id.appointment_hour);
        hour.setText(currentAppointment.toString());

        return listItem;
    }
}
