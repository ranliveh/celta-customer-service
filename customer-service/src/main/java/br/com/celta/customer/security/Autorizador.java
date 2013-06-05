package br.com.celta.customer.security;

import br.com.celta.customer.application.ApplicationAtendenteProvider;
import br.com.celta.customer.entity.Atendente;
import br.gov.frameworkdemoiselle.security.Authorizer;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

/**
 * Autorizador.class
 *
 * @author Ranlive Hrysyk
 */
@Alternative
public class Autorizador implements Authorizer {

    private static final long serialVersionUID = 1L;
    @Inject
    private ApplicationAtendenteProvider provider;

    @Override
    public boolean hasRole(String role) {
        Atendente atendente = provider.getCurrentAtendente();

        if (atendente == null) {
            return false;
        }

        return Roles.hasRole(atendente.getNivel(), role);
    }

    @Override
    public boolean hasPermission(String resource, String operation) {
        return true;
    }
}