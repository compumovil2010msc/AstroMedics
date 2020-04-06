package com.example.astromedics.old;

public class TherapistSearchResult {
    private String name;
    private int numberOfAppointments;
    private double note;

    public TherapistSearchResult(String name, int numberOfAppointments, double note){
        this.name = name;
        this.numberOfAppointments = numberOfAppointments;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
