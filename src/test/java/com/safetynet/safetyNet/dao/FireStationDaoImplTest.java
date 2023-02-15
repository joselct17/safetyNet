package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.json.JsonReader;
import com.safetynet.safetyNet.model.FireStation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
public class FireStationDaoImplTest {

    @InjectMocks
    FireStationDaoImpl fireStationDaoCUT;

    @Mock
    private JsonReader jsonReaderMock;


    @BeforeEach
    void initializeData() {

        ArrayList<FireStation> dataInitialList = new ArrayList<>(Arrays.asList(
                new FireStation("address1", "6"),
                new FireStation("address2", "7"),
                new FireStation("address3", "8")
        ));

        fireStationDaoCUT.setFireStationList(dataInitialList);
    }


    @Test
    @DisplayName("Test get by address")
    void testGetByAddress() throws Exception {
        //Arrange

        String expected = "6";

        //Act
        String result = fireStationDaoCUT.getByAddress("address1");

        //Assert
        assertEquals(expected, result, "Returned value must be 6");
    }

    @Test
    @DisplayName("Test getByStationnumber")
    void testGetByStationnumber() throws Exception {
        //Arrange
        List<FireStation> expectedList = new ArrayList<>(Arrays.asList(
                new FireStation("address1", "6")
        ));

        //Act
        List<FireStation> objectList = fireStationDaoCUT.getByStationNumber("6");

        //Assert
        assertEquals(1, objectList.size(), "Expected list size is 3");
        assertEquals(expectedList, objectList, "Returned list must one Firestation");
    }

    @Test
    @DisplayName("Test getByAddress, address not found")
    void testGetByAddressNotFound() throws Exception {
        //Arrange

        //Act
        String result = fireStationDaoCUT.getByAddress("adressUnknown");

        //Assert
        assertNull(result, "Returned value must be null");
    }

    @Test
    @DisplayName("Delete by station Number")
    void testDeleteByStationNumber() throws Exception {

        //Arrange
        ArrayList<FireStation> dataInitialList = new ArrayList<>(Arrays.asList(
                new FireStation("adress1", "1"),
                new FireStation("adress2", "2"),
                new FireStation("adress3", "3"),
                new FireStation("adress4", "1")
        ));
        fireStationDaoCUT.setFireStationList(dataInitialList);


        List<FireStation> expectedList = new ArrayList<>(Arrays.asList(
                new FireStation("adress2", "2"),
                new FireStation("adress3", "3")
        ));


        //Act
        fireStationDaoCUT.deleteByStationNumber("1");
        List<FireStation> finalList = fireStationDaoCUT.findAll();

        //ASSERT

        assertEquals(2, finalList.size());
        assertEquals(expectedList, finalList);


    }

    @Test
    @DisplayName("Delete by address")
    void testDeleteByAddress() throws Exception {


        List<FireStation> expectedList = new ArrayList<>(Arrays.asList(
                new FireStation("address2", "7"),
                new FireStation("address3", "8")
        ));


        //Act
        fireStationDaoCUT.deleteByAddress("address1");
        List<FireStation> finalList = fireStationDaoCUT.findAll();

        //ASSERT

        assertEquals(2, finalList.size());
        assertEquals(expectedList, finalList);

    }


}
