package br.com.celta.customer.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classificacao.class 
 *
 * @author Ranlive Hrysyk
 */
@Entity
@Table(name = "\"Classificacao\"")
@NamedQueries({
    @NamedQuery(name = "Classificacao.findByDescricao", query = "SELECT c FROM Classificacao c WHERE c.descricao LIKE :descricao")})
public class Classificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "\"ClassificacaoID\"", nullable = false)
    private Integer classificacaoID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "\"Descricao\"", nullable = false, length = 30)
    private String descricao;

    public Classificacao() {
    }

    public Classificacao(Integer classificacaoID) {
        this.classificacaoID = classificacaoID;
    }

    public Classificacao(Integer classificacaoID, String descricao) {
        this.classificacaoID = classificacaoID;
        this.descricao = descricao;
    }

    public Integer getClassificacaoID() {
        return classificacaoID;
    }

    public void setClassificacaoID(Integer classificacaoID) {
        this.classificacaoID = classificacaoID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classificacaoID != null ? classificacaoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classificacao)) {
            return false;
        }
        Classificacao other = (Classificacao) object;
        if ((this.classificacaoID == null && other.classificacaoID != null) || (this.classificacaoID != null && !this.classificacaoID.equals(other.classificacaoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }
}