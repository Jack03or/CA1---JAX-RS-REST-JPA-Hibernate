package com.greenhouse.ca2.parsing;

import com.greenhouse.ca2.dao.EmissionRecordDao;
import com.greenhouse.ca2.entities.Emission;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@ApplicationScoped
public class EmissionXMLParsing {

    // I inject my DAO so I can save Emission objects to the database
    @Inject
    EmissionRecordDao emissionRecordDao;

    /**
     * This method reads the GasEmissionsProjections.xml file,
     * parses it using DOM (like the ReadUsers example),
     * applies the WEM / 2023 / value>0 rules,
     * and saves valid rows to the database.
     */
    @Transactional
    public int importFromXml() throws Exception {

        // Point to the XML file inside my project
        File xmlFile = new File("src/main/resources/GasEmissionsProjections.xml");

        // Standard DOM setup (same as ReadUsers sample)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        // Just to be safe
        doc.getDocumentElement().normalize();

        // Every <Row> in the XML is one possible emission record
        NodeList rows = doc.getElementsByTagName("Row");

        int inserted = 0; // how many records I actually insert

        // Go through each <Row> element
        for (int i = 0; i < rows.getLength(); i++) {

            Node rowNode = rows.item(i);

            if (rowNode.getNodeType() == Node.ELEMENT_NODE) {

                Element row = (Element) rowNode;

                // ----------------------------
                // Get the category name
                // It is inside a tag like <Category__1_3>
                // ----------------------------
                String categoryName = "";

                NodeList children = row.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);

                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String tagName = child.getNodeName();

                        // All category tags start with "Category__"
                        if (tagName.startsWith("Category__")) {
                            categoryName = child.getTextContent().trim();
                        }
                    }
                }

                // If I still don't have a category, skip this row
                if (categoryName.equals("")) {
                    continue;
                }

                // ----------------------------
                // Get Year, Scenario and Value from their tags
                // ----------------------------
                String yearText = row.getElementsByTagName("Year")
                        .item(0).getTextContent().trim();

                String scenario = row.getElementsByTagName("Scenario")
                        .item(0).getTextContent().trim();

                String valueText = row.getElementsByTagName("Value")
                        .item(0).getTextContent().trim();

                int year;
                double value;

                try {
                    year = Integer.parseInt(yearText);
                    value = Double.parseDouble(valueText);
                } catch (NumberFormatException e) {
                    // If I can't parse the numbers, I just ignore this row
                    continue;
                }

                // ----------------------------
                // Apply the rules from the assignment
                // ----------------------------
                if (!scenario.equalsIgnoreCase("WEM")) {
                    continue;
                }

                if (year != 2023) {
                    continue;
                }

                if (value <= 0) {
                    continue;
                }

                // ----------------------------
                // If I get here, the row is valid
                // So I create an Emission entity and save it
                // ----------------------------
                Emission emission = new Emission();
                emission.setCategoryName(categoryName);
                emission.setCategoryDescription(""); // leave empty for now
                emission.setScenario(scenario);
                emission.setYear(year);
                emission.setValue(value);
                emission.setApproved(false);

                // Use my DAO to save to the database
                emissionRecordDao.persist(emission);
                inserted++;
            }
        }

        return inserted;
    }
}
