package com.greenhouse.ca2.dao;

import com.greenhouse.ca2.entities.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class UsersDao {

    @Inject
    EntityManager em;

    // CREATE
    @Transactional
    public void persist(Users user) {
        em.persist(user);
    }

    // UPDATE
    @Transactional
    public Users merge(Users user) {
        return em.merge(user);
    }

    // DELETE
    @Transactional
    public void removeUser(int id) {
        Users u = em.find(Users.class, id);
        if (u != null) {
            em.remove(u);
        }
    }

    // READ - Single
    public Users getUserById(int id) {
        return em.find(Users.class, id);
    }

    // READ - All
    public List<Users> getAllUsers() {
        return em.createNamedQuery("Users.findAll", Users.class)
                .getResultList();
    }

    // READ - Find by username
    public Users getUserByUsername(String username) {
        List<Users> result = em.createNamedQuery("Users.findByUsername", Users.class)
                .setParameter("username", username)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}
