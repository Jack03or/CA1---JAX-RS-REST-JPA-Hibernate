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

    @Inject
    EmissionRecordDao emissionRecordDao;

    @Transactional
    public int importFromXml() throws Exception {

        // Point to the XML file
        File xmlFile = new File("src/main/resources/GasEmissionsProjections.xml");

        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        
        NodeList rows = doc.getElementsByTagName("Row");

        int inserted = 0;

        for (int i = 0; i < rows.getLength(); i++) {

            Node rowNode = rows.item(i);

            if (rowNode.getNodeType() == Node.ELEMENT_NODE) {

                Element row = (Element) rowNode;

                // Find the category name (tag name starts with "Category__")
                String categoryName = "";
                NodeList children = row.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);

                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String tagName = child.getNodeName();

                        if (tagName.startsWith("Category__")) {
                            categoryName = child.getTextContent().trim();
                        }
                    }
                }

                if (categoryName.equals("")) {
                    continue;
                }

                // Read Year, Scenario and Value
                String yearText = row.getElementsByTagName("Year")
                        .item(0).getTextContent().trim();

                String scenario = row.getElementsByTagName("Scenario")
                        .item(0).getTextContent().trim();

                String valueText = row.getElementsByTagName("Value")
                        .item(0).getTextContent().trim();

                int year = Integer.parseInt(yearText);
                double value = Double.parseDouble(valueText);

                // Apply assignment rules
                if (!scenario.equalsIgnoreCase("WEM")) {
                    continue;
                }

                if (year != 2023) {
                    continue;
                }

                if (value <= 0) {
                    continue;
                }

                // Create entity and save
                Emission emission = new Emission();
                emission.setCategoryName(categoryName);
                emission.setCategoryDescription(""); 
                emission.setScenario(scenario);
                emission.setYear(year);
                emission.setValue(value);
                emission.setApproved(false);

                emissionRecordDao.persist(emission);
                inserted++;
            }
        }

        return inserted;
    }
}
