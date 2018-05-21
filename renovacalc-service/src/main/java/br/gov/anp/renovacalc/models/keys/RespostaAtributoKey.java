/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 17/04/2018
 *          Arquivo: RespostaAtributoKey
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models.keys;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que guarda as chaves estrangeiras da tabela RespostaAtributo.
 * Usada como chave composta de RotaAtributoResposta
 */
public class RespostaAtributoKey implements Serializable {

    /**
     * Chave primária da simulação
     */
    private long respostaId;

    /**
     * Chave primária do atributo
     */
    private long atributoId;

    public RespostaAtributoKey() {
    }

    public long getRespostaId() { return respostaId; }
    public void setRespostaId(long respostaId) { this.respostaId = respostaId; }

    public long getAtributoId() { return atributoId; }
    public void setAtributoId(long atributoId) { this.atributoId = atributoId; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RespostaAtributoKey that = (RespostaAtributoKey) o;
        return getRespostaId() == that.getRespostaId() && getAtributoId() == that.getAtributoId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRespostaId(), getAtributoId());
    }
}
