package exam.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import exam.entities.Client;
import exam.repositories.ClientRepository;
import exam.repositories.RepositoryImpl;

public class ClientRepositoryImpl extends RepositoryImpl<Client> implements ClientRepository {
    public ClientRepositoryImpl() {
        super(Client.class);
    }

    @Override
    public Client selectByTel(String tel) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            return em.createQuery(
                    "SELECT c FROM Client c WHERE c.telephone = :tel",
                    Client.class)
                    .setParameter("tel", tel)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Client selectBySurname(String surname) {
        try {
            return entityManager.createQuery(
                    "SELECT c FROM Client c WHERE c.surname = :surname",
                    Client.class)
                    .setParameter("surname", surname)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
