package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class PersonDaoImpl implements PersonDao  {


    @Autowired
    private final JsonReader jsonReader;

    public PersonDaoImpl( JsonReader jsonReader)  {

        this.jsonReader = jsonReader;

    }

    @Override
    public List<Person> findAll() {

        return jsonReader.listPersons;
    }


    @Override
    public List<Person> getByName(String firstName, String lastName) {
        return jsonReader.listPersons.stream()
                .filter(person -> person.getFirstName().equals(firstName))
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }



    @Override
    public List<Person> getByAddress(String address) {
        return jsonReader.listPersons.stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getByFirestationAddress(String address) {

        return null;
    }

    @Override
    public List<Person> getChildsByAddress(String address) {
        List<String> childs = new ArrayList();
        for (Person person : getByAddress(address)) {
            childs.add(person.getFirstName() + person.getLastName());
        }
        JSONArray childsByAddress = new JSONArray();

        childsByAddress.add(childs);

        return childsByAddress;
    }


    @Override
    public List<Person> getByCity(String city) {
        return jsonReader.listPersons.stream()
                .filter(person -> person.getCity().equals(city))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getEmailsByCity(String city) {
        Set<String> emails = new HashSet<>();
        for(Person person : getByCity(city)) {
            emails.add(person.getEmail());
        }
        JSONArray emailsByCity = new JSONArray();

        emailsByCity.add(emails);


        return emailsByCity;

    }



    @Override
    public Person save(Person person) {
        jsonReader.listPersons.add(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        for (Person p : jsonReader.listPersons) {
            if (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())) {
                    p.setAddress(person.getAddress());
                    p.setCity(person.getCity());
                    p.setEmail(person.getEmail());
                    p.setTelephone(person.getTelephone());
                    p.setZip(person.getZip());
            }
        }
        return person;
    }




}
