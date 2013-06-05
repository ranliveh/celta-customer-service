package br.com.celta.customer.security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 * Credenciais.class
 *
 * @author Ranlive Hrysyk
 */
@SessionScoped
public class Credenciais implements Serializable {

    private String login;
    private String senha;

    public Credenciais() {
    }

    public void clear() {
        this.login = null;
        this.senha = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}