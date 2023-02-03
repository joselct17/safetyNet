package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.model.Person;

public interface IPersonService {

    Person savePerson(Person person) throws Exception;

    Person updatePerson(Person person) throws Exception;

    void deletePerson(String firstname, String lastname);


}
