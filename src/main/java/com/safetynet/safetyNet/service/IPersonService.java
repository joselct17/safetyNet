package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.model.Person;

public interface IPersonService {

    Person savePerson(Person person);

    Person updatePerson(Person person);

    void deletePerson(String firstname, String lastname);


}
