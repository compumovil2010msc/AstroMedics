package com.example.astromedics.model;

import java.util.List;

public class MedicalRecord {
    private int medicalRecordId;
    private String bloodType;
    private double weight;
    private double height;
    private List<EvaluationQuestion> evaluationQuestions;

    public MedicalRecord(int medicalRecordId, String bloodType, double weight, double height,
                         List<EvaluationQuestion> evaluationQuestions) {
        this.medicalRecordId = medicalRecordId;
        this.bloodType = bloodType;
        this.weight = weight;
        this.height = height;
        this.evaluationQuestions = evaluationQuestions;
    }

    public int getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(int medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<EvaluationQuestion> getEvaluationQuestions() {
        return evaluationQuestions;
    }

    public void setEvaluationQuestions(List<EvaluationQuestion> evaluationQuestions) {
        this.evaluationQuestions = evaluationQuestions;
    }
}
