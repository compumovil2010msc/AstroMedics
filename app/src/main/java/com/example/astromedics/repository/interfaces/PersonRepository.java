package com.example.astromedics.repository.interfaces;

import com.example.astromedics.model.Person;

public interface PersonRepository {
    Person login(String email, String password) throws Exception;

    Person get(String email) throws Exception;

    void changePassword(Person person, String currentPassword, String newPassword, String repeatNewPassword) throws Exception;
}
