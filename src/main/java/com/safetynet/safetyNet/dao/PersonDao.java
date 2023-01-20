package com.safetynet.safetyNet.dao;


import com.safetynet.safetyNet.model.Person;


import java.util.List;


public interface PersonDao {

        List<Person> findAll();


        List<Person> getEmails();

      /**
        static Person findByName(String firstName, String lastName) {

                return null;
        } */


        Person save(Person person);



}
