package br.com.celta.customer.application;

import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.persistence.AtendenteDAO;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * ApplicationAtendenteProvider.class
 *
 * @author Ranlive Hrysyk
 */
@Singleton
public class ApplicationAtendenteProvider {

    private static final long serialVersionUID = 1L;
    @Inject
    private SecurityContext securityContext;
    @Inject
    private AtendenteDAO atendenteDAO;

    public Atendente getCurrentAtendente() {
        return atendenteDAO.findByLogin(securityContext.getUser().getId());
    }
}