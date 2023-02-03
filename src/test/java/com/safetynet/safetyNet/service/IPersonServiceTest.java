package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.Person;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
 class IPersonServiceTest {

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
