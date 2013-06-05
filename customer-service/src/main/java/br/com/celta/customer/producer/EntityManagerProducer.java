package br.com.celta.customer.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

/**
 * EntityManagerProducer.class
 *
 * @author Ranlive Hrysyk
 */
@ApplicationScoped
@Alternative
public class EntityManagerProducer {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    @Produces
    public EntityManager create() {
        createEntityManager();
        return entityManager;
    }

    private void createEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("default-ds");
        }
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
            entityManager.setFlushMode(FlushModeType.AUTO);
        }
    }

    public EntityManager getEntityManager() {
        createEntityManager();
        return entityManager;
    }
}