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
 * Cliente.class
 *
 * @author Ranlive Hrysyk
 */
@Entity
@Table(name = "\"Cliente\"")
@NamedQueries({
    @NamedQuery(name = "Cliente.findByAtivo", query = "SELECT c FROM Cliente c WHERE c.ativo = :ativo"),
    @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE c.nome = :nome")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ClienteID\"", nullable = false)
    private Integer clienteID;
    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "\"Tipo\"", nullable = false)
    private TipoPessoaEnum tipo = TipoPessoaEnum.PESSOA_FISICA;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "\"Documento\"", nullable = false, length = 18)
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"Ativo\"", nullable = false)
    private boolean ativo = true;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "\"Nome\"", nullable = false, length = 100)
    private String nome;
    @Size(max = 50)
    @Column(name = "\"Logradouro\"", length = 50)
    private String logradouro;
    @Size(max = 10)
    @Column(name = "\"Numero\"", length = 10)
    private String numero;
    @Size(max = 30)
    @Column(name = "\"Complemento\"", length = 30)
    private String complemento;
    @Size(max = 20)
    @Column(name = "\"Bairro\"", length = 20)
    private String bairro;
    @Size(max = 9)
    @Column(name = "\"CEP\"", length = 9)
    private String cep;
    @Size(max = 30)
    @Column(name = "\"Municipio\"", length = 30)
    private String municipio;
    @Size(max = 2)
    @Column(name = "\"UF\"", length = 2)
    private String uf;
    @Size(max = 10)
    @Column(name = "\"Telefone\"", length = 10)
    private String telefone;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "E-mail inválido")
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "\"Email\"", length = 30)
    private String email;

    public Cliente() {
    }

    public Cliente(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public Cliente(Integer clienteID, TipoPessoaEnum tipo, String documento, boolean ativo, String nome) {
        this.clienteID = clienteID;
        this.tipo = tipo;
        this.documento = documento;
        this.ativo = ativo;
        this.nome = nome;
    }
    
    public void setClienteID(Integer clienteID){
        this.clienteID = clienteID;
    }

    public Integer getClienteID() {
        return clienteID;
    }

    public TipoPessoaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoaEnum tipo) {
        this.tipo = tipo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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
        hash += (clienteID != null ? clienteID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clienteID == null && other.clienteID != null) || (this.clienteID != null && !this.clienteID.equals(other.clienteID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}