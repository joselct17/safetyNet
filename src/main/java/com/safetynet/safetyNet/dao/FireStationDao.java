package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.FireStation;

import java.util.List;

public interface FireStationDao {

    List<FireStation> findAll();

    List<FireStation> getByAddress(String address);


    List<FireStation> findByNumber();


    FireStation save(FireStation fireStation);

    FireStation update(FireStation fireStation);

}
