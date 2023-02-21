package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.service.ISafetyNetAlertsService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;


@RestController
public class SafetyNetAlertsController {

    @Autowired
    private ISafetyNetAlertsService safetyNetAlertsService;

    private static final Logger logger = LoggerFactory.getLogger(SafetyNetAlertsController.class);


    //http://localhost:8080/firestations?stationNumber=<station_number>
    @GetMapping("/firestations")
    public ResponseEntity<JSONObject>  peopleByFireStation(@RequestParam String stationNumber) {
        logger.info("GET /fire called");

        JSONObject object = new JSONObject();
        JSONArray result = new JSONArray();
        result.add(safetyNetAlertsService.getPeopleByStationNumber(stationNumber));
        object.put("People", result);

        logger.info("GET /fire response : OK");
        return new ResponseEntity<>(object, HttpStatus.OK)  ;

    }

    //http://localhost:8080/childAlert?address=<address>
    @GetMapping("/childAlert")
    public ResponseEntity <JSONObject> childsAlert(@RequestParam String address) throws ParseException {
        logger.info("GET /childAlert called");

        // corriger probleme, pas d'enfant dans l'adress,
        JSONObject object = new JSONObject();
        JSONArray result = new JSONArray();
        result.add(safetyNetAlertsService.getChildsByAddress(address));
        object.put("Childs", result);

        logger.info("GET /childAlert response : OK");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    @GetMapping("/phoneAlert")
    public ResponseEntity<JSONObject> phoneNumber(@RequestParam String stationNumber) {
        logger.info("GET /phoneAlert called");

        JSONObject object = new JSONObject();
        JSONArray result = new JSONArray();
        result.add(safetyNetAlertsService.getPhoneNumberForStationNumber(stationNumber));
        object.put("Phone", result);

        logger.info("GET /phoneAlert response : OK");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }


    //http://localhost:8080/fire?address=<address>
    @GetMapping("/fire")
    public ResponseEntity <JSONObject> peopleByFirestationAddress (@RequestParam String address){
        logger.info("GET /fire call");

        JSONObject object = new JSONObject();
        JSONArray result = new JSONArray();
        result.add(safetyNetAlertsService.getPeopleByFireAddress(address));
        object.put("People", result);

        logger.info("GET /fire respoonse : OK");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }


    //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @GetMapping("/personInfo")
    public ResponseEntity<JSONObject> peopleByName(@RequestParam(required = false) String firstName, @RequestParam String lastName) {
        logger.info("GET /personInfo called");
//donc corriger url, si John Boyd seulement jhon boyd doit etre envoyé, si seulement on met Boyd tous les boyds doievent apparaitre
        JSONObject object = new JSONObject();
        JSONArray result = new JSONArray();
        result.add(safetyNetAlertsService.getPeopleByName(firstName,lastName));
        object.put("People", result);

        logger.info("GET /personInfo response : OK");
        return new ResponseEntity<>(object, HttpStatus.OK) ;

    }



    //http://localhost:8080/communityEmail?city=<city>
    @GetMapping("/communityEmail")
    public ResponseEntity<JSONObject> peopleByCity(@RequestParam String city) {
        logger.info("GET /communityEmail called");

        JSONObject object = new JSONObject();
        JSONArray result = new JSONArray();
        result.add(safetyNetAlertsService.getEmailsByCity(city));
        object.put("Emails", result);

        logger.info("GET /communityEmail response : OK");
        return new ResponseEntity<>(object, HttpStatus.OK);

    }


    //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
//CORRIGER CETTE REQUETTE, AVEC REQUESTPARAM CA NE MARCHE PAS
    @GetMapping("/flood/stations")
    public ResponseEntity<JSONObject> peopleByStationNumber(@RequestParam List<Integer> stations) {

        logger.info("GET /flood called");

        JSONObject object = new JSONObject();
        JSONArray result = new JSONArray();
        result.add(safetyNetAlertsService.getAddressesListOfPersonsByStationNumberList(stations));
        object.put("People", result);

        logger.info("GET /flodd response : OK");
        return new ResponseEntity<>(object, HttpStatus.OK);

    }


}