package com.safetynet.safetyNet.controller;




import com.safetynet.safetyNet.dao.PersonDao;
import com.safetynet.safetyNet.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
public class PersonController {

    private final PersonDao personDao;

    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }



    @GetMapping("/person")
    public List<Person> personsList() {

        return personDao.findAll();
    }


    @PostMapping("/person")
    public ResponseEntity<Person> ajouterPerson(@RequestBody Person person) {
        Person personAdded = personDao.save(person);
        if (Objects.isNull(personAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                        .path("/{lastName}")
                                .buildAndExpand(personAdded.getLastName()+personAdded.getFirstName())
                                        .toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {

       Person personUpdate = personDao.update(person);

       if(Objects.isNull(personUpdate)) {
           return ResponseEntity.noContent().build();
       }

       URI location = ServletUriComponentsBuilder
               .fromCurrentRequest()
               .path("/{lastName}")
               .buildAndExpand(personUpdate.getLastName()+personUpdate.getFirstName())
               .toUri();
       return ResponseEntity.created(location).build();

    }





}


