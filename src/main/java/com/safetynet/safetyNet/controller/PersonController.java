package com.safetynet.safetyNet.controller;




import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.Person;
import com.safetynet.safetyNet.service.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
public class PersonController {

    private final PersonServiceImpl personService;

    private final IPersonDao personDao;

    public PersonController(PersonServiceImpl personService, IPersonDao personDao) {
        this.personService = personService;
        this.personDao = personDao;
    }



    @GetMapping("/person")
    public List<Person> personsList() {

        return personDao.findAll();
    }


    @PostMapping("/person")
    public ResponseEntity<Person> ajouterPerson(@RequestBody Person person) throws Exception {
        Person personAdded = personService.savePerson(person);
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
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws Exception {

       Person personUpdate = personService.updatePerson(person);

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


    @DeleteMapping("/person/{firstName}{lastName}")
    public ResponseEntity<Person> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);

        return new ResponseEntity<>(HttpStatus.GONE);
    }





}


