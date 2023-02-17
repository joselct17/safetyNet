package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class PersonDaoImplTest {

    @InjectMocks
    PersonDaoImpl personDaoCUT;

    @Mock
    private JsonReader jsonReaderMock;

    @BeforeEach
    void initializeData() {

        ArrayList<Person> dataInitialList = new ArrayList<> (Arrays.asList(
                new Person("John","Doe","address1", "Varadero", "1111", "888888888", "johndoe@mail.com"),
                new Person("Mike","Doe","address1", "Varadero", "2222", "99999999", "mikedoe@mail.com"),
                new Person("Eric","Monson","address3", "Plessis", "3333", "7777777", "ericmonson@mail.com")
        ));
        personDaoCUT.setPersonList(dataInitialList);
    }


    @Test
    @DisplayName("Test get by city")
    void testGetByCity()   throws Exception {
        //Arrange
        List<Person> expected = new ArrayList<>(Arrays.asList(
                new Person("John","Doe","address1", "Varadero", "1111", "888888888", "johndoe@mail.com"),
                new Person("Mike","Doe","address1", "Varadero", "2222", "99999999", "mikedoe@mail.com")
        ));

        //Act
        List<Person> result = personDaoCUT.getByCity("Varadero");
        //Assert
        assertEquals(expected,result,"Returned person must be same as mocked");
    }

    @Test
    @DisplayName("Test get by lastName")
    void testGetByLastName() throws Exception {
        //Arrange
        List<Person> expected = new ArrayList<>(Arrays.asList(
                new Person("John","Doe","address1", "Varadero", "1111", "888888888", "johndoe@mail.com"),
                new Person("Mike","Doe","address1", "Varadero", "2222", "99999999", "mikedoe@mail.com")
        ));

        //Act
        List<Person> result = personDaoCUT.getByLastName("Doe");

        //Assert
        assertEquals(expected, result, "Returned person must be same as mocked");

    }

    @Test
    @DisplayName("Test get by address")
    void testGetByAddress() throws Exception {
        //Arrange
        List<Person> expected = new ArrayList<>(Arrays.asList(
                new Person("John","Doe","address1", "Varadero", "1111", "888888888", "johndoe@mail.com"),
                new Person("Mike","Doe","address1", "Varadero", "2222", "99999999", "mikedoe@mail.com")
        ));

        //Act
        List<Person> result = personDaoCUT.getByAddress("address1");

        //Assert
        assertEquals(expected, result, "Returned person must be same as mocked");

    }

    @Test
    @DisplayName("Test get by Name")
    void testGetByName() throws Exception {
        //Arrange
        Person expected =
                new Person("Eric","Monson","address3", "Plessis", "3333", "7777777", "ericmonson@mail.com");

        //Act
        Person result = personDaoCUT.getByName("Eric", "Monson");

        //Assert
        assertEquals(expected, result, "Returned person must be same as mocked");

    }


    @Test
    @DisplayName("Test delete Person")
    void testDelete_Person() {

        ArrayList<Person> expectedList = new ArrayList<> (Arrays.asList(
                new Person("John","Doe","address1", "Varadero", "1111", "888888888", "johndoe@mail.com"),
                new Person("Mike","Doe","address1", "Varadero", "2222", "99999999", "mikedoe@mail.com")
        ));

        personDaoCUT.delete("Eric", "Monson");
        List<Person> finalList = personDaoCUT.findAll();


        assertEquals(2,finalList.size() );
        assertEquals(expectedList, finalList);


    }


    @Test
    @DisplayName("Test update Person")
    void test_Update() {

        ArrayList<Person> expectedList = new ArrayList<> (Arrays.asList(
                new Person("John","Doe","address1", "Varadero", "1111", "888888888", "johndoe@mail.com"),
                new Person("Mike","Doe","address1", "Varadero", "2222", "99999999", "mikedoe@mail.com"),
                new Person("Eric","Monson","address3", "Plessis", "3333", "7777777", "ericmonson@mail.com")
        ));


        personDaoCUT.update(new Person("Eric","Monson","address3", "Plessis", "3333", "7777777", "ericmonson@mail.com"));


        List<Person> obj = personDaoCUT.findAll();


        assertEquals(3, obj.size());
        assertEquals(expectedList, obj);





    }



}