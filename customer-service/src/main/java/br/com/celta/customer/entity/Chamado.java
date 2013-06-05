package br.com.celta.customer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Chamado.class
 *
 * @author Ranlive Hrysyk
 */
@Entity
@Table(name = "\"Chamado\"")
@NamedQueries({
    @NamedQuery(name = "Chamado.findAll", query = "SELECT c FROM Chamado c ORDER BY c.prazo, c.dataAbertura DESC, c.horaAbertura DESC, c.chamadoID")})
public class Chamado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "\"ChamadoID\"", nullable = false)
    private Integer chamadoID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "\"Titulo\"", nullable = false, length = 20)
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"DataAbertura\"", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataAbertura = new Date();
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"HoraAbertura\"", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date horaAbertura = new Date();
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"Prazo\"", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date prazo = new Date();
    @NotNull
    @JoinColumn(name = "\"ClienteID\"", referencedColumnName = "\"ClienteID\"", nullable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @NotNull
    @JoinColumn(name = "\"ClassificacaoID\"", referencedColumnName = "\"ClassificacaoID\"", nullable = false)
    @ManyToOne(optional = false)
    private Classificacao classificacao;
    @NotNull
    @JoinColumn(name = "\"CategoriaID\"", referencedColumnName = "\"CategoriaID\"", nullable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @NotNull
    @JoinColumn(name = "\"AtendenteID\"", referencedColumnName = "\"AtendenteID\"", nullable = false)
    @ManyToOne(optional = false)
    private Atendente atendente;
    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "\"Status\"", nullable = false)
    private StatusEnum status = StatusEnum.AGUARDANDO;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chamado")
    private List<Iteracao> iteracaoList = new ArrayList<>();

    public Chamado() {
    }

    public Chamado(Integer chamadoID) {
        this.chamadoID = chamadoID;
    }

    public Chamado(Integer chamadoID, String titulo, Date dataAbertura, Date horaAbertura, Date prazo) {
        this.chamadoID = chamadoID;
        this.titulo = titulo;
        this.dataAbertura = dataAbertura;
        this.horaAbertura = horaAbertura;
        this.prazo = prazo;
    }

    public Integer getChamadoID() {
        return chamadoID;
    }

    public void setChamadoID(Integer chamadoID) {
        this.chamadoID = chamadoID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(Date horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public List<Iteracao> getIteracaoList() {
        return iteracaoList;
    }
    
    public void setIteracaoList(List<Iteracao> iteracaoList){
        this.iteracaoList = iteracaoList;
    }

    public void addIteracao(Iteracao iteracao) {
        this.iteracaoList.add(iteracao);
    }

    public void removeIteracao(Iteracao iteracao) {
        this.iteracaoList.remove(iteracao);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chamadoID != null ? chamadoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chamado)) {
            return false;
        }
        Chamado other = (Chamado) object;
        if ((this.chamadoID == null && other.chamadoID != null) || (this.chamadoID != null && !this.chamadoID.equals(other.chamadoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.celta.customer.entity.Chamado[ chamadoID=" + chamadoID + " ]";
    }
}