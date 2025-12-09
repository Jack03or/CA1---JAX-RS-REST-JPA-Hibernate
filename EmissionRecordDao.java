package com.greenhouse.ca2.dao;

import com.greenhouse.ca2.entities.Emission;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EmissionRecordDao {

    @Inject
    EntityManager em;

    // CREATE
    @Transactional
    public void persist(Emission emission) {
        em.persist(emission);
    }

    // UPDATE
    @Transactional
    public Emission merge(Emission emission) {
        return em.merge(emission);
    }

    // DELETE
    @Transactional
    public void removeRecord(int id) {
        Emission e = em.find(Emission.class, id);
        if (e != null) {
            em.remove(e);
        }
    }

    // READ - Single
    public Emission getRecordById(int id) {
        return em.find(Emission.class, id);
    }

    // READ - All
    public List<Emission> getAllRecords() {
        return em.createQuery("FROM Emission", Emission.class)
                .getResultList();
    }

    // READ - By category name
    public List<Emission> getByCategory(String categoryName) {
        return em.createQuery(
                "SELECT e FROM Emission e WHERE TRIM(e.categoryName) = :cat",
                Emission.class)
                .setParameter("cat", categoryName.trim())
                .getResultList();
    }

}
