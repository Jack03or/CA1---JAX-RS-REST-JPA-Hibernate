package com.FitnessCentre.FitnessCentreRest.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NamedQueries({
    @NamedQuery(name = "Member.findAll", query = "SELECT o FROM Member o"),
    @NamedQuery(name = "Member.findByname", query = "SELECT o FROM Member o WHERE o.name = :name")
})
@Entity
@XmlRootElement(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String address;
    private String phoneNumber;
    private String membershipId;
    private String fitnessGoal;   

    // Establish Relationship, many users but they can only have one plan each
    @ManyToOne(cascade = CascadeType.ALL) 
    private MembershipPlan membershipPlan;


    //One user can have many payments
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")  
    private List<Payment> payments;

    //Constructors
    public Member() {
    }

    public Member(String name, String address, String phoneNumber, String membershipId, String fitnessGoal) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.membershipId = membershipId;
        this.fitnessGoal = fitnessGoal;
    }

    //Getters + Setters

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
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
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

    @XmlElement
    public MembershipPlan getMembershipPlan() {
        return membershipPlan;
    }

    public void setMembershipPlan(MembershipPlan membershipPlan) {
        this.membershipPlan = membershipPlan;
    }

    @XmlElement
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
