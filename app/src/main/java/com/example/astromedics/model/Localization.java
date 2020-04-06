package com.example.astromedics.model;

public class Localization {
    private int localizationId;
    private double latitude;
    private double longitude;
    private String name;

    public Localization(int localizationId, double latitude, double longitude, String name) {
        this.localizationId = localizationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public int getLocalizationId() {
        return localizationId;
    }

    public void setLocalizationId(int localizationId) {
        this.localizationId = localizationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
