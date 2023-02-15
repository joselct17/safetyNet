package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationServiceImpl implements IFireStationService{

    @Autowired
    IFireStationDao fireStationDao;
    @Override
    public FireStation saveFirestation(FireStation fireStation) {
        try {
            fireStationDao.save(fireStation);
            return new FireStation(fireStation.getAddress(), fireStationDao.getByAddress(fireStation.getAddress()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FireStation updateFirestation(FireStation fireStation) {
        try {
            fireStationDao.update(fireStation);
            return new FireStation(fireStation.getAddress(),fireStationDao.getByAddress(fireStation.getAddress()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFirestation(String address, String stationNumber) {
        try {
            fireStationDao.delete(address, stationNumber);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteFirestationByAddress(String address) {
        try {
            fireStationDao.deleteByAddress(address);
        }catch (Exception ex) {
            throw new RuntimeException();
        }


    }

    @Override
    public void deleteFirestationByNumber(String stationNumber) {
        try{
            fireStationDao.deleteByStationNumber(stationNumber);
        }catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<FireStation> getAllFirestation() {
        return fireStationDao.findAll();
    }
}
