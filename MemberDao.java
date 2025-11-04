package com.FitnessCentre.FitnessCentreRest.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.FitnessCentre.FitnessCentreRest.model.Member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public class MemberDao {

	protected static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("FitnessCentreRest");

	//Create
	public void persist(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(member);
		em.getTransaction().commit();
		em.close();
	}

	
	//Remove
	public void removeMember(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Member m = em.find(Member.class, id);
		if (m != null) {
			em.remove(m);
		}
		em.getTransaction().commit();
		em.close();
	}

	//Update
	public Member merge(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Member updatedMember = em.merge(member);
		em.getTransaction().commit();
		em.close();
		return updatedMember;
	}

	
	//Read
	public Member getMemberById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Member m = em.find(Member.class, id);
		em.getTransaction().commit();
		em.close();
		return m;
	}

	
	//Read
	public List<Member> getAllMembers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Member> member = new ArrayList<Member>();
		member = em.createQuery("from Member", Member.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return member;
	}

}
