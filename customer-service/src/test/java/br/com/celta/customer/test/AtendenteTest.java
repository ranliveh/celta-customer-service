package br.com.celta.customer.test;

import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.persistence.AtendenteDAO;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import java.util.List;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * AtendenteTest.class
 *
 * @author Ranlive Hrysyk
 */
@RunWith(DemoiselleRunner.class)
public class AtendenteTest {

    private static final long serialVersionUID = 1L;
    @Inject
    private AtendenteDAO atendenteDAO;

    @Test
    public void notEmptyList() {
        Assert.assertNotNull("atendenteDAO não foi injetado.", atendenteDAO);

        List<Atendente> list = atendenteDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertTrue("Nenhum atendente encontrado.", !list.isEmpty());
    }
}