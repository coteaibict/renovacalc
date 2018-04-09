/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: RotaSessao
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ROTA_SESSAO")
public class RotaSessao implements IIdentificavel {
    private long id;

    private String descricao;

    private int ordem;

    private RotaVersao rotaVersao;


    public RotaSessao() { }

    public RotaSessao(RotaVersao versao, String descricao, int ordem) {
        this.rotaVersao = versao;
        this.descricao = descricao;
        this.ordem = ordem;
    }

    @Id
    @Column(name = "SEQ_ROTA_SESSAO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "DESC_ROTA_SESSAO")
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Column(name = "NUM_ORDEM")
    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEQ_ROTA_VERSAO", nullable = false)
    public RotaVersao getRotaVersao() { return rotaVersao; }
    public void setRotaVersao(RotaVersao rotaVersao) { this.rotaVersao = rotaVersao; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaSessao that = (RotaSessao) o;
        return getId() == that.getId() && getRotaVersao().getId() == that.getRotaVersao().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRotaVersao().getId());
    }
}
