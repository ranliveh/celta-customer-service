package br.com.celta.customer.application;

import br.com.celta.customer.entity.Chamado;

/**
 * ApplicationChamadoProvider.class
 *
 * @author Ranlive Hrysyk
 */
public class ApplicationChamadoProvider {

    private static final long serialVersionUID = 1L;
    private static ApplicationChamadoProvider instance;
    private Chamado editingChamado;

    private ApplicationChamadoProvider() {
    }

    public static ApplicationChamadoProvider getInstance() {
        if (instance == null) {
            instance = new ApplicationChamadoProvider();
        }

        return instance;
    }

    public Chamado getEditingChamado() {
        return editingChamado;
    }

    public void setEditingChamado(Chamado editingChamado) {
        this.editingChamado = editingChamado;
    }
}