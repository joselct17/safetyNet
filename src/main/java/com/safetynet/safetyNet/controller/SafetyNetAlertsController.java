package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.service.ISafetyNetAlertsService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;


@RestController
public class SafetyNetAlertsController {

    @Autowired
    private ISafetyNetAlertsService safetyNetAlertsService;



    //http://localhost:8080/communityEmail?city=<city>
    @GetMapping("/communityEmail/{city}")
    public JSONObject peopleByCity(@PathVariable String city) {

        JSONObject peopleByCity = new JSONObject();
        JSONArray result = new JSONArray();

        result.add(safetyNetAlertsService.getEmailsByCity(city));

        peopleByCity.put("Emails", result);

        return peopleByCity;

    }


    //http://localhost:8080/childAlert?address=%3Caddress
    @GetMapping("/childAlert/{address}")
    public JSONObject childs(@PathVariable String address) throws ParseException {

        JSONObject childs = new JSONObject();
        JSONArray result = new JSONArray();

        result.add(safetyNetAlertsService.getChildsByAddress(address));

        childs.put("Childs", result);

        return childs ;
    }


    //http://localhost:8080/phoneAlert?firestation=%3Cfirestation_number
    @GetMapping("/phoneAlert/{stationNumber}")
    public JSONObject phoneNumber(@PathVariable String stationNumber)  {

        JSONObject phoneNumber = new JSONObject();
        JSONArray result = new JSONArray();

        result.add(safetyNetAlertsService.getPhoneNumberForStationNumber(stationNumber));
        phoneNumber.put("Phone" ,result);

        return phoneNumber;
    }

    //http://localhost:8080/firestation?stationNumber=%3Cstation_number
    @GetMapping("/firestation/{stationNumber}")
    public JSONObject peopleByFireStation(@PathVariable String stationNumber) {
        JSONObject people = new JSONObject();
        JSONArray result = new JSONArray();

        result.add(safetyNetAlertsService.getPeopleByStationNumber(stationNumber));

        people.put("People", result);


      return people ;

    }


}
