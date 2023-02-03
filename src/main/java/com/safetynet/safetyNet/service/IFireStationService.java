package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.model.FireStation;

public interface IFireStationService {

    FireStation saveFirestation(FireStation fireStation);


    FireStation updateFirestation(FireStation fireStation);

    void deleteFirestation(String address, String stationNumber);



}
