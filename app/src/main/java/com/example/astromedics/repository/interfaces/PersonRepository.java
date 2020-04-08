package com.example.astromedics.repository.interfaces;

import com.example.astromedics.model.Person;

public interface PersonRepository {
    Person login(String email, String password) throws Exception;
}
