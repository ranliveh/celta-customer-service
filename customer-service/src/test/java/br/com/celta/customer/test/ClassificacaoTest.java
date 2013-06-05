package br.com.celta.customer.test;

import br.com.celta.customer.entity.Classificacao;
import br.com.celta.customer.persistence.ClassificacaoDAO;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import java.util.List;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * CategoriaTest.class
 *
 * @author Ranlive Hrysyk
 */
@RunWith(DemoiselleRunner.class)
public class ClassificacaoTest {

    private static final long serialVersionUID = 1L;
    @Inject
    private ClassificacaoDAO classificacaoDAO;

    @Test
    public void notEmptyList() {
        Assert.assertNotNull("classificacaoDAO não foi injetado.", classificacaoDAO);

        List<Classificacao> list = classificacaoDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertTrue("Nenhuma classificação encontrada.", !list.isEmpty());
    }
}