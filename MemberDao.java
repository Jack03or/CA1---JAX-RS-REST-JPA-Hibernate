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
	
	public void persist(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(member);
		em.getTransaction().commit();
		em.close();
	}
	
	public void removeMember(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(member));
		em.getTransaction().commit();
		em.close();
	}
	
	public Member merge(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Member updatedMember = em.merge(member);
		em.getTransaction().commit();
		em.close();
		return updatedMember;
	}
	
	
	public List<Member> getAllMembers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Member> member = new ArrayList<Member>();
		member = em.createQuery("from member", Member.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return member;
	}
	
}
