package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.dao.PersonDao;
import com.safetynet.safetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SafetyNetAlertsController {

    @Autowired
    private PersonDao personDao;



    //http://localhost:8080/communityEmail?city=<city>
    @GetMapping("/communityEmail/{city}")
    public List<Person> peopleByCity(@PathVariable String city) {

        return personDao.getEmailsByCity(city);

    }

    //http://localhost:8080/firestation?stationNumber=%3Cstation_number
    @GetMapping("/firestation/{stationNumber}")
    public List<Person> peopleByFirestation(@PathVariable String stationNumber) {
        return null;
    }

    //http://localhost:8080/childAlert?address=%3Caddress
    @GetMapping("/childAlert/{address}")
    public List<Person> childs(@PathVariable String address) {
        return personDao.getChildsByAddress(address);
    }

}
