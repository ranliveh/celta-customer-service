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
 * Categoria.class
 *
 * @author Ranlive Hrysyk
 */
@Entity
@Table(name = "\"Categoria\"")
@NamedQueries({
    @NamedQuery(name = "Categoria.findByDescricao", query = "SELECT c FROM Categoria c WHERE c.descricao LIKE :descricao")})
public class Categoria implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"CategoriaID\"")
    private Integer categoriaID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "\"Descricao\"", nullable = false, length = 30)
    private String descricao;

    public Categoria() {
    }

    public Categoria(Integer categoriaID) {
        this.categoriaID = categoriaID;
    }

    public Categoria(Integer categoriaID, String descricao) {
        this.categoriaID = categoriaID;
        this.descricao = descricao;
    }

    public Integer getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(Integer categoriaID) {
        this.categoriaID = categoriaID;
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
        hash += (categoriaID != null ? categoriaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.categoriaID == null && other.categoriaID != null) || (this.categoriaID != null && !this.categoriaID.equals(other.categoriaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }
}