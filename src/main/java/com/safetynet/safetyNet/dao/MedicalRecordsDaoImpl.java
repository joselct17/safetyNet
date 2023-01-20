package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class MedicalRecordsDaoImpl implements MedicalRecordsDao{

    private final ArrayList<MedicalRecords> medicalRecords;

    public MedicalRecordsDaoImpl(ArrayList<MedicalRecords> medicalRecords) throws IOException, ParseException {
        this.medicalRecords = medicalRecords;

        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/main/resources/JSON/data.JSON");

        Object jsonObj = parser.parse(reader);

        JSONObject jsonObject = (JSONObject) jsonObj;

        JSONArray jsonArray = (JSONArray ) jsonObject.get("medicalrecords");

        Iterator<JSONObject> i = jsonArray.iterator();

        while (i.hasNext()) {

            JSONObject o = i.next();
            MedicalRecords medicalRec = new MedicalRecords();
            medicalRec.setFirstName((String)o.get("firstName"));
            medicalRec.setLastName((String)o.get("lastName"));
            medicalRec.setBirthDate((String)o.get("birthdate"));
            medicalRec.setMedication((ArrayList<String>) o.get("medications"));
            medicalRec.setAllergies((ArrayList<String>) o.get("allergies"));

            medicalRecords.add(medicalRec);


        }
    }


    @Override
    public List<MedicalRecords> findAll() {
        return medicalRecords;
    }
}
