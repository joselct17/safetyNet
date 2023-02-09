package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.model.Person;

import java.util.List;

public interface IPersonService {

    Person savePerson(Person person) throws Exception;

    Person updatePerson(Person person) throws Exception;

    void deletePerson(String firstname, String lastname);


    List<Person> getAllPerson();
}
