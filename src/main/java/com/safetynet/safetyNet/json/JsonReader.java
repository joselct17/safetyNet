package com.safetynet.safetyNet.json;

import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import com.safetynet.safetyNet.service.FloodResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
@Component
public class JsonReader {

    public ArrayList<Person> listPersons;

    public ArrayList<FireStation> listFireStation;

    public ArrayList<MedicalRecords> listMedicalRecords;

    public ArrayList<FloodResponse> listFloodResponse;


    public JsonReader(ArrayList<Person> listPersons, ArrayList<FireStation> listFireStation, ArrayList<MedicalRecords> listMedicalRecords , ArrayList<FloodResponse> listFloodResponse) throws ParseException, IOException, FileNotFoundException {


        this.listPersons = listPersons;
        this.listFireStation = listFireStation;
        this.listMedicalRecords = listMedicalRecords;
        this.listFloodResponse = listFloodResponse;

        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/main/resources/JSON/data.JSON");

        Object jsonObj = parser.parse(reader);

        JSONObject jsonObject = (JSONObject) jsonObj;

        JSONArray persons = (JSONArray ) jsonObject.get("persons");

        Iterator<JSONObject> per = persons.iterator();

        while (per.hasNext()) {

            JSONObject o = per.next();
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

        JSONArray firestations = (JSONArray ) jsonObject.get("firestations");

        Iterator<JSONObject> fire = firestations.iterator();

        while (fire.hasNext()) {

            JSONObject o = fire.next();
            FireStation fireStation = new FireStation();
            fireStation.setAddress((String)o.get("address"));
            fireStation.setStationNumber((String)o.get("station"));

            listFireStation.add(fireStation);

        }

        JSONArray medicalrecords = (JSONArray ) jsonObject.get("medicalrecords");

        Iterator<JSONObject> medic = medicalrecords.iterator();

        while (medic.hasNext()) {

            JSONObject o = medic.next();

            MedicalRecords medicalRec = new MedicalRecords();
            medicalRec.setFirstName((String)o.get("firstName"));
            medicalRec.setLastName((String)o.get("lastName"));
            medicalRec.setBirthDate((String) o.get("birthdate"));
            medicalRec.setMedication((ArrayList<String>) o.get("medications"));
            medicalRec.setAllergies((ArrayList<String>) o.get("allergies"));

            listMedicalRecords.add(medicalRec);


        }

        while (fire.hasNext() && per.hasNext() && medic.hasNext()) {
            JSONObject oFire = fire.next();
            JSONObject oPer = per.next();
            JSONObject oMedic = medic.next();

            FloodResponse floodResponse = new FloodResponse();
            floodResponse.setAddress((String)oFire.get("address"));
            floodResponse.setStationNumber((String) oFire.get("station"));

            floodResponse.setFirstName((String)oPer.get("firstName"));
            floodResponse.setLastName((String)oPer.get("lastName"));
            floodResponse.setTelephone((String) oPer.get("phone"));

            floodResponse.setBirthDate((String) oMedic.get("birthdate"));
            floodResponse.setMedication((ArrayList<String>) oMedic.get("medications"));
            floodResponse.setAllergies((ArrayList<String>) oMedic.get("allergies"));
            listFloodResponse.add(floodResponse);
        }

    }

}
