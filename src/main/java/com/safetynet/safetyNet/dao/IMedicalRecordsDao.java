package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.MedicalRecords;

import java.util.List;

public interface IMedicalRecordsDao {

    List<MedicalRecords> findAll();

    MedicalRecords getByName(String firstName, String lastName);


    MedicalRecords save(MedicalRecords medicalRecords);


    MedicalRecords update(MedicalRecords medicalRecords);

    void delete(String firstName, String lastName);



}
