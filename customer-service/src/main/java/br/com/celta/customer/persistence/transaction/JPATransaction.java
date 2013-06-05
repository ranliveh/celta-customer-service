package br.com.celta.customer.persistence.transaction;

import br.com.celta.customer.producer.EntityManagerProducer;
import br.gov.frameworkdemoiselle.transaction.Transaction;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * JPATransaction.class
 *
 * @author Ranlive Hrysyk
 */
@Alternative
@RequestScoped
public class JPATransaction implements Transaction {

    private static final long serialVersionUID = 1L;
    @Inject
    private EntityManagerProducer producer;

    public EntityManager getDelegate() {
        return producer.getEntityManager();
    }

    @Override
    public void begin() {
        EntityTransaction transaction;
        EntityManager entityManager = getDelegate();
        transaction = entityManager.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }
    }

    @Override
    public void commit() {
        EntityTransaction transaction;
        EntityManager entityManager = getDelegate();
        transaction = entityManager.getTransaction();

        if (transaction.isActive()) {
            transaction.commit();
        }
    }

    @Override
    public void rollback() {
        EntityTransaction transaction;
        EntityManager entityManager = getDelegate();

        transaction = entityManager.getTransaction();

        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Override
    public void setRollbackOnly() {
        EntityTransaction transaction;
        EntityManager entityManager = getDelegate();

        transaction = entityManager.getTransaction();

        if (transaction.isActive()) {
            transaction.setRollbackOnly();
        }
    }

    @Override
    public boolean isActive() {
        boolean active = false;
        EntityManager entityManager = getDelegate();

        if (entityManager.getTransaction().isActive()) {
            active = true;
        }

        return active;
    }

    @Override
    public boolean isMarkedRollback() {
        boolean rollbackOnly = false;

        EntityTransaction transaction;
        EntityManager entityManager = getDelegate();

        transaction = entityManager.getTransaction();

        if (transaction.isActive() && transaction.getRollbackOnly()) {
            rollbackOnly = true;
        }

        return rollbackOnly;
    }
}