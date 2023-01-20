package com.safetynet.safetyNet.controller;


import com.safetynet.safetyNet.dao.FireStationDao;
import com.safetynet.safetyNet.model.FireStation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



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
}
