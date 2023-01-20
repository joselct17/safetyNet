package com.safetynet.safetyNet.controller;

import com.safetynet.safetyNet.dao.PersonDao;
import com.safetynet.safetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SafetyNetAlertsController {

    @Autowired

    private PersonDao personDao;


    @GetMapping("/communityEmail")
    public List<Person> listEmails() {

        return personDao.getEmails();

    }

}
