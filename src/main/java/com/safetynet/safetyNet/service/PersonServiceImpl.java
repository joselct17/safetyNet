package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    IPersonDao personDao;

    @Override
    public Person savePerson(Person person) throws Exception {
        try {
            personDao.save(person);
            return personDao.getByName(person.getFirstName(), person.getLastName());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Person updatePerson(Person person) throws Exception {
        try {
            personDao.update(person);
            return personDao.getByName(person.getFirstName(), person.getLastName());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deletePerson(String firstname, String lastname) {
        try {
            personDao.delete(firstname, lastname);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
