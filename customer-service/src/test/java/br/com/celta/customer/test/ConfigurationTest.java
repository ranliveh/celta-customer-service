package br.com.celta.customer.test;

import br.com.celta.customer.application.ApplicationConfig;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ConfigurationTest.class
 *
 * @author Ranlive Hrysyk
 * @since 21/06/2013
 */
@RunWith(DemoiselleRunner.class)
public class ConfigurationTest {

    @Inject
    private ApplicationConfig config;

    @Test
    public void notNullTitle() {
        Assert.assertNotNull(config);
        Assert.assertNotNull("Titulo não pode estar vazio.", config.getApplicationTitle());
    }

    @Test
    public void notNullEmpresa() {
        Assert.assertNotNull(config);
        Assert.assertNotNull("Empresa não pode estar vazio.", config.getApplicationEmpresaId());
    }

    @Test
    public void notNullWidth() {
        Assert.assertNotNull(config);
        Assert.assertNotNull("Application widht não pode estar vazio.", config.getApplicationWidth());
    }

    @Test
    public void notNullHeigth() {
        Assert.assertNotNull(config);
        Assert.assertNotNull("Application heigth não pode estar vazio.", config.getApplicationHeigth());
    }
}