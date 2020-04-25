package com.example.astromedics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("isDoctor")
    @Expose
    private boolean isDoctor;

    private int identificationNumber;
    private String photoURL;
    private long houseNumber;
    private long phoneNumber;
    private String address;
    private Date admissionDate;

    public Person(int identificationNumber, String name, String photoURL, long houseNumber, long phoneNumber, String address, String email,
                  Date admissionDate) {
        this.email = email;
        this.name = name;
        this.identificationNumber = identificationNumber;
        this.photoURL = photoURL;
        this.houseNumber = houseNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.admissionDate = admissionDate;
    }

    public Person(String email, String name, boolean isDoctor) {
        this.email = email;
        this.name = name;
        this.isDoctor = isDoctor;
    }

    public Person(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Person() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", isDoctor=" + isDoctor +
                '}';
    }
}
