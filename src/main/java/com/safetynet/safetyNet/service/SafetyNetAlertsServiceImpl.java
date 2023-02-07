package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
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
    public ArrayList<LinkedHashMap> getChildsByAddress(String address) throws ParseException {

        List<Person> personListByAddress = personDao.getByAddress(address);

        ArrayList<LinkedHashMap> list = new ArrayList<>();

        for (Person person : personListByAddress) {

            MedicalRecords medicalRecords = medicalRecordsDao.getByName(person.getFirstName(), person.getLastName());

            LocalDate age = LocalDate.parse(medicalRecords.getBirthDate(), formatter);

            if (ageCalculator(age) < 18) {

                LinkedHashMap<String, String> childs = new LinkedHashMap<>();

                childs.put("Firstname", person.getFirstName());
                childs.put("Lastname", person.getLastName());
                childs.put("Age", String.valueOf((ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter)))));

                JSONArray array = new JSONArray();
                //array.add(childs);
                list.add(childs);
            }
        }
        Person person = new Person();
        personListByAddress.stream().filter(p1->!(p1.getFirstName().equals(person.getFirstName())&& p1.getLastName().equals(person.getLastName()))).forEach(p2->{

            LinkedHashMap<String, String> adults = new LinkedHashMap<>();
            adults.put("Firstname", p2.getFirstName());
            adults.put("Lastname", p2.getLastName());

            list.add(adults);

        });
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
    public ArrayList<HashMap> getPeopleByStationNumber(String stationNumber) {

        int numberOfadults = 0;
        int numberOfChildren = 0;

        FireStation fireStation = new FireStation();

        ArrayList<HashMap> list = new ArrayList<>();

        List<String> peopleByStationNumber = fireStationDao.getByStationNumber(stationNumber).stream().map(FireStation::getAddress).collect(Collectors.toList());

        for (Person person : personDao.findAll()) {
            if (peopleByStationNumber.contains(person.getAddress())) {

                LinkedHashMap<String, String> people = new LinkedHashMap<>();

                people.put("Firstname", person.getFirstName());
                people.put("Lastname", person.getLastName());
                people.put("Address", person.getAddress());
                people.put( "Phone", person.getTelephone());


               list.add(people);

               MedicalRecords medicalRecords = medicalRecordsDao.getByName(person.getFirstName(), person.getLastName());

                if (ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter)) >=18 ) {
                    numberOfadults++;
                }
                else {
                    numberOfChildren++;
                }
            }
        }
        HashMap<String, Integer> number = new HashMap<String, Integer>();
        number.put("Adults", numberOfadults);
        number.put("Childs", numberOfChildren);
        list.add(number);

        return list;
    }


    @Override
    public ArrayList<HashMap> getPeopleByFireAddress(String address) {

        FireStation fireStation = new FireStation();

        String fireStationNumber = fireStationDao.getByAddress(address);

       String stationNumber = fireStation.getStationNumber();

        ArrayList<HashMap> list = new ArrayList<>();

        List<String> peopleByStationNumber = fireStationDao.getByStationNumber(stationNumber).stream().map(FireStation::getAddress).collect(Collectors.toList());

        for (Person person : personDao.findAll()) {

                MedicalRecords medicalRecords = medicalRecordsDao.getByName(person.getFirstName(), person.getLastName());



                LinkedHashMap<String, String> people = new LinkedHashMap<>();

                people.put("Firstname", person.getFirstName());
                people.put("Lastname", person.getLastName());
                people.put("Phone", person.getTelephone());
                people.put("Age", String.valueOf((ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter)))));
                people.put("Medications", medicalRecords.getMedication().toString());
                people.put("Allergies", medicalRecords.getAllergies().toString());
                people.put("Firestation", fireStationNumber);


                list.add(people);

        }

        return list;
    }

    @Override
    public ArrayList<HashMap> getPeopleByName(String firstName, String lastName) {

        ArrayList<HashMap> list = new ArrayList<>();

        Person person = personDao.getByName(firstName, lastName);

        if (person!= null) {
            for (Person p: personDao.getByLastName(person.getLastName())) {

                MedicalRecords medicalRecords = medicalRecordsDao.getByName(person.getFirstName(), person.getLastName());

                LinkedHashMap<String, String> people = new LinkedHashMap<>();

                people.put("Firstname", p.getFirstName());
                people.put("Lastname", p.getLastName());
                people.put("Address", p.getAddress());
                people.put("Age", String.valueOf((ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter)))));
                people.put("Email", p.getEmail());
                people.put("Medications", medicalRecords.getMedication().toString());
                people.put("Allergies", medicalRecords.getAllergies().toString());

                list.add(people);

            }
        }


        return list;
    }

    @Override
    public ArrayList<HashMap> getAddressesListOfPersonsByStationNumberList(List<String> stationNumberList) {

        ArrayList<HashMap> list = new ArrayList<>();

    for (String i : stationNumberList ) {

        LinkedHashMap<String, String> fireStations = new LinkedHashMap<>();

        fireStations.put("StationNumber", i);

        List<FireStation> fireStationList = fireStationDao.getByStationNumber(i);

        list.add(fireStations);
        for (FireStation fireStation : fireStationList) {

            LinkedHashMap<String, String> addressList = new LinkedHashMap<>();

            addressList.put("address", fireStation.getAddress());
            List<Person> personsList = personDao.getByAddress(fireStation.getAddress());

            list.add(addressList);
            for ( Person person : personsList) {
                MedicalRecords medicalRecords = medicalRecordsDao.getByName(person.getFirstName(), person.getLastName());

                LinkedHashMap<String, String> people = new LinkedHashMap<>();

                people.put("Firstname", person.getFirstName());
                people.put("Lastname", person.getLastName());
                people.put("Phone", person.getTelephone());
                people.put("Age", String.valueOf((ageCalculator(LocalDate.parse(medicalRecords.getBirthDate(), formatter)))));
                people.put("Medications", medicalRecords.getMedication().toString());
                people.put("Allergies", medicalRecords.getAllergies().toString());

                list.add(people);
            }
        }

    }


        return list;
    }
}