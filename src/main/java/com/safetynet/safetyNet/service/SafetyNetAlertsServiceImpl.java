package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SafetyNetAlertsServiceImpl implements ISafetyNetAlertsService {

    @Autowired
    private final IPersonDao personDao;

    @Autowired
    private final IMedicalRecordsDao medicalRecordsDao;


    @Autowired
    private final IFireStationDao fireStationDao;

    private Integer ageCalculator(LocalDate birthDate) {
        Period p = Period.between(birthDate, LocalDate.now());
        return p.getYears();
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public SafetyNetAlertsServiceImpl(IPersonDao personDao, IMedicalRecordsDao medicalRecordsDao, IFireStationDao fireStationDao) {
        this.personDao = personDao;
        this.medicalRecordsDao = medicalRecordsDao;
        this.fireStationDao = fireStationDao;
    }


    @Override
    public Set<String> getEmailsByCity(String city) {
        Set<String> emails = new HashSet<>();
        for (Person person : personDao.getByCity(city)) {
            emails.add(person.getEmail());
        }

        return emails;

    }

    @Override
    public ArrayList<JSONArray> getChildsByAddress(String address) throws ParseException {

        List<Person> personListByAddress = personDao.getByAddress(address);

        ArrayList<JSONArray> list = new ArrayList<>();

        for (Person person : personListByAddress) {

            MedicalRecords medicalRecords = medicalRecordsDao.getByName(person.getFirstName(), person.getLastName());

            LocalDate age = LocalDate.parse(medicalRecords.getBirthDate(), formatter);

            if (ageCalculator(age) < 18) {

                JSONObject obj = new JSONObject();

                obj.put("Firstname", person.getFirstName());
                obj.put("Lastname", person.getLastName());
                obj.put("Age", (ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter))));

                JSONArray array = new JSONArray();
                array.add(obj);
                list.add(array);


                personListByAddress.stream().filter(p1->!(p1.getFirstName().equals(person.getFirstName())&& p1.getLastName().equals(person.getLastName()))).forEach(p2->{

                   JSONObject objParents = new JSONObject();
                    objParents.put("Firstname", p2.getFirstName());
                    objParents.put("Lastname", p2.getLastName());

                    array.add(objParents);

                });

            }
        }
        return list;
    }


    @Override
    public Set<String> getPhoneNumberForStationNumber(String stationNumber) {

        //On cree un hashSet pour eviter les doubles
        Set<String> phoneNumber = new HashSet<>();

        // ON cree une liste pour sotcker les adreesses par numero de station
        List<String> addressByStationNumber = fireStationDao.getByStationNumber(stationNumber).stream().map(FireStation::getAddress).collect(Collectors.toList());

        //on itere
        for (Person person : personDao.findAll()) {
            if (addressByStationNumber.contains(person.getAddress())) {
                phoneNumber.add(person.getTelephone());
            }
        }

        return phoneNumber;
    }

    @Override
    public JSONObject getPeopleByStation(String stationNumber) {

        ArrayList<Person> people = new ArrayList<>();

        List<String> addressByStationNumber = fireStationDao.getByStationNumber(stationNumber).stream().map(FireStation::getAddress).collect(Collectors.toList());

        for (Person person : personDao.findAll()) {
            if (addressByStationNumber.contains(person.getAddress())) {
                Person person1 = new Person();
                person1.setFirstName(person.getFirstName());
                person1.setLastName(person.getLastName());
                person1.setAddress(person.getAddress());
                person1.setTelephone(person.getTelephone());

                people.add(person1);
            }
        }

        MedicalRecords medicalRecords = new MedicalRecords();
        JSONObject peopleCounter = new JSONObject();

      if (ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter)) <18) {
          for ( int i = (ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter))); i<18; i++ ) {
              Integer childs =i ;
              peopleCounter.put("Childs", childs);
          }
      } else if(ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter)) >18) {
          for ( int i = (ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter))); i>18; i++ ) {
              Integer adults =i ;
              peopleCounter.put("Adults", adults);
          }
      }
JSONObject def = new JSONObject();
      def.put("People", people);
      def.put("Counter", peopleCounter);


        return def;
    }
}