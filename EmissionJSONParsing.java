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

    // Again I inject my DAO so I can save Emission objects
    @Inject
    EmissionRecordDao emissionRecordDao;

    /**
     * This method reads the GreenhouseGasEmissions2025.json file,
     * parses it using json-simple (like the JSONRequestAndParse example),
     * assumes scenario=WEM and year=2023,
     * and saves all records with value>0 to the database.
     */
    @Transactional
    public int importFromJson() throws Exception {

        // Point to the JSON file inside my project
        File jsonFile = new File("src/main/resources/GreenhouseGasEmissions2025.json");

        String jsonString = "";

        // Use Scanner to read the whole file into one String
        try (Scanner scanner = new Scanner(jsonFile)) {
            while (scanner.hasNextLine()) {
                jsonString += scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("JSON file not found", e);
        }

        // Parse the String the same way as in JSONRequestAndParse
        JSONParser parser = new JSONParser();
        JSONObject rootObject = (JSONObject) parser.parse(jsonString);

        // In my file, the array is under the key "Emissions"
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

            // Value might already be a Number, or it might be stored as a String
            if (valueObj instanceof Number) {
                value = ((Number) valueObj).doubleValue();
            } else {
                value = Double.parseDouble(valueObj.toString());
            }

            // Rule: value must be > 0
            if (value <= 0) {
                continue;
            }

            // For this JSON file, we assume the same scenario and year
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

            // Save to the database using my DAO
            emissionRecordDao.persist(emission);
            inserted++;
        }

        return inserted;
    }
}
