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
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.EducationalFormation;

import java.util.ArrayList;
import java.util.List;

public class EducationalFormationAdapter extends ArrayAdapter<EducationalFormation> {

    private Context mContext;
    private List<EducationalFormation> educationalFormation = new ArrayList<>();

    public EducationalFormationAdapter(Context context, List<EducationalFormation> items) {
        super(context,
              0,
              items);
        mContext = context;
        educationalFormation = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext)
                                 .inflate(R.layout.list_view_educational_formation,
                                          parent,
                                          false);
        }
        EducationalFormation educationalFormation = this.educationalFormation.get(position);

        ((TextView) view.findViewById(R.id.educational_formation_title))
                .setText(educationalFormation.getTitle());

        ((TextView) view.findViewById(R.id.educational_formation_institution))
                .setText(educationalFormation.getInstitution());

        ((TextView) view.findViewById(R.id.educational_formation_start_date))
                .setText(new ApplicationDateFormat().toString(educationalFormation.getStartDate()));

        ((TextView) view.findViewById(R.id.educational_formation_end_date))
                .setText(new ApplicationDateFormat().toString(educationalFormation.getEnDate()));

        ((TextView) view.findViewById(R.id.educational_formation_graduated))
                .setText(educationalFormation.isGraduated() ? getContext().getString(R.string.educational_formation_graduated_yes)
                                 : getContext().getString(R.string.educational_formation_graduated_no));

        return view;
    }
}
