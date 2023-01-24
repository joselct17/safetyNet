package com.safetynet.safetyNet.dao;

import com.safetynet.safetyNet.model.FireStation;
import com.safetynet.safetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Repository
public class FireStationDaoImpl implements FireStationDao {

    private ArrayList<FireStation> listFireStation;

    public FireStationDaoImpl(ArrayList<FireStation> listFireStation) throws IOException, ParseException {
        this.listFireStation = listFireStation;

        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/main/resources/JSON/data.JSON");

        Object jsonObj = parser.parse(reader);

        JSONObject jsonObject = (JSONObject) jsonObj;

        JSONArray jsonArray = (JSONArray ) jsonObject.get("firestations");

        Iterator<JSONObject> i = jsonArray.iterator();

        while (i.hasNext()) {

            JSONObject o = i.next();
            FireStation fireStation = new FireStation();
            fireStation.setAddress((String)o.get("address"));
            fireStation.setStationNumber((String)o.get("station"));

            listFireStation.add(fireStation);

        }
    }


    @Override
    public List<FireStation> findAll() {
        return listFireStation;
    }

    @Override
    public List<FireStation> findByAddress() {
        return null;
    }

    @Override
    public List<FireStation> findByNumber() {
        return null;
    }

    @Override
    public FireStation save(FireStation fireStation) {
        listFireStation.add(fireStation);
        return fireStation;
    }

}
