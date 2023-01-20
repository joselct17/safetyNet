package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.FireStation;

import java.util.List;

public interface FireStationDao {

    List<FireStation> findAll();

    List<FireStation> findByAddress();

    List<FireStation> findByNumber();

    FireStation save(FireStation fireStation);



}
