package com.safetynet.safetyNet.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloodResponse {

    private Integer stationNumber;

    private String address;

    private String firstName;

    private String lastName;

    private String telephone;

    private String birthDate;

    private ArrayList medication;

    private ArrayList allergies;


}
