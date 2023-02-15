package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IFireStationDao;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

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

    List<FireStation> fireStationList;
    @BeforeEach
            void data() {
        fireStationList  = new ArrayList<>(Arrays.asList(
                new FireStation("101 Av", "3"),
                new FireStation("102 Av", "4"),
                new FireStation("103 Av", "5")
        ));

    }


    @Test
    @DisplayName("test getAllFireStation")
    void testGetAllFirestations() {
        List<FireStation> expected = new ArrayList<>(Arrays.asList(
                new FireStation("101 Av", "3"),
                new FireStation("102 Av", "4"),
                new FireStation("103 Av", "5")));

        when(iFireStationDaoMock.findAll()).thenReturn(fireStationList);

        List<FireStation> result = fireStationServiceImplMock.getAllFirestation();

        assertEquals(expected, result);
    }
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
    void testDeleteFirestationByAddress() {

        doNothing().when(iFireStationDaoMock).deleteByAddress("101 Av");
        fireStationServiceImplMock.deleteFirestationByAddress("101 Av");
        verify(iFireStationDaoMock, times(1)).deleteByAddress("101 Av");

    }

    @Test
    void testDeleteFirestationByStationNumber() {

        doNothing().when(iFireStationDaoMock).deleteByStationNumber("3");
        fireStationServiceImplMock.deleteFirestationByNumber("3");
        verify(iFireStationDaoMock, times(1)).deleteByStationNumber("3");

    }
}
