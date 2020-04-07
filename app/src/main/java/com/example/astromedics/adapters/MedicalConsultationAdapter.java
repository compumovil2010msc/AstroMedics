package com.example.astromedics.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.io.InputStream;
import java.util.List;

public class MedicalConsultationAdapter extends ArrayAdapter<MedicalConsultation> {
    private int resourceLayout;

    public MedicalConsultationAdapter(Context context, int resource, List<MedicalConsultation> items) {
        super(context,
              resource,
              items);
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(this.resourceLayout,
                                          null);
        }
        MedicalConsultation medicalConsultation = getItem(position);
        Therapist therapist = Repository.getInstance()
                                        .getTherapistRepository()
                                        .getTherapist(medicalConsultation);
        Appointment appointment = null;

        for (Appointment auxiliarAppointment : therapist.getAppointments()) {
            if (auxiliarAppointment.getMedicalConsultation() != null && auxiliarAppointment.getMedicalConsultation()
                           .getMedicalConsultationId() == medicalConsultation.getMedicalConsultationId()) {
                appointment = auxiliarAppointment;
            }
        }

        if (medicalConsultation != null && therapist != null && appointment != null) {
            ((TextView) view.findViewById(R.id.medical_consultation_name))
                    .setText(therapist.getName());

            ((TextView) view.findViewById(R.id.medical_consultation_emphasis))
                    .setText(medicalConsultation.getEmphasis());

            ((TextView) view.findViewById(R.id.medical_consultation_date))
                    .setText(appointment
                                     .getStartDate()
                                     .toString());

            new DownloadImageTask((ImageView) view.findViewById(R.id.medical_consultation_photo))
                    .execute(therapist.getPhotoURL());
        }
        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
