package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.time.LocalDate;
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
    List<Person> personListAddress5;

    List<Person>personListAddress4;
    List<Person> personListFamilyMcAvoy;


    List<MedicalRecords> medicalRecordsList;
    Person EdNortonPerson;
    Person JohnMcAvoyPerson;

    Person ElenaMcAvoyPerson;
    Person JamesFrancoPerson;

    MedicalRecords EdNortonMedical;
    MedicalRecords JohnMcAvoyMedical;
    MedicalRecords JamesFrancoMedical;

    MedicalRecords ElenaMcAvoyMedical;
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
                new MedicalRecords("James", "Franco", "05/21/1994", new ArrayList<>(), new ArrayList<>()),
                new MedicalRecords("Elena", "McAvoy", "05/21/1994", new ArrayList<>(), new ArrayList<>())
        ));

        personList = new ArrayList<>( Arrays.asList(
                new Person("John", "McAvoy", "101 Av", "NY", "87456", "5787-878", "john@gmail.com"),
                new Person("Ed", "Norton", "102 Av", "NY", "87456", "5787-999", "edNorton@gmail.com"),
                new Person("James", "Franco", "103 Av", "NY", "87456", "5787-222", "jamesFranco@gmail.com"),
                new Person("Elena", "McAvoy","101 Av", "NY", "87456", "5787-878", "elena@gmail.com")
        ));
        personListAddress5 = new ArrayList<>(Arrays.asList(
                new Person("James", "Franco", "103 Av", "NY", "87456", "5787-222", "jamesFranco@gmail.com")

                ));
        personListAddress4 = new ArrayList<>(Arrays.asList(
                new Person("Ed", "Norton", "102 Av", "NY", "87456", "5787-999", "edNorton@gmail.com")

        ));



        JohnMcAvoyMedical =  new MedicalRecords("John", "McAvoy", "01/20/1554", new ArrayList<>(), new ArrayList<>());
        EdNortonMedical= new MedicalRecords("Ed", "Norton", "02/25/2015", new ArrayList<>(), new ArrayList<>());
        JamesFrancoMedical =new MedicalRecords("James", "Franco", "05/21/1994", new ArrayList<>(), new ArrayList<>());
        ElenaMcAvoyMedical =  new MedicalRecords("Elena", "McAvoy", "05/21/1994", new ArrayList<>(), new ArrayList<>());

        JohnMcAvoyPerson = new Person("John", "McAvoy", "101 Av", "NY", "87456", "5787-878", "john@gmail.com");
        EdNortonPerson = new Person("Ed", "Norton", "102 Av", "NY", "87456", "5787-999", "edNorton@gmail.com");
        JamesFrancoPerson = new Person("James", "Franco", "103 Av", "NY", "87456", "5787-222", "jamesFranco@gmail.com");
        ElenaMcAvoyPerson = new Person("Elena", "McAvoy","101 Av", "NY", "87456", "5787-878", "elena@gmail.com");


        personListFamilyMcAvoy = new ArrayList<>(Arrays.asList(
                JohnMcAvoyPerson,
                ElenaMcAvoyPerson
        ));



    }


    @Test
    @DisplayName("Get all emails by city")
    void testGetEmailsByCity() {
        //ARRANGE
        Set<String> expected = new HashSet<>();
        expected.add("john@gmail.com");
        expected.add("edNorton@gmail.com");
        expected.add("elena@gmail.com");
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
            ArrayList<Object> expected = new ArrayList<>();

            ArrayList<Object> total = new ArrayList<>();

            LinkedHashMap<String, String> childs = new LinkedHashMap<>();

            LinkedHashMap<String, String> adults = new LinkedHashMap<>();

            childs.put("Firstname", "Ed");
            childs.put("Lastname", "Norton");
            childs.put("Age", "7");

            total.add(childs);


            expected.add(total);

            when(iPersonDao.getByAddress("102 Av")).thenReturn(new ArrayList<>(Arrays.asList(EdNortonPerson)));//only 1 child at this address
            when(iMedicalRecordsDao.getByName("Ed","Norton")).thenReturn(EdNortonMedical);

            //Act
            ArrayList<Object> result = safetyNetAlertsService.getChildsByAddress("102 Av");

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
        @DisplayName("Get persons by station Number + Number of adults and children")
        void testGetPeopleByStationNumber() {

            //ARRANGE
            ArrayList<HashMap> expected = new ArrayList<>();

            LinkedHashMap<String, String> people = new LinkedHashMap<>();
            HashMap<String, Integer> count = new HashMap<>();

            people.put("Firstname", "Ed");
            people.put("Lastname", "Norton");
            people.put("Address", "102 Av");
            people.put("Phone", "5787-999");

            expected.add(people);

            count.put("Childs", 1);
            count.put("Adults", 0);
            expected.add(count);

            //Act
            when(iFireStationDao.getByStationNumber("4")).thenReturn(fireStationList);
            when(iMedicalRecordsDao.getByName("Ed", "Norton")).thenReturn(EdNortonMedical);
            when(iPersonDao.findAll()).thenReturn(personListAddress4);

            ArrayList result = safetyNetAlertsService.getPeopleByStationNumber("4");

            //Assert
            assertEquals(expected, result);


        }


        @Test
        @DisplayName("Get a person info by name and his family")
        void testGetPeopleByName() {
        //ARRANGE

            //Array list final ou tout est stocké
            ArrayList<HashMap> expected = new ArrayList<>();

            //Map pour la personne a tester
            LinkedHashMap<String, Object> obj1 = new LinkedHashMap<>();

            //Mpa pour la famille de la personne
            LinkedHashMap<String, Object> obj2 = new LinkedHashMap<>();

            //Map qui stocke les infos PEOPLE de la personne a tester
            LinkedHashMap<String, String> people = new LinkedHashMap<>();

            //Map qui stocke les infos PEOPLE de la famille
            LinkedHashMap<String, String> family = new LinkedHashMap<>();

            people.put("Firstname", "John");
            people.put("Lastname", "McAvoy");
            people.put("Address", "101 Av");
            people.put("Age", "469");
            people.put("Email", "john@gmail.com");

            //MAP qui stocke les infos MEDICAL de la personne a tester
            LinkedHashMap<String, List> med = new LinkedHashMap<>();
            med.put("Medication", new ArrayList<>());
            med.put("Allergies", new ArrayList<>());

            family.put("Firstname", "Elena");
            family.put("Lastname", "McAvoy");
            family.put("Address", "101 Av");
            family.put("Age", "469");
            family.put("Email", "elena@gmail.com");


            //Map qui stocke les infos MEDICAL de la famille
            LinkedHashMap<String, List> med2 = new LinkedHashMap<>();
            med2.put("Medication", new ArrayList<>());
            med2.put("Allergies", new ArrayList<>());


            obj1.putAll(people);
            obj1.putAll(med);

            obj2.putAll(family);
            obj2.putAll(med2);

            expected.add(obj1);
            expected.add(obj2);

            when(iMedicalRecordsDao.getByName("John", "McAvoy")).thenReturn(JohnMcAvoyMedical);
            when(iPersonDao.getByName("John", "McAvoy")).thenReturn(JohnMcAvoyPerson);
            when(iPersonDao.getByLastName("McAvoy")).thenReturn(personListFamilyMcAvoy);

            //ACT

            ArrayList result = safetyNetAlertsService.getPeopleByFirstNameAndLastName("John", "McAvoy");

            //ASSERT
            assertEquals(expected, result);


        }

    @Test
    @DisplayName("Get all emails for a city")
    void testGetAllEmailsByCity() {

        Set<String> expected = new HashSet<>();

        expected.add("edNorton@gmail.com");
        expected.add("john@gmail.com");
        expected.add("jamesFranco@gmail.com");
        expected.add("elena@gmail.com");



        when(iPersonDao.getByCity("NY")).thenReturn(personList);

        Set result = safetyNetAlertsService.getEmailsByCity("NY");

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("get People By FireAddress")
    void testGetPeopleByFireAddress() {
        ArrayList<HashMap> expected = new ArrayList<>();

        LinkedHashMap<String, Object> obj = new LinkedHashMap<>();

        LinkedHashMap<String, String> people = new LinkedHashMap<>();
        people.put("Firstname", "James");
        people.put("Lastname", "Franco");
        people.put("Phone", "5787-222");
        people.put("Age", "28");
        people.put("Firestation", "5");



        LinkedHashMap<String, List> medical = new LinkedHashMap<>();
        medical.put("Medication", new ArrayList<>());
        medical.put("Allergies",  new ArrayList<>());


        obj.putAll(people);
        obj.putAll(medical);

        expected.add(obj);

        when(iMedicalRecordsDao.getByName("James", "Franco")).thenReturn(JamesFrancoMedical);


        when(iFireStationDao.getByAddress("102 Av")).thenReturn("5");
        when(iPersonDao.getByAddress("102 Av")).thenReturn(personListAddress5);

        ArrayList result = safetyNetAlertsService.getPeopleByFireAddress("102 Av");

        assertEquals(expected, result);


    }









}



