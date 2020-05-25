package com.example.astromedics.model.dto;

import com.example.astromedics.model.Localization;

public class CurrentLocation {
    private String uniqueId;
    private Localization targetLocation;
    private Localization currentLocation;

    public CurrentLocation(String uniqueId, Localization targetLocation, Localization currentLocation) {
        this.uniqueId = uniqueId;
        this.targetLocation = targetLocation;
        this.currentLocation = currentLocation;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Localization getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Localization targetLocation) {
        this.targetLocation = targetLocation;
    }

    public Localization getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Localization currentLocation) {
        this.currentLocation = currentLocation;
    }
}
