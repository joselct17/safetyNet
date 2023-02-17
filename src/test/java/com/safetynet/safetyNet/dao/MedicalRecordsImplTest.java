package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;


import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.MedicalRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
public class MedicalRecordsImplTest {

    @InjectMocks
    MedicalRecordsDaoImpl medicalRecordsDaoCUT;

    @Mock
    private JsonReader jsonReaderMock;

    @BeforeEach
    void initializeList() {

        ArrayList<MedicalRecords> dataInitialList = new ArrayList<>(Arrays.asList(
                new MedicalRecords("Bruce", "Wayne", "20/10/1995",
                        new ArrayList<>(Arrays.asList("medoc1", "medoc2")),
                        new ArrayList<>(Arrays.asList("allergie1", "allergie2"))),

                new MedicalRecords("Kal", "El", "21/01/1992",
                        new ArrayList<>(Arrays.asList("medoc1")),
                        new ArrayList<>(Arrays.asList("allergie1"))),

                new MedicalRecords("Barry", "Allen", "22/07/1999",
                        new ArrayList<>(Arrays.asList("medoc1", "medoc2", "medoc3")),
                        new ArrayList<>(Arrays.asList()))
        ));


        medicalRecordsDaoCUT.setMedicalList(dataInitialList);

    }

    @Test
    @DisplayName("Test Get by name")
    void testGetByName() throws Exception {
        //Arrange
        MedicalRecords expected = new MedicalRecords("Bruce","Wayne","20/10/1995",
                new ArrayList<> (Arrays.asList("medoc1","medoc2")),
                new ArrayList<> (Arrays.asList("allergie1", "allergie2"))
        );

        //Act
        MedicalRecords result = medicalRecordsDaoCUT.getByName("Bruce", "Wayne");

        //Assert
        assertEquals(expected,result,"Returned Medical record must be same as expected");
    }


    @Test
    @DisplayName("Delete MedicalRecord")
    void testDelete_MedicalRecords() {

        List<MedicalRecords> expectedList = new ArrayList<>(Arrays.asList(
                new MedicalRecords("Kal", "El", "21/01/1992", new ArrayList<>(Arrays.asList("medoc1")), new ArrayList<>(Arrays.asList("allergie1"))),
                new MedicalRecords("Barry", "Allen", "22/07/1999", new ArrayList<>(Arrays.asList("medoc1", "medoc2", "medoc3")), new ArrayList<>(Arrays.asList()))
                ));


        medicalRecordsDaoCUT.delete("Bruce", "Wayne");
        List<MedicalRecords> finalList = medicalRecordsDaoCUT.findAll();


        //ASSERT
        assertEquals(2, finalList.size());
        assertEquals(expectedList, finalList);
    }

    @Test
    @DisplayName("Update")
    void test_Update() throws Exception {
        //Arrange
        List<MedicalRecords> expectedList = new ArrayList<> (Arrays.asList(
                new MedicalRecords("Bruce", "Wayne", "20/10/1995",
                        new ArrayList<>(Arrays.asList("medoc1", "medoc2")),
                        new ArrayList<>(Arrays.asList("allergie1", "allergie2"))),

                new MedicalRecords("Kal", "El", "21/01/1992",
                        new ArrayList<>(Arrays.asList("medoc1")),
                        new ArrayList<>(Arrays.asList("allergie1"))),

                new MedicalRecords("Barry", "Allen", "22/07/1999",
                        new ArrayList<>(Arrays.asList("medoc1", "medoc2", "medoc3")),
                        new ArrayList<>(Arrays.asList()))));

        //ACT

       medicalRecordsDaoCUT.update(new MedicalRecords(
               "Barry",
               "Allen",
               "22/07/1999",
               new ArrayList<>(Arrays.asList("medoc1", "medoc2", "medoc3")),
               new ArrayList<>()
       ));

       List<MedicalRecords> obj = medicalRecordsDaoCUT.findAll();

        assertEquals(3, obj.size());
        assertEquals(expectedList, obj);
    }

    @Test
    @DisplayName("Test GetByName, no medical record found, must return null")
    void testGetByName_IllegalStateExceptionNotFound() throws Exception {
        //Arrange
        //Act
        MedicalRecords result = medicalRecordsDaoCUT.getByName("John", "Unknown");
        //Assert
        assertNull(result,"no medical record found, must return null");
    }
}
