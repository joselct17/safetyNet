package com.safetynet.safetyNet.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStation {

    private String address;

    private String stationNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }


}
