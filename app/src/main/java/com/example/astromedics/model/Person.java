package com.example.astromedics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("isDoctor")
    @Expose
    private boolean isDoctor;

    public Person(String email, String name, boolean isDoctor) {
        this.email = email;
        this.name = name;
        this.isDoctor=isDoctor;
    }

    public Person(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Person(){
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

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", isDoctor=" + isDoctor +
                '}';
    }
}
