package com.greenhouse.ca2.rest;

import com.greenhouse.ca2.dao.UsersDao;
import com.greenhouse.ca2.entities.Users;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserService {

    @Inject
    UsersDao usersDao;

    @POST
    @Path("/registerUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String registerUser(Users user) {

        // check if username already exists
        Users existingUser = usersDao.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            return "Username already taken.";
        }

        usersDao.persist(user);
        return "User " + user.getUsername() + " registered successfully!";
    }

    // READ ALL
    @GET
    @Path("/allUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Users> getAllUsers() {
        return usersDao.getAllUsers();
    }

    // READ BY ID
    @GET
    @Path("/findUserById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Users getUserById(@PathParam("id") int id) {
        return usersDao.getUserById(id);
    }

   
    // UPDATE
    @PUT
    @Path("/updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Users updateUser(Users user) {
        return usersDao.merge(user);
    }

    // DELETE
    @DELETE
    @Path("/deleteUser/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@PathParam("id") int id) {
        usersDao.removeUser(id);
        return "User with ID " + id + " deleted.";
    }
    
    //User Login
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(Users loginUser) {

        // Look up the username
        Users user = usersDao.getUserByUsername(loginUser.getUsername());

        // check if match
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            return "Invalid username or password.";
        }

        // If login successful
        return "Login successful! Welcome " + user.getUsername();
    }

}
