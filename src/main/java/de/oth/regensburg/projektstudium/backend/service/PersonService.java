package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    Person findById(Long id);

    Person addPerson(Person newPerson);
}
