package br.com.celta.customer.persistence;

import br.com.celta.customer.entity.Classificacao;
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
public class ClassificacaoDAO extends JPACrud<Classificacao, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Lista classificação pela descrição.
     */
    public List<Classificacao> findByDescricao(String descricao) {
        try {
            EntityManager manager = getEntityManager();
            TypedQuery<Classificacao> query = manager.createNamedQuery("Classificacao.findByDescricao", Classificacao.class);
            query.setParameter("descricao", "%" + descricao + "%");
            return query.getResultList();
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        return null;
    }
}