package br.com.celta.customer.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Empresa.class
 *
 * @author Ranlive Hrysyk
 */
@Entity
@Table(name = "\"Empresa\"")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "\"EmpresaID\"", nullable = false)
    private Integer empresaID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "\"Nome\"", nullable = false, length = 100)
    private String nome;
    @Size(max = 10)
    @Column(name = "\"Telefone\"", length = 10)
    private String telefone;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "E-mail inválido")
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "\"Email\"", length = 30)
    private String email;

    public Empresa() {
    }

    public Empresa(Integer empresaID) {
        this.empresaID = empresaID;
    }

    public Empresa(Integer empresaID, String nome) {
        this.empresaID = empresaID;
        this.nome = nome;
    }

    public Integer getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(Integer empresaID) {
        this.empresaID = empresaID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresaID != null ? empresaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.empresaID == null && other.empresaID != null) || (this.empresaID != null && !this.empresaID.equals(other.empresaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.celta.customer.entity.Empresa[ empresaID=" + empresaID + " ]";
    }
}