package com.safetynet.safetyNet.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import com.safetynet.safetyNet.dao.PersonDaoImpl;
import com.safetynet.safetyNet.service.ISafetyNetAlertsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;





@SpringBootTest
@AutoConfigureMockMvc
class SafetyNetAlertsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    ISafetyNetAlertsService iSafetyNetMock;

    @InjectMocks
    PersonDaoImpl personDaoCUT;




    @Test
    void  testPeopleByFirestation() throws Exception {
        mockMvc.perform(get("/firestation/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void  childsAlert() throws Exception {
        mockMvc.perform(get("/childAlert/101 Gotham City"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void phoneNumber() throws Exception {
        mockMvc.perform(get("/phoneAlert/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void peopleByFirestationAddress() throws Exception {
        mockMvc.perform(get("/fire/200 Rue du Martre"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void peopleByStationNumber() throws Exception {
        mockMvc.perform(get("/flood/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void peopleByName() throws Exception {
        mockMvc.perform(get("/personInfo/Bruce+Wayne"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testGetEmailsInCity() throws Exception {
        mockMvc.perform(get("/communityEmail/Culver"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}



