package com.example.astromedics.repository.interfaces;

import com.example.astromedics.model.Person;

public interface PersonRepository {
    Person get(String email) throws Exception;
}
