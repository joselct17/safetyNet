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

        FireStation fireStationUpdate = fireStationService.updateFirestation(fireStation);

        if(Objects.isNull(fireStationUpdate)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{stationNumber}")
                .buildAndExpand(fireStationUpdate.getStationNumber())
                .toUri();
        return ResponseEntity.created(location).build();

    }


    @DeleteMapping("/firestation")
    public ResponseEntity<FireStation> daleteFirestation(@RequestParam(required = true) String address, @RequestParam(required = true) String stationNumber) {
        fireStationService.deleteFirestation(address, stationNumber);

        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
