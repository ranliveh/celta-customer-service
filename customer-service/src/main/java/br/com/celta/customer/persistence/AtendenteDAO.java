package br.com.celta.customer.persistence;

import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.utils.ExceptionUtils;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * AtendenteDAO.class
 *
 * @author Ranlive Hrysyk
 */
@PersistenceController
public class AtendenteDAO extends JPACrud<Atendente, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Lista os atendentes ativos.
     */
    public List<Atendente> findAtivos() {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Atendente> query = manager.createNamedQuery("Atendente.findByAtivo", Atendente.class);
            query.setParameter("ativo", true);
            return query.getResultList();
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        return null;
    }

    /**
     * Lista os atendentes por nome.
     */
    public List<Atendente> findByNome(String nome) {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Atendente> query = manager.createNamedQuery("Atendente.findByNome", Atendente.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        return null;
    }

    /**
     * Retorna um atendente pelo login.
     */
    public Atendente findByLogin(String login) {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Atendente> query = manager.createNamedQuery("Atendente.findByLogin", Atendente.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (Exception exception) {
            if (exception instanceof NoResultException) {
                return null;
            }

            ExceptionUtils.throwCustomerException(exception, true);
        }
        return null;
    }
}