package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.MedicalRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordsDaoImpl implements MedicalRecordsDao {


    @Autowired
    private final JsonReader jsonReader;

    public MedicalRecordsDaoImpl(JsonReader jsonReader) {

        this.jsonReader = jsonReader;

    }

    @Override
    public List<MedicalRecords> findAll() {
        return jsonReader.listMedicalRecords;
    }

    @Override
    public MedicalRecords save(MedicalRecords medicalRecords) {
        jsonReader.listMedicalRecords.add(medicalRecords);
        return medicalRecords;
    }

    @Override
    public MedicalRecords update(MedicalRecords medicalRecords) {
        for (MedicalRecords m : jsonReader.listMedicalRecords) {
            if (m.getFirstName().equals(medicalRecords.getFirstName()) && m.getLastName().equals(medicalRecords.getLastName())) {
                m.setBirthDate(medicalRecords.getBirthDate());
                m.setMedication(new ArrayList<>(medicalRecords.getMedication()));
                m.setAllergies(new ArrayList<>(medicalRecords.getAllergies()));
            }
        }

        return medicalRecords;

    }

}