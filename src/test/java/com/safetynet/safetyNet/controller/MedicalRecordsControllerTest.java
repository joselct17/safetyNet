package com.safetynet.safetyNet.controller;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.safetyNet.model.MedicalRecords;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.safetynet.safetyNet.service.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MedicalRecordsControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MedicalRecordServiceImpl medicalRecordService;

    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();

    private MedicalRecords medicalRecords;

    @BeforeEach
    void data() {
        objectMapper = new ObjectMapper();
        medicalRecords = new MedicalRecords(
                "James",
                "McAvoy",
                "03/06/1984",
                new ArrayList<> (),
                new ArrayList<> ()
        );
    }


    @Test
    void testMedicalRecordsList() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    void testPostMedicalRecords() throws Exception {

        MedicalRecords medicalRecordsMock = new MedicalRecords("James", "McAvoy", "03/06/1984", new ArrayList<>(), new ArrayList<>());
        Mockito.when(medicalRecordService.saveMedicalRecord(medicalRecordsMock)).thenReturn(medicalRecordsMock);
        String content = objectWriter.writeValueAsString(medicalRecordsMock);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().is(201));
    }

    @Test
    void testPutMedicalRecords() throws Exception {

        MedicalRecords medicalRecordsToUpdate = new MedicalRecords("James", "McAvoy", "03/06/1984", new ArrayList<>(), new ArrayList<>());
        String content = objectMapper.writeValueAsString(medicalRecordsToUpdate);
        MedicalRecords medicalRecordsUpdated = new MedicalRecords("James", "McAvoy", "03/06/1995", new ArrayList<>(), new ArrayList<>());

        when(medicalRecordService.updateMedicalRecord(any(MedicalRecords.class))).thenReturn(medicalRecordsUpdated);

        //Act
        MockHttpServletRequestBuilder  builder = MockMvcRequestBuilders
                .put("/medicalRecord/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);




        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is(201))
                        .andExpect((ResultMatcher) MockMvcResultMatchers.content());


        //Assert
        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecords.class));
       // MedicalRecords medicalrecordResult = objectMapper.readValue(builder.getResponse().getContentAsString(), new TypeReference<MedicalRecords>() {});
     //   assertNotNull(medicalrecordResult);
    //    assertEquals(medicalRecordsUpdated, medicalrecordResult);

    }


    @Test
    void testDeleteMedicalrecord() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "James")
                        .param("lastName", "McAvoy"))
                .andExpect(status().isGone()).andReturn();
        verify(medicalRecordService).deleteMedicalRecord("James", "McAvoy");
    }










}



