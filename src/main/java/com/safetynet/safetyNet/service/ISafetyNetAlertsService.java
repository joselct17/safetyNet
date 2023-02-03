package com.safetynet.safetyNet.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Set;

@Service
public interface ISafetyNetAlertsService {


    Set<String> getEmailsByCity(String city);


    ArrayList<JSONArray> getChildsByAddress(String address) throws ParseException;


    JSONObject getPeopleByStation(String stationNumber);


    Set<String> getPhoneNumberForStationNumber(String stationNumber);
}
