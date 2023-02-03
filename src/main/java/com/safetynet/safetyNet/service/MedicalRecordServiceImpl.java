package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.model.MedicalRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService{

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

    @Autowired
    IMedicalRecordsDao medicalRecordsDao;

    @Override
    public MedicalRecords saveMedicalRecord(MedicalRecords medicalRecords) {
        try {
            medicalRecordsDao.save(medicalRecords);
            return medicalRecordsDao.getByName(medicalRecords.getFirstName(), medicalRecords.getLastName());
        }catch (Exception ex) {
            logger.debug("Technical error creating medicalrecord  {} {}", medicalRecords.getFirstName(), medicalRecords.getLastName());
            throw new RuntimeException(ex);
        }

    }

    @Override
    public MedicalRecords updateMedicalRecord(MedicalRecords medicalRecords) {
        try {
            medicalRecordsDao.update(medicalRecords);
            return medicalRecordsDao.getByName(medicalRecords.getFirstName(), medicalRecords.getLastName());
        } catch (Exception ex) {
            logger.debug("Technical error updating medicalrecord {} {}", medicalRecords.getFirstName(), medicalRecords.getLastName());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        try {
            medicalRecordsDao.delete(firstName, lastName);
        } catch (Exception ex) {
            logger.debug("Technical error deleting medicalrecord {} {}", firstName, lastName);
            throw new RuntimeException(ex);
        }
    }
}
