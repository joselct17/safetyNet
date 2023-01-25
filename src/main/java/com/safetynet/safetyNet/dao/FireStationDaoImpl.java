package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.FireStation;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class FireStationDaoImpl implements FireStationDao {

    @Autowired
    private JsonReader jsonReader;

    public FireStationDaoImpl( JsonReader jsonReader)  {
        this.jsonReader = jsonReader;
    }


    @Override
    public List<FireStation> findAll() {
        return jsonReader.listFireStation;
    }

    @Override
    public List<FireStation> getByAddress(String address) {

        return jsonReader.listFireStation.stream().filter(fireStation -> fireStation.getAddress().equals(address)).collect(Collectors.toList());
    }

    @Override
    public List<FireStation> findByNumber() {
        return null;
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

}
