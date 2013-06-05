package br.com.celta.customer.entity;

import java.util.ResourceBundle;

/**
 * NivelAcessoEnum.class 
 *
 * @author Ranlive Hrysyk
 */
public enum NivelAcessoEnum {

    ADMINISTRADOR("nivel.administrador"),
    SUPERVISOR("nivel.supervisor"),
    ATENDENTE("nivel.atendente");
    private String descricao;

    private NivelAcessoEnum(String key) {
        this.descricao = ResourceBundle.getBundle("messages").getString(key);
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
}