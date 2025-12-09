package com.greenhouse.ca2.parsing;

import com.greenhouse.ca2.dao.EmissionRecordDao;
import com.greenhouse.ca2.entities.Emission;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@ApplicationScoped
public class EmissionJSONParsing {

    @Inject
    EmissionRecordDao emissionRecordDao;

   
    @Transactional
    public int importFromJson() throws Exception {

        // Point to the JSON file
        File jsonFile = new File("src/main/resources/GreenhouseGasEmissions2025.json");

        String jsonString = "";

        // Use Scanner to read the whole file 
        try (Scanner scanner = new Scanner(jsonFile)) {
            while (scanner.hasNextLine()) {
                jsonString += scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("JSON file not found", e);
        }

       
        JSONParser parser = new JSONParser();
        JSONObject rootObject = (JSONObject) parser.parse(jsonString);

      
        JSONArray emissionsArray = (JSONArray) rootObject.get("Emissions");

        int inserted = 0;

        // Go through each object in the Emissions array
        for (int i = 0; i < emissionsArray.size(); i++) {

            JSONObject jo = (JSONObject) emissionsArray.get(i);

            // Get values from JSON object
            String categoryName = (String) jo.get("Category");
            Object valueObj = jo.get("Value");

            if (categoryName == null || valueObj == null) {
                // If any important field is missing I skip it
                continue;
            }

            double value;

            // Value might be no. or string
            if (valueObj instanceof Number) {
                value = ((Number) valueObj).doubleValue();
            } else {
                value = Double.parseDouble(valueObj.toString());
            }

            // Rule: value must be > 0
            if (value <= 0) {
                continue;
            }

            //Set WEM and year
            String scenario = "WEM";
            int year = 2023;

            // Create Emission entity and fill in the fields
            Emission emission = new Emission();
            emission.setCategoryName(categoryName);
            emission.setCategoryDescription(""); // empty for now
            emission.setScenario(scenario);
            emission.setYear(year);
            emission.setValue(value);
            emission.setApproved(false);

            // Save to the database using DAO
            emissionRecordDao.persist(emission);
            inserted++;
        }

        return inserted;
    }
}
