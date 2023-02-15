package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.FireStation;

import java.util.List;

public interface IFireStationDao {

    List<FireStation> findAll();

    String getByAddress(String address);


    List<FireStation> getByStationNumber(String stationNumber);


    FireStation save(FireStation fireStation);

    FireStation update(FireStation fireStation);

    void delete(String address, String stationNumber);

    void deleteByAddress(String address);

    void deleteByStationNumber(String stationNumber);
}
