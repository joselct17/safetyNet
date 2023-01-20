package com.safetynet.safetyNet;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


@SpringBootApplication
@ComponentScan({"com.safetynet.safetyNet"})
public class SafetyNetApplication  extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);


	}



}


