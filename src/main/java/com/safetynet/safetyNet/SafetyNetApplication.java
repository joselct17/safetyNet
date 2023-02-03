package com.safetynet.safetyNet;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.time.Period;


@SpringBootApplication
@ComponentScan({"com.safetynet.safetyNet"})
@ComponentScan({"com.safetynet.safetyNet.json"})
@ComponentScan({"com.safetynet.safetyNet.service"})
public class SafetyNetApplication  extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);


	}



}


