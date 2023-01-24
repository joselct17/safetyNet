package com.safetynet.safetyNet.controller;


import com.safetynet.safetyNet.dao.FireStationDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
public class FireStationController {


    private final FireStationDao fireStationDao;

    public FireStationController(FireStationDao fireStationDao) {
        this.fireStationDao = fireStationDao;
    }

    @GetMapping("/firestation")
    public List<FireStation> showFirestation() {

        return fireStationDao.findAll();
    }

    @PostMapping("/firestation")
    public ResponseEntity<FireStation> ajouterFirestation(@RequestBody FireStation fireStation) {
        FireStation firestationAdded = fireStationDao.save(fireStation);
        if (Objects.isNull(firestationAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{stationNumber}")
                .buildAndExpand(firestationAdded.getStationNumber())
                .toUri();
        return ResponseEntity.created(location).build();

    }
}
