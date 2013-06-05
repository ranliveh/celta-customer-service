package br.com.celta.customer.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Iteracao.class
 *
 * @author Ranlive Hrysyk
 */
@Entity
@Table(name = "\"Iteracao\"")
@NamedQueries({
    @NamedQuery(name = "Iteracao.findAll", query = "SELECT i FROM Iteracao i"),
    @NamedQuery(name = "Iteracao.findByIteracaoID", query = "SELECT i FROM Iteracao i WHERE i.iteracaoID = :iteracaoID"),
    @NamedQuery(name = "Iteracao.findByData", query = "SELECT i FROM Iteracao i WHERE i.data = :data"),
    @NamedQuery(name = "Iteracao.findByHora", query = "SELECT i FROM Iteracao i WHERE i.hora = :hora"),
    @NamedQuery(name = "Iteracao.findByIteracao", query = "SELECT i FROM Iteracao i WHERE i.iteracao = :iteracao")})
public class Iteracao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "\"IteracaoID\"", nullable = false)
    private Integer iteracaoID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"Data\"", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data = new Date();
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"Hora\"", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date hora = new Date();
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "\"Iteracao\"", nullable = false, length = 2000)
    private String iteracao;
    @JoinColumn(name = "\"ChamadoID\"", referencedColumnName = "\"ChamadoID\"", nullable = false)
    @ManyToOne(optional = false)
    private Chamado chamado;
    @JoinColumn(name = "\"AtendenteID\"", referencedColumnName = "\"AtendenteID\"", nullable = false)
    @ManyToOne(optional = false)
    private Atendente atendente;

    public Iteracao() {
    }

    public Iteracao(Integer iteracaoID) {
        this.iteracaoID = iteracaoID;
    }

    public Iteracao(Integer iteracaoID, Date data, Date hora, String iteracao) {
        this.iteracaoID = iteracaoID;
        this.data = data;
        this.hora = hora;
        this.iteracao = iteracao;
    }

    public Integer getIteracaoID() {
        return iteracaoID;
    }

    public void setIteracaoID(Integer iteracaoID) {
        this.iteracaoID = iteracaoID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getIteracao() {
        return iteracao;
    }

    public void setIteracao(String iteracao) {
        this.iteracao = iteracao;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iteracaoID != null ? iteracaoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iteracao)) {
            return false;
        }
        Iteracao other = (Iteracao) object;
        if ((this.iteracaoID == null && other.iteracaoID != null) || (this.iteracaoID != null && !this.iteracaoID.equals(other.iteracaoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.celta.customer.entity.Iteracao[ iteracaoID=" + iteracaoID + " ]";
    }
}