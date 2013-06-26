package br.com.celta.customer.security;

import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.persistence.AtendenteDAO;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

/**
 * Autenticador.class
 *
 * @author Ranlive Hrysyk
 */
@Alternative
public class Autenticador implements Authenticator {

    private static final long serialVersionUID = 1L;
    @Inject
    private Credenciais credenciais;
    @Inject
    private AtendenteDAO atendenteDAO;
    private boolean isLoggedIn;

    @Override
    public boolean authenticate() {
        String login = credenciais.getLogin();
        String senha = credenciais.getSenha();
        this.isLoggedIn = true;

        Atendente atendente = atendenteDAO.findByLogin(login);

        if (atendente == null || !senha.equals(atendente.getSenha())) {
            this.isLoggedIn = false;
        }

        return isLoggedIn;
    }

    @Override
    public void unAuthenticate() {
        this.credenciais.clear();
        this.isLoggedIn = false;
    }

    @Override
    public User getUser() {
//        if (this.isLoggedIn) {
            return new User() {
                @Override
                public String getId() {
                    return credenciais.getLogin();
                }

                @Override
                public Object getAttribute(Object key) {
                    return null;
                }

                @Override
                public void setAttribute(Object key, Object value) {
                }
            };
//        }

//        return null;
    }
}