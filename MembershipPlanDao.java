package com.FitnessCentre.FitnessCentreRest.dao;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.FitnessCentre.FitnessCentreRest.model.MembershipPlan;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public class MembershipPlanDao {

	
	protected static EntityManagerFactory emf = 
    Persistence.createEntityManagerFactory("FitnessCentreRest");
			
			public void persist(MembershipPlan plan) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.persist(plan);
				em.getTransaction().commit();
				em.close();
			}
			
			public void removeMembershipPlan(MembershipPlan plan) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.remove(em.merge(plan));
				em.getTransaction().commit();
				em.close();
			}
			
			public MembershipPlan merge(MembershipPlan plan) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				MembershipPlan updatedPlan = em.merge(plan);
				em.getTransaction().commit();
				em.close();
				return updatedPlan;
			}
			
			
			public List<MembershipPlan> getAllMembershipPlans() {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				List<MembershipPlan> plan = new ArrayList<MembershipPlan>();
				plan = em.createQuery("from MembershipPlan", MembershipPlan.class).getResultList();
				em.getTransaction().commit();
				em.close();
				return plan;
			}
	
	
}
