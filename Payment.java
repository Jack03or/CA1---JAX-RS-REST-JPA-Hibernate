package com.FitnessCentre.FitnessCentreRest.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findByMember", query = "SELECT p FROM Payment p WHERE p.member.id = :memberId")
})


@Entity
@XmlRootElement(name = "payment")
public class Payment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	
	private String date;
    private double amount;

    @ManyToOne
    private Member member;
	
    public Payment() {}

    public Payment(String date, double amount, Member member) {
        this.date = date;
        this.amount = amount;
        this.member = member;
    }
	
    @XmlElement
    public int getId() {
		return id;
	}

    
	public void setId(int id) {
		this.id = id;
	}

	
	@XmlElement
	public String getDate() {
		return date;
	}

	
	public void setDate(String date) {
		this.date = date;
	}

	
	@XmlElement
	public double getAmount() {
		return amount;
	}

	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	
	@XmlElement
	public Member getMember() {
		return member;
	}

	
	
	public void setMember(Member member) {
		this.member = member;
	}


}
