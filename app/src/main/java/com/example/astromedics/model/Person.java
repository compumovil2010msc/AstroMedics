package com.example.astromedics.model;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
    private int identificationNumber;
    private String name;
    private String photoURL;
    private long houseNumber;
    private long phoneNumber;
    private String address;
    private String email;
    private String password;
    private Date admissionDate;

    public Person(int identificationNumber, String name, String photoURL, long houseNumber, long phoneNumber, String address, String email,
                  String password, Date admissionDate) {
        this.identificationNumber = identificationNumber;
        this.name = name;
        this.photoURL = photoURL;
        this.houseNumber = houseNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.admissionDate = admissionDate;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }
}
