package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.model.MedicalRecords;

import java.util.List;

public interface IMedicalRecordService {

    MedicalRecords saveMedicalRecord(MedicalRecords medicalRecords);

    MedicalRecords updateMedicalRecord(MedicalRecords medicalRecords);

    void deleteMedicalRecord(String firstName, String lastName);

    List<MedicalRecords> getAllMedicalrecord();
}
