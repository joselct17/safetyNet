package com.safetynet.safetyNet.controller;




import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.Person;
import com.safetynet.safetyNet.service.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonServiceImpl personService;

    private final IPersonDao personDao;

    public PersonController(PersonServiceImpl personService, IPersonDao personDao) {
        this.personService = personService;
        this.personDao = personDao;
    }



    @GetMapping("/person")
    public List<Person> personsList() {
        logger.info("GET/ person called");
        return personDao.findAll();
    }


    @PostMapping("/person")
    public ResponseEntity<Person> ajouterPerson(@RequestBody Person person) throws Exception {
        logger.info("POST /person called");
        Person personAdded = personService.savePerson(person);
        if (Objects.isNull(personAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                        .path("/{lastName}")
                                .buildAndExpand(personAdded.getLastName()+personAdded.getFirstName())
                                        .toUri();
        logger.info("POST /person response : CREATED");

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws Exception {
        logger.info("PUT /person called");
       Person personUpdate = personService.updatePerson(person);

       if(Objects.isNull(personUpdate)) {
           return ResponseEntity.noContent().build();
       }

       URI location = ServletUriComponentsBuilder
               .fromCurrentRequest()
               .path("/person")
               .buildAndExpand(personUpdate.getLastName()+personUpdate.getFirstName())
               .toUri();
        logger.info("PUT /person response : OK");
       return ResponseEntity.created(location).build();

    }


    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {

        logger.info("DELETE /person called");
        personService.deletePerson(firstName, lastName);
        logger.info("DELETE /person response : GONE");
        return new ResponseEntity<>(HttpStatus.GONE);
    }





}


