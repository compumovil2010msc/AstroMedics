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
import com.example.astromedics.old.TherapistSearchResult;

import java.util.ArrayList;
import java.util.List;

public class TherapistSearchResultAdapter  extends ArrayAdapter<TherapistSearchResult> {

    private Context mContext;
    private List<TherapistSearchResult> therapists = new ArrayList<>();

    public TherapistSearchResultAdapter(Context context, List<TherapistSearchResult> items) {
        super(context, 0, items);
        mContext = context;
        therapists = items;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.therapist_search_result, parent, false);
        }

        TherapistSearchResult currentTherapist = therapists.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.SearchResultName);
        name.setText(currentTherapist.getName());

        TextView numberOfAppointments = (TextView) listItem.findViewById(R.id.SearchResultNumberOfAppointments);
        numberOfAppointments.setText(String.valueOf(currentTherapist.getNumberOfAppointments()));

        return listItem;
    }
}
