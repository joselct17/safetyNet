package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.dao.MedicalRecordsDao;
import com.safetynet.safetyNet.model.MedicalRecords;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordsController {

    private final MedicalRecordsDao medicalRecordsDao;

    public MedicalRecordsController(MedicalRecordsDao medicalRecordsDao) {
        this.medicalRecordsDao = medicalRecordsDao;
    }


    @GetMapping("/medicalRecord")
    List<MedicalRecords> medicalList() {
        return medicalRecordsDao.findAll();
    }
}
