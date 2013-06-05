package br.com.celta.customer.entity;

import java.util.ResourceBundle;

/**
 * TipoPessoaEnum.class
 *
 * @author Ranlive Hrysyk
 */
public enum TipoPessoaEnum {

    PESSOA_FISICA("pessoa.fisica"),
    PESSOA_JURIDICA("pessoa.juridica");
    private String descricao;

    private TipoPessoaEnum(String key) {
        this.descricao = ResourceBundle.getBundle("messages").getString(key);
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}