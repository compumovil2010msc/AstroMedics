package com.example.astromedics.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pacient extends Person implements Serializable {
    private MedicalRecord medicalRecord;
    private List<MedicalConsultation> medicalHistory;

    public Pacient(){
        medicalHistory = new ArrayList<>();
    }

    public Pacient(int identificationNumber, String name, String photoURL, long houseNumber, long phoneNumber, String address, String email,
                   Date admissionDate, MedicalRecord medicalRecord, List<MedicalConsultation> medicalHistory) {
        super(identificationNumber,
              name,
              photoURL,
              houseNumber,
              phoneNumber,
              address,
              email,
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

    public void addMedicalHistory(MedicalConsultation medicalConsultation) {
        this.medicalHistory.add(medicalConsultation);
    }
}
