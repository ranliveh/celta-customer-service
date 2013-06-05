package br.com.celta.customer.entity;

import java.util.ResourceBundle;

/**
 * StatusEnum.class
 *
 * @author Ranlive Hrysyk
 */
public enum StatusEnum {

    AGUARDANDO("status.aguardando"),
    ATENDIMENTO("status.atendimento"),
    FINALIZADO("status.finalizado");
    private String descricao;

    private StatusEnum(String key) {
        this.descricao = ResourceBundle.getBundle("messages").getString(key);
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}