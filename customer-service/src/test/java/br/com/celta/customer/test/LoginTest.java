package br.com.celta.customer.test;

import br.com.celta.customer.security.Credenciais;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * LoginTest.class
 *
 * @author Ranlive Hrysyk
 */
@RunWith(DemoiselleRunner.class)
public class LoginTest {

    private static final long serialVersionUID = 1L;
    @Inject
    private SecurityContext context;
    @Inject
    private Credenciais credentials;

    @Test
    public void loginSuccessful() {
        // Acessando com meu usuário
        credentials.setLogin("administrador");
        credentials.setSenha("masterkey");
        context.login();
        Assert.assertEquals("administrador", context.getUser().getId());
        context.logout();
    }
}