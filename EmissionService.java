package com.greenhouse.ca2.rest;

import com.greenhouse.ca2.dao.EmissionRecordDao;
import com.greenhouse.ca2.dao.UsersDao;
import com.greenhouse.ca2.entities.Emission;
import com.greenhouse.ca2.entities.Users;
import com.greenhouse.ca2.parsing.EmissionJSONParsing;
import com.greenhouse.ca2.parsing.EmissionXMLParsing;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/emissions")
public class EmissionService {

    @Inject
    EmissionRecordDao emissionDao;

    @Inject
    UsersDao usersDao;

    @Inject
    EmissionXMLParsing xmlImportService;

    @Inject
    EmissionJSONParsing jsonImportService;

    // CREATE
    @POST
    @Path("/newEmission")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addEmission(Emission emission) {
        emissionDao.persist(emission);
        return "Emission for category " + emission.getCategoryName() + " added!";
    }

    // READ All
    @GET
    @Path("/allEmissions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Emission> getAllEmissions() {
        return emissionDao.getAllRecords();
    }

    // READ by id
    @GET
    @Path("/findEmissionById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Emission getById(@PathParam("id") int id) {
        return emissionDao.getRecordById(id);
    }

    // READ by cat
    @GET
    @Path("/findByCategory/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Emission> getByCategory(@PathParam("category") String categoryName) {
        return emissionDao.getByCategory(categoryName);
    }

    // UPDATE
    @PUT
    @Path("/updateEmission")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Emission updateEmission(Emission emission) {
        return emissionDao.merge(emission);
    }

    // DELETE
    @DELETE
    @Path("/deleteEmission/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteEmission(@PathParam("id") int id) {
        emissionDao.removeRecord(id);
        return "Emission record " + id + " deleted.";
    }

    // Import the xml records
    @POST
    @Path("/import/xml")
    @Produces(MediaType.TEXT_PLAIN)
    public String importFromXml() throws Exception {
        int count = xmlImportService.importFromXml();
        return count + " emission records imported from XML.";
    }

    // Import the json records
    @POST
    @Path("/import/json")
    @Produces(MediaType.TEXT_PLAIN)
    public String importFromJson() throws Exception {
        int count = jsonImportService.importFromJson();
        return count + " emission records imported from JSON.";
    }

    // Approve emmision method
    @PUT
    @Path("/approve/{emissionId}/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String approveEmission(@PathParam("emissionId") int emissionId,
                                  @PathParam("userId") int userId) {

        // get the emission by id
        Emission emission = emissionDao.getRecordById(emissionId);

        // get the user who is approving
        Users user = usersDao.getUserById(userId);

        // set approved to true by the user passed in
        emission.setApproved(true);
        emission.setApprovedBy(user);

        // update in the db
        emissionDao.merge(emission);

        return "Emission " + emissionId + " approved by user " + user.getUsername() + ".";
    }
}
