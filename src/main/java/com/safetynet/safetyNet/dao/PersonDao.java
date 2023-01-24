package com.safetynet.safetyNet.dao;


import com.safetynet.safetyNet.model.Person;


import java.util.List;


public interface PersonDao {

        List<Person> findAll();

        List<Person> getByAddress(String address);

        List<Person> getByCity(String city);


        List<Person> getEmailsByCity(String city);

        List<Person> getByName(String firstName, String lastName);

    Person save(Person person);

    List<Person> updatePerson();



}
