package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.service.IMedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class MedicalRecordsController {

    private final IMedicalRecordsDao medicalRecordsDao;
    private final IMedicalRecordService medicalRecordService;

    public MedicalRecordsController(IMedicalRecordsDao medicalRecordsDao, IMedicalRecordService medicalRecordService) {
        this.medicalRecordsDao = medicalRecordsDao;
        this.medicalRecordService = medicalRecordService;
    }


    @GetMapping("/medicalRecord")
    List<MedicalRecords> medicalList() {
        return medicalRecordsDao.findAll();
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecords> ajouterMedical(@RequestBody MedicalRecords medicalRecords) {
        MedicalRecords medicalAdded = medicalRecordService.saveMedicalRecord(medicalRecords);
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

        MedicalRecords medicalRecordsUpdate = medicalRecordService.updateMedicalRecord(medicalRecords);

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



    @DeleteMapping("/medicalRecord")
        public ResponseEntity<MedicalRecords> deleteMedical(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
