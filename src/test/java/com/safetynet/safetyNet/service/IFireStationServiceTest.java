package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.model.FireStation;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class IFireStationServiceTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    IFireStationDao iFireStationDaoMock;

    @InjectMocks
    FireStationServiceImpl fireStationServiceImplMock;



    @Test
    void testSaveFireStation() throws Exception {

        final String baseUrl = "http://localhost:" +randomServerPort+"/firestation";
        URI uri = new URI(baseUrl);

        FireStation fireStation = new FireStation("101 av", "4");

        HttpEntity<FireStation> request = new HttpEntity<>(fireStation);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertEquals(201, result.getStatusCode().value());
    }


    @Test
    void testUpdateFirestation() throws Exception {
        FireStation fireStationToUpdate = new FireStation("une address", "4");
        FireStation fireStationUpdated = new FireStation("une address", "3");


        when(iFireStationDaoMock.getByAddress("une address")).thenReturn("3");

        FireStation result = fireStationServiceImplMock.updateFirestation(fireStationToUpdate);

        assertNotNull(result);
        assertEquals(fireStationUpdated, result);

        verify(iFireStationDaoMock, times(1)).update(any(FireStation.class));

    }


    @Test
    void testDeletePerson() {

        doNothing().when(iFireStationDaoMock).delete("101 Av", "3");
        fireStationServiceImplMock.deleteFirestation("101 Av", "3");
        verify(iFireStationDaoMock, times(1)).delete("101 Av", "3");

    }
}
