package com.example.astromedics.model;

import android.content.Context;

import com.example.astromedics.R;
import com.example.astromedics.helpers.DateComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Therapist extends Person implements Serializable {
    private int medicalConsultationPrice;
    private List<Emphasis> emphasis;
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
                     String password, Date admissionDate, int medicalConsultationPrice, List<Emphasis> emphasis,
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
        this.medicalConsultationPrice = medicalConsultationPrice;
        this.emphasis = emphasis;
        this.educationalFormation = educationalFormation;
        this.appointments = appointments;
    }

    public int getMedicalConsultationPrice() {
        return medicalConsultationPrice;
    }

    public void setMedicalConsultationPrice(int medicalConsultationPrice) {
        this.medicalConsultationPrice = medicalConsultationPrice;
    }

    public List<Emphasis> getEmphasis() {
        return emphasis;
    }

    public void setEmphasis(List<Emphasis> emphasis) {
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

    public int getNumberOfCompletedMedicalConsultations() {
        int returnable = 0;

        for (Appointment appointment : getAppointments()) {
            if (appointment.getMedicalConsultation() != null && appointment.getMedicalConsultation()
                                                                           .getCalification() > 0 && appointment.getEndDate()
                                                                                                                .before(new Date())) {
                returnable++;
            }
        }

        return returnable;
    }

    public double getAverageCalification() {
        int numberOfAppointments = 0;
        double sumOfCalifications = 0;

        for (Appointment appointment : getAppointments()) {
            if (appointment.getMedicalConsultation() != null && appointment.getMedicalConsultation()
                                                                           .getCalification() > 0 && appointment.getEndDate()
                                                                                                                .before(new Date())) {
                numberOfAppointments++;
                sumOfCalifications += appointment.getMedicalConsultation()
                                                 .getCalification();
            }
        }

        return numberOfAppointments > 0 ? sumOfCalifications / numberOfAppointments : 0;
    }

    public List<Date> getAvailableDays(Date startDate, Date endDate) {
        DateComparator dateComparator = new DateComparator();

        for (Appointment appointment : getAppointments()) {
            if (appointment.getMedicalConsultation() == null
                    && new DateComparator().between(appointment.getStartDate(), startDate, endDate)
                    && Collections.binarySearch(returnable,
                                                appointment.getStartDate(),
                                                dateComparator) < 0 && appointment.getStartDate()
                                                                                  .after(new Date())) {
                returnable.add(appointment.getStartDate());
            }
        }

        Collections.sort(returnable);

        return returnable;
    }

    public List<Date> getAvailableDays() {
        List<Date> returnable = new ArrayList<>();
        DateComparator dateComparator = new DateComparator();

        for (Appointment appointment : getAppointments()) {
            if (appointment.getMedicalConsultation() == null && Collections.binarySearch(returnable,
                                                                                         appointment.getStartDate(),
                                                                                         dateComparator) < 0 && appointment.getStartDate()
                                                                                                                           .after(new Date())) {
                returnable.add(appointment.getStartDate());
            }
        }

        Collections.sort(returnable);

        return returnable;
    }

    public List<Appointment> getAvailableAppointmentsByDate(Date date) {
        List<Appointment> returnable = new ArrayList<>();
        DateComparator dateComparator = new DateComparator();

        for (Appointment appointment : getAppointments()) {
            if (appointment.getMedicalConsultation() == null
                    && dateComparator.equals(appointment.getStartDate(),
                                             date)) {
                returnable.add(appointment);
            }
        }

        return returnable;
    }
}
