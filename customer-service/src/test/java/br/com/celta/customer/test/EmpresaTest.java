package br.com.celta.customer.test;


import br.com.celta.customer.application.ApplicationConfig;
import br.com.celta.customer.entity.Empresa;
import br.com.celta.customer.persistence.EmpresaDAO;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * EmpresaTest.class 
 *
 * @author Ranlive Hrysyk
 */
@RunWith(DemoiselleRunner.class)
public class EmpresaTest {

    private static final long serialVersionUID = 1L;
    @Inject
    private EmpresaDAO empresaDAO;
    @Inject
    private ApplicationConfig config;
    
    @Test
    public void loadSuccessful(){
        Assert.assertNotNull("empresaBC não injetado", empresaDAO);
        Assert.assertNotNull("config não injetado", config);
        Assert.assertNotNull("ID da empresa não pode estar nulo", config.getApplicationEmpresaId());
        
        Empresa empresa = empresaDAO.load(config.getApplicationEmpresaId());
        
        Assert.assertNotNull("empresa não carregado", empresa);
    }
}