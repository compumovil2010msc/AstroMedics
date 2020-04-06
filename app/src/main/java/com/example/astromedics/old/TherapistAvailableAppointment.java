package com.example.astromedics.old;

public class TherapistAvailableAppointment {
    private int startHour;
    private int startMinutes;
    private int endHour;
    private int endMinutes;

    public TherapistAvailableAppointment(int startHour, int startMinutes, int endHour, int endMinutes) {
        this.startHour = startHour;
        this.startMinutes = startMinutes;
        this.endHour = endHour;
        this.endMinutes = endMinutes;
    }

    public String toString(){
        return String.format("%02d", startHour) + ":" + String.format("%02d", startMinutes) + " - " + String.format("%02d", endHour) + ":" + String.format("%02d", endMinutes);
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
    }
}
