package com.safetynet.safetyNet.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.safetyNet.dao.IMedicalRecordsDao;
import com.safetynet.safetyNet.model.MedicalRecords;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MedicalRecordsControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    IMedicalRecordsDao iMedicalRecordsDao;

    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();


    @Test
    void testPersonList() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    void testPostMedicalRecords() throws Exception {

        MedicalRecords medicalRecordsMock = new MedicalRecords("James", "McAvoy", "03/06/1984", new ArrayList<>(), new ArrayList<>());
        Mockito.when(iMedicalRecordsDao.save(medicalRecordsMock)).thenReturn(medicalRecordsMock);
        String content = objectWriter.writeValueAsString(medicalRecordsMock);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().is(201));
    }










}



