package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
    public List<Person> getEmails() {

        for (Person person : listPersons) {
            List<Person> emails = new ArrayList<>();
            person.getEmail() ;
            System.out.println(person.getEmail());

            return emails;
        }
        return null;
    }

    /**
    @Override
    public void findByName(String firstName, String lastName) {
        return null;
    } */



    @Override
    public Person save(Person person) {
        listPersons.add(person);
        return person;
    }


}
