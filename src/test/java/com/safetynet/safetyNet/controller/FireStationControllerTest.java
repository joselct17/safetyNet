package com.safetynet.safetyNet.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.safetyNet.dao.IFireStationDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.service.FireStationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FireStationControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private FireStationServiceImpl fireStationServiceImpl;


    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();


    @Test
    void testFirestationList() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void testPostFirestation() throws Exception {

        FireStation fireStationMock = new FireStation("101 Av", "5");
        when(fireStationServiceImpl.saveFirestation(fireStationMock)).thenReturn(fireStationMock);
        String content = objectWriter.writeValueAsString(fireStationMock);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().is(201));

    }
    @Test
    void testPutFirestation() throws Exception {

        FireStation fireStationToUpdate = new FireStation("101 Av", "4");

        String jsonContent = objectMapper.writeValueAsString(fireStationToUpdate);

        FireStation fireStationUpdated = new FireStation("200 Av", "5");

        when(fireStationServiceImpl.updateFirestation(any(FireStation.class))).thenReturn(fireStationUpdated);



        mockMvc .perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(jsonContent))
                        .andExpect(status().is(201)).andReturn();

    }


    @Test
    void testDeleteFirestationByAddress() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("address", "101 Av"))
                .andExpect(status().isGone()).andReturn();
        verify(fireStationServiceImpl).deleteFirestationByAddress("101 Av");
    }


    @Test
    void testDeleteFirestationByStationNumber() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("stationNumber", "4"))
                .andExpect(status().isGone()).andReturn();
        verify(fireStationServiceImpl).deleteFirestationByNumber( "4");
    }


}



