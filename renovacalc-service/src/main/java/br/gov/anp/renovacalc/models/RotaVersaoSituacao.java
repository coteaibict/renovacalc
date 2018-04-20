/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaVersaoSituacao
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ROTA_VERSAO_SITUACAO")
public class RotaVersaoSituacao {

    private long codigo;

    private String descricao;

    public RotaVersaoSituacao() { }

    public RotaVersaoSituacao(long codigo, String desc) {
        this.codigo = codigo;
        this.descricao = desc;
    }

    public RotaVersaoSituacao(String desc) {
        this.descricao = desc;
    }

    // Getters/Setters

    @Id
    @Column(name = "COD_ROTA_VERSAO_SITUACAO")
    public long getCodigo() {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    @Column(name = "DESC_ROTA_VERSAO_SITUACAO")
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaVersaoSituacao that = (RotaVersaoSituacao) o;
        return Objects.equals(getDescricao(), that.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescricao());
    }
}

