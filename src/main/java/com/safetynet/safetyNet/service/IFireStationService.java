package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.model.FireStation;

import java.util.List;

public interface IFireStationService {

    FireStation saveFirestation(FireStation fireStation);


    FireStation updateFirestation(FireStation fireStation);

    void deleteFirestation(String address, String stationNumber);


    List<FireStation> getAllFirestation();
}
