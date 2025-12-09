package com.greenhouse.ca2.entities;

import jakarta.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "select u from Users u"),
        @NamedQuery(name = "Users.findByUsername", query = "select u from Users u where u.username = :username")
})
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    //Constructors
    public Users() {
    	
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
