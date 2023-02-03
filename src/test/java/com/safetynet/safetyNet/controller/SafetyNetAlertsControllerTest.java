package com.safetynet.safetyNet.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.safetynet.safetyNet.dao.IPersonDao;
import com.safetynet.safetyNet.model.Person;
import com.safetynet.safetyNet.service.ISafetyNetAlertsService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class SafetyNetAlertsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    ISafetyNetAlertsService iSafetyNetMock;

    @InjectMocks
    IPersonDao iPersonDao;

    List<Person> personEmailListforCulver;


    @BeforeEach

    void initializeData() {

        personEmailListforCulver = new ArrayList<>((Collection) new Person("Jon", "Doe", "101 Av", "Culver", "78780", "555", "d@d.com"));
    }

    @Test
    void testGetEmailsInCity() throws Exception {

        JSONObject expected = new JSONObject();

    expected.put("email", "a@a.com" );
    expected.put("email", "b@b.com");
    expected.put("email", "c@c.com");

    when(iPersonDao.getByCity("Culver")).thenReturn(personEmailListforCulver);
    //when(iPersonDao.findAll()).thenReturn();

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



