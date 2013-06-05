package br.com.celta.customer.persistence;

import br.com.celta.customer.entity.Cliente;
import br.com.celta.customer.utils.ExceptionUtils;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * ClienteDAO.class 
 *
 * @author Ranlive Hrysyk
 */
@PersistenceController
public class ClienteDAO extends JPACrud<Cliente, Integer>{

    private static final long serialVersionUID = 1L;
    
    /**
     * Lista os atendentes ativos.
     */
    public List<Cliente> findAtivos() {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Cliente> query = manager.createNamedQuery("Cliente.findByAtivo", Cliente.class);
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
    public List<Cliente> findByNome(String nome) {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Cliente> query = manager.createNamedQuery("Cliente.findByNome", Cliente.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        return null;
    }
}