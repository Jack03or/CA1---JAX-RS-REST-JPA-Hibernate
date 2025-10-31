package com.FitnessCentre.FitnessCentreRest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@NamedQueries({
	@NamedQuery(name="Member.findAll", query="select o from Member o"), 
	@NamedQuery(name = "Member.findByname", query = "select o from Subscriber o where o.username=:username")
})


@Entity
@XmlRootElement(name = "member")
public class Members {

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	
	private String name;
	private String address;
	private String phoneNumber;
	private String membershipId;
	private String FitnessGoal;
	
	
	//private membershipPlan membershipPlan
    //create memberhsip plan class and relationship OneToOne
		
	//Create a payment class a payment list
	//One member to many payments relationship here
	
	
	public Members() {
		
		
	}

	public Members(String name, String address, String phoneNumber, String membershipId, String fitnessGoal) {
	    this.name = name;
	    this.address = address;
	    this.phoneNumber = phoneNumber;
	    this.membershipId = membershipId;
	    this.FitnessGoal = fitnessGoal;
	}
		
	@XmlElement
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	@XmlElement
	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@XmlElement
	public String getFitnessGoal() {
		return FitnessGoal;
	}

	public void setFitnessGoal(String fitnessGoal) {
		FitnessGoal = fitnessGoal;
	}

	@XmlElement
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@XmlElement
	public String getMembershipId() {
		return membershipId;
	}
	
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
			
}
