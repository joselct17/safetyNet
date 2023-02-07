package com.safetynet.safetyNet.dao;


import com.safetynet.safetyNet.model.Person;


import java.util.List;


public interface IPersonDao {

        List<Person> findAll();

        List<Person> getByAddress(String address);

        List<Person> getByLastName(String lastName);

        List<Person> getByCity(String city);



        Person getByName(String firstName, String lastName);





    Person save(Person person);

    Person update(Person person);


    void delete(String firstname, String lastname);
}
