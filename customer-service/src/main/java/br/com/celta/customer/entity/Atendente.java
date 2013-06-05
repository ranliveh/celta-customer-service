package br.com.celta.customer.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Atendente.class
 *
 * @author Ranlive Hrysyk
 */
@Entity
@Table(name = "\"Atendente\"")
@NamedQueries({
    @NamedQuery(name = "Atendente.findByAtivo", query = "SELECT a FROM Atendente a WHERE a.ativo = :ativo"),
    @NamedQuery(name = "Atendente.findByNome", query = "SELECT a FROM Atendente a WHERE a.nome LIKE :nome"),
    @NamedQuery(name = "Atendente.findByLogin", query = "SELECT a FROM Atendente a WHERE a.login = :login")})
public class Atendente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "\"AtendenteID\"", nullable = false)
    private Integer atendenteID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"Ativo\"", nullable = false)
    private boolean ativo = true;
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
    @Size(min = 5, max = 20)
    @NotNull
    @Column(name = "\"Login\"", length = 20, unique = true)
    private String login;
    @Size(max = 256)
    @NotNull
    @Column(name = "\"Senha\"", length = 256)
    private String senha;
    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "\"Nivel\"", nullable = false)
    private NivelAcessoEnum nivel = NivelAcessoEnum.ATENDENTE;

    public Atendente() {
    }

    public Atendente(Integer atendenteID) {
        this.atendenteID = atendenteID;
    }

    public Atendente(Integer atendenteID, boolean ativo, String nome) {
        this.atendenteID = atendenteID;
        this.ativo = ativo;
        this.nome = nome;
    }

    public Integer getAtendenteID() {
        return atendenteID;
    }

    public void setAtendenteID(Integer atendenteID) {
        this.atendenteID = atendenteID;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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

    public NivelAcessoEnum getNivel() {
        return nivel;
    }

    public void setNivel(NivelAcessoEnum nivel) {
        this.nivel = nivel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atendenteID != null ? atendenteID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atendente)) {
            return false;
        }
        Atendente other = (Atendente) object;
        if ((this.atendenteID == null && other.atendenteID != null) || (this.atendenteID != null && !this.atendenteID.equals(other.atendenteID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}