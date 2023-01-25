package com.safetynet.safetyNet.controller;


import com.safetynet.safetyNet.model.Person;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

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


    @Test
    void testPersonList() throws Exception {
        mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testAjouterPerson() throws  Exception {

        final String baseUrl = "http://localhost:"+randomServerPort+"/person";
        URI uri = new URI(baseUrl);

        Person person = new Person("Lola", "Flores", "10 Rue de Madrid", "Madrid", "78855", "345669", "lola@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Person> request = new HttpEntity<>(person, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(201 , result.getStatusCodeValue());

    }






}



