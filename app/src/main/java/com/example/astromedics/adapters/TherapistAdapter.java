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
import com.example.astromedics.model.Therapist;

import java.util.ArrayList;
import java.util.List;

public class TherapistAdapter extends ArrayAdapter<Therapist> {

    private Context mContext;
    private List<Therapist> therapists = new ArrayList<>();

    public TherapistAdapter(Context context, List<Therapist> items) {
        super(context, 0, items);
        mContext = context;
        therapists = items;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view_therapist_search_result, parent, false);
        }

        Therapist currentTherapist = therapists.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.SearchResultName);
        name.setText(currentTherapist.getName());

        TextView numberOfAppointments = (TextView) listItem.findViewById(R.id.SearchResultNumberOfAppointments);
        numberOfAppointments.setText(String.valueOf(currentTherapist.getNumberOfAppointments()));

        return listItem;
    }
}
