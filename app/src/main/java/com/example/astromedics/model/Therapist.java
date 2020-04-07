package com.example.astromedics.model;

import android.content.Context;

import com.example.astromedics.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Therapist extends Person implements Serializable {
    private List<String> emphasis;
    private List<EducationalFormation> educationalFormation;
    private List<Appointment> appointments;

    public enum Emphasis {
        undefined_therapy,
        speech_therapy,
        physiotherapy,
        psychology;

        public static List<String> getAllEmphasis(Context context) {
            List<String> emphasis = new ArrayList<>();
            emphasis.add(Emphasis.toString(speech_therapy,
                                           context));
            emphasis.add(Emphasis.toString(physiotherapy,
                                           context));
            emphasis.add(Emphasis.toString(psychology,
                                           context));
            return emphasis;
        }

        public static Emphasis fromString(String value, Context context) {
            Emphasis returnable = undefined_therapy;

            if (value == context.getString(R.string.emphasis_speech_therapy)) {
                returnable = speech_therapy;
            } else if (value == context.getString(R.string.emphasis_physiotherapy)) {
                returnable = physiotherapy;
            } else if (value == context.getString(R.string.emphasis_psychology)) {
                returnable = psychology;
            }
            return returnable;
        }

        public static String toString(Emphasis emphasis, Context context) {
            String returnable = "";

            switch (emphasis) {
                case speech_therapy:
                    returnable = context.getString(R.string.emphasis_speech_therapy);
                    break;
                case physiotherapy:
                    returnable = context.getString(R.string.emphasis_physiotherapy);
                    break;
                case psychology:
                    returnable = context.getString(R.string.emphasis_psychology);
                    break;
            }

            return returnable;
        }
    }

    public Therapist(int identificationNumber, String name, String photoURL, long houseNumber, long phoneNumber, String address, String email,
                     String password, Date admissionDate, List<String> emphasis,
                     List<EducationalFormation> educationalFormation, List<Appointment> appointments) {
        super(identificationNumber,
              name,
              photoURL,
              houseNumber,
              phoneNumber,
              address,
              email,
              password,
              admissionDate);
        this.emphasis = emphasis;
        this.educationalFormation = educationalFormation;
        this.appointments = appointments;
    }

    public List<String> getEmphasis() {
        return emphasis;
    }

    public void setEmphasis(List<String> emphasis) {
        this.emphasis = emphasis;
    }

    public List<EducationalFormation> getEducationalFormation() {
        return educationalFormation;
    }

    public void setEducationalFormation(List<EducationalFormation> educationalFormation) {
        this.educationalFormation = educationalFormation;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public int getNumberOfAppointments() {
        int returnable = 0;

        for (Appointment appointment : getAppointments()) {
            if (appointment.getMedicalConsultation() != null && appointment.getMedicalConsultation()
                                                                           .getCalification() > 0) {
                returnable++;
            }
        }

        return returnable;
    }
}
