package com.safetynet.safetyNet.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SafetyNetAlertsServiceTest {

    @InjectMocks
    SafetyNetAlertsServiceImpl safetyNetAlertsService;

    @Mock
    IFireStationDao iFireStationDao;


    @Mock
    IPersonDao iPersonDao;

    @Mock
    IMedicalRecordsDao iMedicalRecordsDao;

    List<FireStation> fireStationList;

    List<Person> personList;

    List<MedicalRecords> medicalRecordsList;
    Person EdNortonPerson;
    Person JohnMcAvoyPerson;
    Person JamesFrancoPerson;

    MedicalRecords EdNortonMedical;
    MedicalRecords JohnMcAvoyMedical;
    MedicalRecords JamesFrancoMedical;

    @BeforeEach
    void data() {
        fireStationList  = new ArrayList<>(Arrays.asList(
                new FireStation("101 Av", "3"),
                new FireStation("102 Av", "4"),
                new FireStation("103 Av", "5")
        ));

        medicalRecordsList  = new ArrayList<>(Arrays.asList(
                new MedicalRecords("John", "McAvoy", "01/20/1554", new ArrayList<>(), new ArrayList<>()),
                new MedicalRecords("Ed", "Norton", "02/25/2015", new ArrayList<>(), new ArrayList<>()),
                new MedicalRecords("James", "Franco", "05/21/1994", new ArrayList<>(), new ArrayList<>())
        ));

        personList = new ArrayList<>( Arrays.asList(
                new Person("John", "McAvoy", "101 Av", "NY", "87456", "5787-878", "james@gmail.com"),
                new Person("Ed", "Norton", "102 Av", "NY", "87456", "5787-999", "edNorton@gmail.com"),
                new Person("James", "Franco", "103 Av", "NY", "87456", "5787-222", "jamesFranco@gmail.com")
        ));


        JohnMcAvoyMedical =  new MedicalRecords("John", "McAvoy", "01/20/1554", new ArrayList<>(), new ArrayList<>());
        EdNortonMedical= new MedicalRecords("Ed", "Norton", "02/25/2015", new ArrayList<>(), new ArrayList<>());
        JamesFrancoMedical =new MedicalRecords("James", "Franco", "05/21/1994", new ArrayList<>(), new ArrayList<>());

        JohnMcAvoyPerson = new Person("John", "McAvoy", "101 Av", "NY", "87456", "5787-878", "james@gmail.com");
        EdNortonPerson = new Person("Ed", "Norton", "102 Av", "NY", "87456", "5787-999", "edNorton@gmail.com");
        JamesFrancoPerson = new Person("James", "Franco", "103 Av", "NY", "87456", "5787-222", "jamesFranco@gmail.com");




    }


    @Test
    @DisplayName("Get all emails by city")
    void testGetEmailsByCity() {
        //ARRANGE
        Set<String> expected = new HashSet<>();
        expected.add("james@gmail.com");
        expected.add("edNorton@gmail.com");
        expected.add("jamesFranco@gmail.com");
        when(iPersonDao.getByCity("NY")).thenReturn(personList);

        //ACT
        Set<String> result = safetyNetAlertsService.getEmailsByCity("NY");

        //ASSERT
        assertEquals(expected, result);

    }

        @Test
        @DisplayName("Get childs by address")
        void testGetChildsByAddress() throws ParseException {

        //ARRANGE
            ArrayList<LinkedHashMap> expected = new ArrayList<>();

            LinkedHashMap<String, String> childs = new LinkedHashMap<>();

            childs.put("Firstname", "Ed");
            childs.put("Lastname", "Norton");
            childs.put("Age", "7");

            expected.add(childs);

            when(iPersonDao.getByAddress("102 Av")).thenReturn(new ArrayList<>(Arrays.asList(EdNortonPerson)));//only 1 child at this address
            when(iMedicalRecordsDao.getByName("Ed","Norton")).thenReturn(EdNortonMedical);

            //Act
            ArrayList result = safetyNetAlertsService.getChildsByAddress("102 Av");

            //Assert
            assertEquals(expected,result);


        }

        @Test
        @DisplayName("Get phone number for station number")
        void testGetPhoneForStationNumber() {

            //ARRANGE
            Set<String> expected = new HashSet<>();

            expected.add("5787-878");
            expected.add("5787-999");
            expected.add("5787-222");
            when(iFireStationDao.getByStationNumber("3")).thenReturn(fireStationList);
            when(iPersonDao.findAll()).thenReturn(personList);


            Set<String> result = safetyNetAlertsService.getPhoneNumberForStationNumber("3");

            assertEquals(expected, result);

        }

        @Test
        @DisplayName("Get persons by station Number")
        void testGetPeopleByStationNumber() {

            //ARRANGE
            ArrayList<HashMap> expected = new ArrayList<>();


            LinkedHashMap<String, String> people = new LinkedHashMap<>();

            people.put("Firstname", "Ed");
            people.put("Lastname", "Norton");
            people.put("Address", "102 Av");
            people.put("Phone", "5787-999");
            expected.add(people);

            //Act

            when(iFireStationDao.getByStationNumber("4")).thenReturn(fireStationList);
            when(iMedicalRecordsDao.getByName("Ed", "Norton")).thenReturn(EdNortonMedical);
            when(iPersonDao.findAll()).thenReturn(personList);

            ArrayList result = safetyNetAlertsService.getPeopleByStationNumber("4");


            //Assert
            assertEquals(expected, result);


        }









}



