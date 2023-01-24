package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class PersonDaoImpl implements PersonDao  {

    private final ArrayList<Person> listPersons;


    public PersonDaoImpl(ArrayList<Person> listPersons) throws IOException, ParseException {
        this.listPersons = listPersons;


        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/main/resources/JSON/data.JSON");

        Object jsonObj = parser.parse(reader);

        JSONObject jsonObject = (JSONObject) jsonObj;

        JSONArray  persons = (JSONArray ) jsonObject.get("persons");


        Iterator<JSONObject> i = persons.iterator();

        while (i.hasNext()) {

            JSONObject o = i.next();
            Person person = new Person();
            person.setFirstName((String)o.get("firstName"));
            person.setLastName((String)o.get("lastName"));
            person.setAddress((String)o.get("address"));
            person.setCity((String)o.get("city"));
            person.setZip((String)o.get("zip"));
            person.setTelephone((String)o.get("phone"));
            person.setEmail((String)o.get("email"));

            listPersons.add(person);

        }

    }


    @Override
    public List<Person> findAll() {

        return listPersons;
    }


    @Override
    public List<Person> getByName(String firstName, String lastName) {
        return listPersons.stream()
                .filter(person -> person.getFirstName().equals(firstName))
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getByAddress(String address) {
        return listPersons.stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }


    @Override
    public List<Person> getByCity(String city) {
        return listPersons.stream()
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
        listPersons.add(person);
        return person;
    }

    @Override
    public List<Person> updatePerson() {
        return null;
    }

   /** public List<Person> updatePerson(String firstName, String lastName) {
        Set<String> Persons = new HashSet<>();
        for (Person person : getByName(firstName, lastName)) {
            Persons.add(person.getFirstName());
            Persons.add(person.getLastName());
        }
        JSONArray updatePersons = new JSONArray();

        updatePersons.add(Persons);


        return updatePersons ;
    } */


}
