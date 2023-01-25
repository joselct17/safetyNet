package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.dao.MedicalRecordsDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecords> ajouterMedical(@RequestBody MedicalRecords medicalRecords) {
        MedicalRecords medicalAdded = medicalRecordsDao.save(medicalRecords);
        if (Objects.isNull(medicalAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{lastName}")
                .buildAndExpand(medicalAdded.getLastName())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecords> updateMedicalRecord(@RequestBody MedicalRecords medicalRecords) {

        MedicalRecords medicalRecordsUpdate = medicalRecordsDao.update(medicalRecords);

        if (Objects.isNull(medicalRecordsUpdate)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{lastName}")
                .buildAndExpand(medicalRecordsUpdate.getLastName() + medicalRecordsUpdate.getFirstName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
