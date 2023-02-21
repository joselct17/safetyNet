package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class FireStationDaoImpl implements IFireStationDao {

    private final Logger logger = LoggerFactory.getLogger(FireStationDaoImpl.class);
    @Autowired
    private JsonReader jsonReader;

    public FireStationDaoImpl( JsonReader jsonReader)  {
        this.jsonReader = jsonReader;
    }

    protected void setFireStationList(ArrayList<FireStation> listFireStation) {
        this.jsonReader.listFireStation = listFireStation;
    }


    @Override
    public List<FireStation> findAll() {
        return jsonReader.listFireStation;
    }

    @Override
    public String getByAddress(String address) {

        List<FireStation> result = jsonReader.listFireStation.stream().filter(fireStation -> fireStation.getAddress().equals(address)).collect(Collectors.toList());
        if (result.size()==1) {
            return result.get(0).getStationNumber();
        }
        else if (result.isEmpty()) { //not found is not an error
            return null;
        }
        else {//this is to test case doubles : error
            logger.debug("Found {} firestations for address={} , but was expecting 1.",result.size(), address );
            throw new IllegalStateException("Found "+result.size()+" persons for " +
                    " " + address + ", but was expecting 1 Firestation.");
        }
    }

    @Override
    public List<FireStation> getByStationNumber(String stationNumber) {
        return jsonReader.listFireStation.stream().filter(fireStation -> fireStation.getStationNumber().equals(stationNumber)).collect(Collectors.toList());
    }


    @Override
    public FireStation save(FireStation fireStation) {
        jsonReader.listFireStation.add(fireStation);
        return fireStation;
    }

    @Override
    public FireStation update(FireStation fireStation) {
        for (FireStation f : jsonReader.listFireStation) {
            if (f.getAddress().equals(fireStation.getAddress())) {
                f.setStationNumber(fireStation.getStationNumber());
            }
        }
        return fireStation;
    }


    @Override
    public void deleteByAddress(String address) {
        jsonReader.listFireStation.removeIf(fireStation -> fireStation.getAddress().equals(address));
    }

    @Override
    public void deleteByStationNumber(String stationNumber) {
        jsonReader.listFireStation.removeIf(fireStation -> fireStation.getStationNumber().equals(stationNumber));
    }

}
