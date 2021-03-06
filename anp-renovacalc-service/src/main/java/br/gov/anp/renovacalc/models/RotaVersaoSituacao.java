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

/**
 * Classe de modelo que representa uma situação que a determinada versão pode se encontrar
 */
@Entity
@Table(name = "TRNB_ROTA_VERSAO_SITUACAO")
public class RotaVersaoSituacao {

    private short codigo;

    private String descricao;

    public RotaVersaoSituacao() { }

    public RotaVersaoSituacao(short codigo, String desc) {
        this.codigo = codigo;
        this.descricao = desc;
    }


    // Getters/Setters

    @Id
    @Column(name = "COD_ROTA_VERSAO_SITUACAO", columnDefinition = "CHAR(1)")
    public short getCodigo() {
        return codigo;
    }
    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "DSC_ROTA_VERSAO_SITUACAO")
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

