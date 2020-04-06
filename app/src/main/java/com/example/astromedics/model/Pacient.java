package com.example.astromedics.model;

import java.util.Date;
import java.util.List;

public class Pacient extends Person{
    private MedicalRecord medicalRecord;
    private List<MedicalConsultation> medicalHistory;

    public Pacient(int personId, int identificationNumber, String name, long houseNumber, long phoneNumber, String address, String email,
                   String user, String password, Date admissionDate, MedicalRecord medicalRecord,
                   List<MedicalConsultation> medicalHistory) {
        super(personId,
              identificationNumber,
              name,
              houseNumber,
              phoneNumber,
              address,
              email,
              user,
              password,
              admissionDate);
        this.medicalRecord = medicalRecord;
        this.medicalHistory = medicalHistory;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public List<MedicalConsultation> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<MedicalConsultation> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
