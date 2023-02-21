package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.Person;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class PersonDaoImpl implements IPersonDao {


    private ArrayList<Person> listPersons;
    @Autowired
    private final JsonReader jsonReader;


    public PersonDaoImpl(JsonReader jsonReader)  {

        this.jsonReader = jsonReader;
    }

    protected void setPersonList(ArrayList<Person> listPersons) {
        this.jsonReader.listPersons = listPersons;
    }


    @Override
    public List<Person> findAll() {

        return jsonReader.listPersons;
    }


    @Override
    public Person getByName(String firstName, String lastName) {
        List<Person> result = jsonReader.listPersons.stream()
                .filter(person -> (person.getFirstName().equals(firstName) &&
                person.getLastName().equals(lastName)))
                .collect(Collectors.toList());
        if (result.size()==1) {
            return result.get(0);
        }
        else return null;
    }

    @Override
    public List<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return jsonReader.listPersons.stream()
                .filter(person -> (person.getFirstName().equals(firstName)
                        && person.getLastName().equals(lastName)))
                .collect(Collectors.toList());
    }


    @Override
    public List<Person> getByAddress(String address) {
        return jsonReader.listPersons.stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getByLastName(String lastName) {
        return jsonReader.listPersons.stream()
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }


    @Override
    public List<Person> getByCity(String city) {
        return jsonReader.listPersons.stream()
                .filter(person -> person.getCity().equals(city))
                .collect(Collectors.toList());
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

    @Override
    public void delete(String firstName, String lastName) {
            jsonReader.listPersons.removeIf
                    (person -> (person.getFirstName().equals(firstName)
                            && person.getLastName().equals(lastName)));

    }


}
