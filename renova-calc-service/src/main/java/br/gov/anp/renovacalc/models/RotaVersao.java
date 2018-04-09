package br.gov.anp.renovacalc.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NamedQueries({
    @NamedQuery(
            name = RotaVersao.QUERY_ENCONTRAR_POR_ROTA_ID,
            query = "SELECT v from RotaVersao v where rota.id = :rotaId"
    ),
    @NamedQuery(
            name = RotaVersao.QUERY_ENCONTRAR_POR_ROTA_E_SITUACAO,
            query = "SELECT v FROM RotaVersao v WHERE rota.id = :rotaId AND situacao.id = :situacaoId"
    )
})
@Entity
@Table(name = "ROTA_VERSAO")
public class RotaVersao implements IIdentificavel {

    private long id;

    private int numVersao;

    private Rota rota;

    private RotaVersaoSituacao situacao;

    private List<RotaSessao> sessoes;

    public static final String QUERY_ENCONTRAR_POR_ROTA_ID = "query.encontrar.por.rota.id";
    public static final String QUERY_ENCONTRAR_POR_ROTA_E_SITUACAO = "query.encontrar.por.rota.e.situacao";

    private RotaVersao() {
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "NUM_VERSAO")
    public int getNumVersao() { return numVersao; }
    public void setNumVersao(int numVersao) { this.numVersao = numVersao; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEQ_ROTA", nullable = false)
    public Rota getRota() { return rota; }
    public void setRota(Rota rota) { this.rota = rota; }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "COD_ROTA_VERSAO_SITUACAO", nullable = false)
    public RotaVersaoSituacao getSituacao() { return situacao; }
    public void setSituacao(RotaVersaoSituacao situacao) { this.situacao = situacao; }

    @OneToMany(mappedBy = "rotaVersao", fetch = FetchType.EAGER)
    @OrderBy("NUM_ORDEM")
    public List<RotaSessao> getSessoes() { return sessoes; }
    public void setSessoes(List<RotaSessao> sessoes) { this.sessoes = sessoes; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaVersao versao = (RotaVersao) o;
        return  getNumVersao() == versao.getNumVersao() &&
                getRota().getId() == versao.getRota().getId() &&
                getSituacao().getId() == versao.getSituacao().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumVersao(), getRota().getId(), getSituacao().getId());
    }
}

