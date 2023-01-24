package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.MedicalRecords;

import java.util.List;

public interface MedicalRecordsDao {

    List<MedicalRecords> findAll();


    MedicalRecords save(MedicalRecords medicalRecords);



}
