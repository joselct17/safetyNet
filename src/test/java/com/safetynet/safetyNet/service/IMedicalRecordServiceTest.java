package com.safetynet.safetyNet.service;

import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.model.MedicalRecords;
import org.junit.Assert;
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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class IMedicalRecordServiceTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    IMedicalRecordsDao iMedicalRecordsDaoMock;


    @InjectMocks
    MedicalRecordServiceImpl medicalRecordServiceImplMock;




    @Test
    void testAjouterMedicalRecords() throws  Exception {

        final String baseUrl = "http://localhost:"+randomServerPort+"/medicalRecord";
        URI uri = new URI(baseUrl);

        MedicalRecords medicalRecords = new MedicalRecords("Nina", "Gutierrez", "20/10/1195", new ArrayList<>(), new ArrayList<>() );


        HttpEntity<MedicalRecords> request = new HttpEntity<>(medicalRecords);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(201 , result.getStatusCode().value());

    }


    @Test
    void testUpdateMedicalRecords() {
        MedicalRecords medicalrecordToUpdate = new MedicalRecords("John","Doe","10/10/1995",new ArrayList<>(),new ArrayList<>());
        MedicalRecords medicalrecordExpected = new MedicalRecords("John","Doe","10/10/1995",new ArrayList<>(),new ArrayList<>());
        when(iMedicalRecordsDaoMock.getByName("John", "Doe"))
                .thenReturn(medicalrecordToUpdate);


        MedicalRecords result = medicalRecordServiceImplMock.updateMedicalRecord(medicalrecordToUpdate);

        //Assert
        assertNotNull(result);
        assertEquals(medicalrecordExpected,result);
        verify(iMedicalRecordsDaoMock, times(1)).update(any(MedicalRecords.class));
    }

    @Test
    void testDeleteMedicalRecords() {
        doNothing().when(iMedicalRecordsDaoMock).delete("John","Doe");

        medicalRecordServiceImplMock.deleteMedicalRecord("John", "Doe");

        verify(iMedicalRecordsDaoMock, times(1)).delete("John", "Doe");
    }
}
