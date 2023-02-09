package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
 class PersonServiceTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    IPersonDao iPersonDaoMock;

    @InjectMocks
    PersonServiceImpl personServiceImplMock;

    List<Person> personList;
    @BeforeEach
    void data() {
        personList = new ArrayList<>( Arrays.asList(
                new Person("James", "McAvoy", "101 Av", "NY", "87456", "5787-878", "james@gmail.com"),
                new Person("Ed", "Norton", "101 Av", "NY", "87456", "5787-878", "edNorton@gmail.com"),
                new Person("James", "Franco", "101 Av", "NY", "87456", "5787-878", "jamesFranco@gmail.com")
        ));


    }

    @Test
    @DisplayName("test getAllPersons")
    void testGetAllPerson() {
        List<Person> expected = new ArrayList<>(Arrays.asList(
                new Person("James", "McAvoy", "101 Av", "NY", "87456", "5787-878", "james@gmail.com"),
                new Person("Ed", "Norton", "101 Av", "NY", "87456", "5787-878", "edNorton@gmail.com"),
                new Person("James", "Franco", "101 Av", "NY", "87456", "5787-878", "jamesFranco@gmail.com")));

        when(iPersonDaoMock.findAll()).thenReturn(personList);

        List<Person> result = personServiceImplMock.getAllPerson();

        assertEquals(expected, result);
    }


    @Test
    void testSavePerson() throws  Exception {

        final String baseUrl = "http://localhost:"+randomServerPort+"/person";
        URI uri = new URI(baseUrl);

        Person person = new Person("Lola", "Flores", "10 Rue de Madrid", "Madrid", "78855", "345669", "lola@gmail.com");

        HttpEntity<Person> request = new HttpEntity<>(person);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        assertEquals(201 , result.getStatusCode().value());

    }


    @Test
    void testUpdatePerson() throws Exception {

        Person personUpdated = new Person("Lola", "Flores", "10 Rue de Madrid", "Madrid", "78855", "345669", "lola@gmail.com");
        when(iPersonDaoMock.getByName("Lola", "Flores"))
                .thenReturn(personUpdated);
        Person result = personServiceImplMock.updatePerson(personUpdated);
        assertNotNull(result);
        assertEquals(personUpdated, result);
        verify(iPersonDaoMock, times(1)).update(any(Person.class));
    }

    @Test
    void testDeletePerson() {

        doNothing().when(iPersonDaoMock).delete("Lola", "Flores");

        personServiceImplMock.deletePerson("Lola", "Flores");

        verify(iPersonDaoMock, times(1)).delete("Lola", "Flores");

    }
}
