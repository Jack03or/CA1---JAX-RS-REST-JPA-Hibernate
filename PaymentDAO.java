package com.FitnessCentre.FitnessCentreRest.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.FitnessCentre.FitnessCentreRest.model.Payment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public class PaymentDAO {

	protected static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("FitnessCentreRest");

	//Create
	public void persist(Payment payment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(payment);
		em.getTransaction().commit();
		em.close();
	}

	//Delete
	public void removePayment(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Payment p = em.find(Payment.class, id);
		if (p != null) {
			em.remove(p);
		}
		em.getTransaction().commit();
		em.close();
	}

	//Update
	public Payment merge(Payment payment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Payment updatedPayment = em.merge(payment);
		em.getTransaction().commit();
		em.close();
		return updatedPayment;
	}

	//Read
	public Payment getPaymentById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Payment p = em.find(Payment.class, id);
		em.getTransaction().commit();
		em.close();
		return p;
	}

	//Read
	public List<Payment> getAllPayments() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Payment> payments = new ArrayList<Payment>();
		payments = em.createQuery("from Payment", Payment.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return payments;
	}
}
