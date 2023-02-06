package com.safetynet.safetyNet.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

@Service
public interface ISafetyNetAlertsService {


    Set<String> getEmailsByCity(String city);


    ArrayList<LinkedHashMap> getChildsByAddress(String address) throws ParseException;


    ArrayList<LinkedHashMap> getPeopleByStationNumber(String stationNumber);


    Set<String> getPhoneNumberForStationNumber(String stationNumber);
}
