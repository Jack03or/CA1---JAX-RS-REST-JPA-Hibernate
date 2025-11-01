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
					
			public void persist(Payment payment) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.persist(payment);
				em.getTransaction().commit();
				em.close();
			}
			
			public void removePayment(Payment payment) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.remove(em.merge(payment));
				em.getTransaction().commit();
				em.close();
			}
			
			public Payment merge(Payment payment) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				Payment updatedPayment = em.merge(payment);
				em.getTransaction().commit();
				em.close();
				return updatedPayment;
			}
			
			
			public List<Payment> getAllPayments() {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				List<Payment> payment = new ArrayList<Payment>();
				payment = em.createQuery("from Payment", Payment.class).getResultList();
				em.getTransaction().commit();
				em.close();
				return payment;
			}
	
	

}
