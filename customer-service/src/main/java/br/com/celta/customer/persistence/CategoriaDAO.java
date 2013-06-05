package br.com.celta.customer.persistence;

import br.com.celta.customer.entity.Categoria;
import br.com.celta.customer.utils.ExceptionUtils;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * CategoriaDAO.class
 *
 * @author Ranlive Hrysyk
 */
@PersistenceController
public class CategoriaDAO extends JPACrud<Categoria, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Lista categorias pela descrição.
     */
    public List<Categoria> findByDescricao(String descricao) {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Categoria> query = manager.createNamedQuery("Categoria.findByDescricao", Categoria.class);
            query.setParameter("descricao", "%" + descricao + "%");
            return query.getResultList();
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        return null;
    }
}