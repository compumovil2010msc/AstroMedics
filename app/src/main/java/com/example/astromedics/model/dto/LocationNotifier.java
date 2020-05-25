package com.example.astromedics.model.dto;

public class LocationNotifier {
    private String uniqueId;
    private String email;
    private Boolean alreadyNotified;

    public LocationNotifier() {
        alreadyNotified = false;
    }

    public LocationNotifier(String uniqueId, String email) {
        this.uniqueId = uniqueId;
        this.email = email;
        this.alreadyNotified = false;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAlreadyNotified() {
        return alreadyNotified;
    }

    public void setAlreadyNotified(Boolean alreadyNotified) {
        this.alreadyNotified = alreadyNotified;
    }
}
