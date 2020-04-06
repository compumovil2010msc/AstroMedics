package com.example.astromedics.repository;

import com.example.astromedics.model.Person;

public interface PersonRepository {
    Person login(String user, String password);
}
