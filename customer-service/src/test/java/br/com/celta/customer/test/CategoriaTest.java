package br.com.celta.customer.test;

import br.com.celta.customer.entity.Categoria;
import br.com.celta.customer.persistence.CategoriaDAO;
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
public class CategoriaTest {

    private static final long serialVersionUID = 1L;
    @Inject
    private CategoriaDAO categoriaDAO;

    @Test
    public void notEmptyList() {
        Assert.assertNotNull("categoriaDAO não foi injetado.", categoriaDAO);

        List<Categoria> list = categoriaDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertTrue("Nenhuma categoria encontrada.", !list.isEmpty());
    }
}