package com.safetynet.safetyNet.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.Person;
import com.safetynet.safetyNet.service.PersonServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersonControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonServiceImpl personServiceImpl;


    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();


    @BeforeEach
    void setup() {
        ObjectWriter objectWriter = objectMapper.writer();
        objectMapper = new ObjectMapper();
        Person person = new Person("James", "McAvoy", "101 Av", "NY", "87456", "5787-878", "james@gmail.com");
    }


    @Test
    void testPersonList() throws Exception {
        mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testPostPerson() throws Exception {

        Person personMock = new Person("James", "McAvoy", "101 Av", "NY", "87456", "5787-878", "james@gmail.com");
        when(personServiceImpl.savePerson(personMock)).thenReturn(personMock);
        String content = objectWriter.writeValueAsString(personMock);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().is(201));

    }

    @Test
    void testPutPerson() throws Exception {

        Person personToUpdateMock = new Person("James", "McAvoy", "101 Av", "NY", "87456", "5787-878", "james@gmail.com");

        String jsonContent = objectMapper.writeValueAsString(personToUpdateMock);

        Person personUpdatedMock = new Person("James", "McAvoy", "200 Av", "NY", "9999", "5787-878", "james@gmail.com");
        when(personServiceImpl.updatePerson(any(Person.class))).thenReturn(personUpdatedMock);

        //Act

        MvcResult result = mockMvc
                .perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().is(201)).andReturn();

        //Assert
        verify(personServiceImpl).updatePerson(any(Person.class));
        Person personResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Person>() {});
        assertNotNull(personResult);
        assertEquals(personUpdatedMock.getEmail(), personResult.getEmail());


    }

    @Test
    void testDeletePerson() throws Exception {
        mockMvc.perform(delete("/person")
                        .param("firstName", "James")
                        .param("lastName", "McAvoy"))
                .andExpect(status().isGone()).andReturn();
        verify(personServiceImpl).deletePerson("James", "McAvoy");
    }

}



