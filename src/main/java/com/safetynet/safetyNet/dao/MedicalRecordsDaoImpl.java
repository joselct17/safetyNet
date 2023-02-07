package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.MedicalRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MedicalRecordsDaoImpl implements IMedicalRecordsDao {


    @Autowired
    private final JsonReader jsonReader;

    public MedicalRecordsDaoImpl(JsonReader jsonReader) {

        this.jsonReader = jsonReader;

    }
    protected void setMedicalList(ArrayList<MedicalRecords> listMedicalRecords) {
        this.jsonReader.listMedicalRecords = listMedicalRecords;
    }

    @Override
    public List<MedicalRecords> findAll() {
        return jsonReader.listMedicalRecords;
    }

    @Override
    public MedicalRecords getByName(String firstName, String lastName) {
        List<MedicalRecords> result = jsonReader.listMedicalRecords.stream()
                .filter(medicalRecords -> (medicalRecords.getFirstName().equals(firstName) &&
                        medicalRecords.getLastName().equals(lastName)))
                .collect(Collectors.toList());
        if (result.size()==1) {
            return result.get(0);
        }
        else return null;
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
                m.setBirthDate (medicalRecords.getBirthDate());
                m.setMedication(new ArrayList<>(medicalRecords.getMedication()));
                m.setAllergies(new ArrayList<>(medicalRecords.getAllergies()));
            }
        }

        return medicalRecords;

    }

    @Override
    public void delete(String firstName, String lastName) {
        jsonReader.listMedicalRecords.removeIf(medicalRecords -> (medicalRecords.getFirstName().equals(firstName) && medicalRecords.getLastName().equals(lastName)));
    }

}