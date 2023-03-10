package com.safetynet.safetyNet.controller;


import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.service.FireStationServiceImpl;
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
public class FireStationController {


    private final FireStationServiceImpl fireStationService;

    private final IFireStationDao fireStationDao;

    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);

    public FireStationController(FireStationServiceImpl fireStationService, IFireStationDao fireStationDao) {
        this.fireStationService = fireStationService;
        this.fireStationDao = fireStationDao;
    }

    @GetMapping("/firestation")
    public List<FireStation> showFirestation() {
        logger.info("GET /firestation called");
        return fireStationDao.findAll();
    }

    @PostMapping("/firestation")
    public ResponseEntity<FireStation> ajouterFirestation(@RequestBody FireStation fireStation) {

        logger.info("POST /firestation called");
        FireStation firestationAdded = fireStationService.saveFirestation(fireStation);
        if (Objects.isNull(firestationAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{stationNumber}")
                .buildAndExpand(firestationAdded.getStationNumber())
                .toUri();

        logger.info("POST /firestation response : 201");
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStation> updateFirestation(@RequestBody FireStation fireStation) {

        logger.info("PUT /firestation called");

        FireStation fireStationUpdate = fireStationService.updateFirestation(fireStation);

        if(Objects.isNull(fireStationUpdate)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{stationNumber}")
                .buildAndExpand(fireStationUpdate.getStationNumber())
                .toUri();
        logger.info("PUT /firestation response : 201");
        return ResponseEntity.created(location).build();

    }


    @DeleteMapping(value = "/firestation", params = "stationNumber")
    public ResponseEntity<FireStation> deleteFirestationByStationNumber( @RequestParam String stationNumber) {

        logger.info("DELETE stationNumber /firestation called");
        fireStationService.deleteFirestationByNumber(stationNumber);

        logger.info("DELETE /firestation response GONE");
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @DeleteMapping(value = "/firestation", params = "address")
    public ResponseEntity<FireStation> deleteFirestationByAddress(@RequestParam String address) {

        logger.info("DELETE address /firestation called");
        fireStationService.deleteFirestationByAddress(address);

        logger.info("DELETE /firestation response GONE");
        return new ResponseEntity<>(HttpStatus.GONE);
    }



}
