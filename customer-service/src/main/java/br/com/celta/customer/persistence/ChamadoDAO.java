package br.com.celta.customer.persistence;

import br.com.celta.customer.entity.Chamado;
import br.com.celta.customer.utils.ExceptionUtils;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * ChamadoDAO.class
 *
 * @author Ranlive Hrysyk
 */
@PersistenceController
public class ChamadoDAO extends JPACrud<Chamado, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Retorna uma lista de chamados.
     */
    @Override
    public List<Chamado> findAll() {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Chamado> query = manager.createNamedQuery("Chamado.findAll", Chamado.class);
            return query.getResultList();
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        return null;
    }
}