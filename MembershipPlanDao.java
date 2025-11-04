package com.FitnessCentre.FitnessCentreRest.dao;

import com.FitnessCentre.FitnessCentreRest.model.MembershipPlan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MembershipPlanDao {

    protected static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("FitnessCentreRest");

    
    //Create
    public void persist(MembershipPlan plan) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(plan);
        em.getTransaction().commit();
        em.close();
    }

    //Update
    public MembershipPlan merge(MembershipPlan plan) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MembershipPlan updated = em.merge(plan);
        em.getTransaction().commit();
        em.close();
        return updated;
    }

    
    //Read
    public MembershipPlan getById(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MembershipPlan plan = em.find(MembershipPlan.class, id);
        em.getTransaction().commit();
        em.close();
        return plan;
    }

    
    //Delete
    public void removeMembershipPlan(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MembershipPlan plan = em.find(MembershipPlan.class, id);
        if (plan != null) {
            em.remove(plan);
        }
        em.getTransaction().commit();
        em.close();
    }


    //Read
    public List<MembershipPlan> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<MembershipPlan> list =
            em.createQuery("from MembershipPlan", MembershipPlan.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return list;
    }
}
