package com.safetynet.safetyNet.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public interface ISafetyNetAlertsService {


    Set<String> getEmailsByCity(String city);


    ArrayList<Object> getChildsByAddress(String address) throws ParseException;


    ArrayList<HashMap> getPeopleByStationNumber(String stationNumber);


    Set<String> getPhoneNumberForStationNumber(String stationNumber);

    ArrayList<HashMap> getPeopleByFireAddress(String address);


    ArrayList<HashMap> getPeopleByName(String firstName, String lastName);


    ArrayList<Object> getAddressesListOfPersonsByStationNumberList(List<Integer> stationNumberList );



}
