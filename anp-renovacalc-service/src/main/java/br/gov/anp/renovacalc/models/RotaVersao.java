package br.gov.anp.renovacalc.models;


import org.hibernate.annotations.*;
import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Classe de modelo que representa uma versão de uma rota.
 * É com este modelo, e não diretamente a Rota, que as sessões e atributos
 * estão relacionados.
 */
@Entity
@Table(name = "TRNB_ROTA_VERSAO")
public class RotaVersao {

    private long id;

    private int numVersao;

    private Rota rota;

    private RotaVersaoSituacao situacao;

    /**
     * Conjunto com as sessões-raiz da RotaVersão,
     * isto é, sessões que não são filhas de outra sessão
     */
    private Set<RotaSessao> sessoes = new HashSet<RotaSessao>();


    public RotaVersao() {
    }

    public RotaVersao(Rota rota, RotaVersaoSituacao situacao, int numVersao) {
        this.numVersao = numVersao;
        this.rota = rota;
        this.situacao = situacao;
    }

    public RotaVersao(long id, Rota rota, RotaVersaoSituacao situacao, int numVersao) {
        this.id = id;
        this.numVersao = numVersao;
        this.rota = rota;
        this.situacao = situacao;
    }

    @Id
    @Column(name = "SEQ_ROTA_VERSAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceVersao")
    @SequenceGenerator(name = "sequenceVersao", sequenceName = "SRNB_ROTA_VERSAO", allocationSize = 1)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "NUM_VERSAO")
    public int getNumVersao() { return numVersao; }
    public void setNumVersao(int numVersao) { this.numVersao = numVersao; }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Rota getRota() { return rota; }
    public void setRota(Rota rota) { this.rota = rota; }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "COD_ROTA_VERSAO_SITUACAO", nullable = false)
    public RotaVersaoSituacao getSituacao() { return situacao; }
    public void setSituacao(RotaVersaoSituacao situacao) { this.situacao = situacao; }

    @OneToMany(mappedBy = "rotaVersao", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @Where(clause = "seq_rota_sessao_superior IS NULL")
    public Set<RotaSessao> getSessoes() { return sessoes; }
    public void setSessoes(Set<RotaSessao> sessoes) { this.sessoes = sessoes; }


    /**
     * Método para adicionar uma sessao diretamente a uma versão
     * @param sessao
     */
    public void adicionarSessao(RotaSessao sessao) {
        sessao.setRotaVersao(this);
        sessoes.add(sessao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaVersao versao = (RotaVersao) o;
        return  getNumVersao() == versao.getNumVersao() &&
                getRota().getId() == versao.getRota().getId() &&
                getSituacao().getCodigo() == versao.getSituacao().getCodigo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumVersao(), getRota().getId(), getSituacao().getCodigo());
    }
}

