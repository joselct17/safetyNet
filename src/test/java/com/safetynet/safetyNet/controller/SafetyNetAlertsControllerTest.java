package com.safetynet.safetyNet.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import com.jsoniter.any.Any;
import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@AutoConfigureMockMvc
class SafetyNetAlertsControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    void testGetEmailsInCity() throws Exception {
        mockMvc.perform(get("/communityEmail/Culver"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void  testPeopleByFirestation() throws Exception {
        mockMvc.perform(get("/firestation/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void  childsByAddress() throws Exception {
        mockMvc.perform(get("/childAlert/101 Gotham City"))
                .andDo(print())
                .andExpect(status().isOk());
    }





}



