package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.service.IMedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class MedicalRecordsController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordsController.class);


    private final IMedicalRecordsDao medicalRecordsDao;
    private final IMedicalRecordService medicalRecordService;

    public MedicalRecordsController(IMedicalRecordsDao medicalRecordsDao, IMedicalRecordService medicalRecordService) {
        this.medicalRecordsDao = medicalRecordsDao;
        this.medicalRecordService = medicalRecordService;
    }


    @GetMapping("/medicalRecord")
    List<MedicalRecords> medicalList() {
        logger.info("GET /medicalRecord called");
        return medicalRecordsDao.findAll();
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecords> ajouterMedical(@RequestBody MedicalRecords medicalRecords) {
        logger.info("POST /medicalRecord called");
        MedicalRecords medicalAdded = medicalRecordService.saveMedicalRecord(medicalRecords);
        if (Objects.isNull(medicalAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{lastName}")
                .buildAndExpand(medicalAdded.getLastName())
                .toUri();
        logger.info("POST /medicalRecord response : CREATED");
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecords> updateMedicalRecord(@RequestBody MedicalRecords medicalRecords) {

        logger.info("PUT /medicalRecord called");
        MedicalRecords medicalRecordsUpdated = medicalRecordService.updateMedicalRecord(medicalRecords);

        if(Objects.isNull(medicalRecordsUpdated)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/medicalRecord")
                .buildAndExpand(medicalRecordsUpdated.getLastName()+medicalRecordsUpdated.getFirstName())
                .toUri();
        logger.info("PUT /medicalRecord response : OK");
        return ResponseEntity.created(location).build();
    }



    @DeleteMapping("/medicalRecord")
        public ResponseEntity<MedicalRecords> deleteMedical(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("DELETE /medicalRecord called");
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        logger.info("DELETE /medicalRecord response : GONE");
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
