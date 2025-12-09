package com.greenhouse.ca2.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "emissions")
public class Emission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String categoryName;          
    private String categoryDescription;   

    private String scenario;              
    private double value;                 
    private int year;                     

    private boolean approved = false;

    @ManyToOne
    @JoinColumn(name = "approvedBy_id")
    private Users approvedBy;

    //constructors
    
    public Emission() {
    }

    public Emission(String categoryName, String categoryDescription, String scenario, double value, int year) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.scenario = scenario;
        this.value = value;
        this.year = year;
        this.approved = false; 
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Users getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Users approvedBy) {
        this.approvedBy = approvedBy;
    }
}
