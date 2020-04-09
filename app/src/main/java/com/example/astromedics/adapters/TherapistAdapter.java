package com.example.astromedics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.Therapist;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TherapistAdapter extends ArrayAdapter<Therapist> {

    private Context mContext;
    private List<Therapist> therapists = new ArrayList<>();

    public TherapistAdapter(Context context, List<Therapist> items) {
        super(context,
              0,
              items);
        mContext = context;
        therapists = items;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(mContext)
                                     .inflate(R.layout.list_view_therapist_search_result,
                                              parent,
                                              false);
        }

        Therapist currentTherapist = therapists.get(position);
        int numberOfAppointments = currentTherapist.getNumberOfCompletedMedicalConsultations();
        double calification = currentTherapist.getAverageCalification();
        DecimalFormat formatter = new DecimalFormat("#,###");

        ((TextView) listItem.findViewById(R.id.therapist_search_result_name)).setText(currentTherapist.getName());
        ((TextView) listItem.findViewById(R.id.therapist_search_result_price)).setText(formatter.format(currentTherapist.getMedicalConsultationPrice()) + " " + getContext().getString(R.string.therapist_adapter_currency));
        TextView numberOfAppointmentsTextView = listItem.findViewById(R.id.therapist_search_result_number_of_appointments);
        numberOfAppointmentsTextView.setText(numberOfAppointments > 0
                                                     ? String.valueOf(currentTherapist.getNumberOfCompletedMedicalConsultations()) + " " + getContext().getString(R.string.therapist_adapter_number_of_medical_consultations)
                                                     : getContext().getString(R.string.therapist_adapter_no_califications));
        if (calification > 0) {
            LinearLayout linearLayout = listItem.findViewById(R.id.therapist_search_result_calification);
            linearLayout.setVisibility(View.VISIBLE);

            int i = 0;
            for (i = 1; i <= calification; i++) {
                linearLayout.addView(getStarImageView());

                if (1 > calification - i && calification - i > 0.5) {
                    linearLayout.addView(getHalfStarImageView());
                }
            }
            TextView textView = new TextView(getContext());
            textView.setText("(" + calification + ")");
            textView.setTextColor(getContext().getColor(R.color.colorLight));
            linearLayout.addView(textView);
        }

        new DownloadImageTask((ImageView) listItem.findViewById(R.id.therapist_search_result_photo))
                .execute(currentTherapist.getPhotoURL());

        return listItem;
    }

    private ImageView getStarImageView() {
        ImageView returnable = new ImageView(getContext());
        returnable.setBackgroundResource(R.drawable.ic_star_black_24dp);
        return returnable;
    }

    private ImageView getHalfStarImageView() {
        ImageView returnable = new ImageView(getContext());
        returnable.setBackgroundResource(R.drawable.ic_star_half_black_24dp);
        return returnable;
    }
}
