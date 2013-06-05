package br.com.celta.customer.test;

import br.com.celta.customer.entity.Chamado;
import br.com.celta.customer.persistence.ChamadoDAO;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import java.util.List;
import javax.inject.Inject;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ChamadoTest.class
 *
 * @author Ranlive Hrysyk
 */
@RunWith(DemoiselleRunner.class)
public class ChamadoTest {
    
    private static final long serialVersionUID = 1L;
    @Inject
    private ChamadoDAO chamadoDAO;
    
    @Test
    public void findSuccessFul() {
        Assert.assertNotNull("chamadoDAO não foi injetado.", chamadoDAO);
        
        List<Chamado> list = chamadoDAO.findAll();
        
        Assert.assertNotNull("list não pode estar nula.", list);
    }
}