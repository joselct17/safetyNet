package com.safetynet.safetyNet.json;

import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JsonReader {

    public JsonReader() throws ParseException, IOException, FileNotFoundException {

        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/main/resources/JSON/data.JSON");

        Object jsonObj = parser.parse(reader);

        JSONObject jsonObject = (JSONObject) jsonObj;

        JSONArray persons = (JSONArray) jsonObject.get("persons");
        Iterator<Person> per = persons.iterator();
        while (per.hasNext()) {
            System.out.println("Person = " + per.next());
        }

        JSONArray firestations = (JSONArray) jsonObject.get("firestations");
        Iterator<FireStation> fire = firestations.iterator();
        while (fire.hasNext()) {
            System.out.println("Firestation = " + fire.next());
        }

        JSONArray medicalrecords = (JSONArray) jsonObject.get("medicalrecords");
        Iterator<MedicalRecords> medic = medicalrecords.iterator();
        while (medic.hasNext()) {
            System.out.println("Medicalrecords = " + medic.next());
        }
        reader.close();
    }

}
