package com.example.astromedics.model.dto;

import com.example.astromedics.helpers.ApplicationDateFormat;

import java.util.Date;

public class ApplicationDate {

    private Date date;

    public ApplicationDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new ApplicationDateFormat().toString(this.date);
    }
}
