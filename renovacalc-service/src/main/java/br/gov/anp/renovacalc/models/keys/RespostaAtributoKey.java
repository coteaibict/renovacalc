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

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que guarda as chaves estrangeiras da tabela RespostaAtributo.
 * Usada como chave composta de RotaRespostaAtributo
 */
public class RespostaAtributoKey implements Serializable {
    private long repostaId;

    private long atributoId;

    public RespostaAtributoKey() {
    }

    @Column(name = "SEQ_ROTA_REPOSTA")
    public long getRepostaId() { return repostaId; }
    public void setRepostaId(long repostaId) { this.repostaId = repostaId; }

    @Column(name = "SEQ_ROTA_ATRIBUTO")
    public long getAtributoId() { return atributoId; }
    public void setAtributoId(long atributoId) { this.atributoId = atributoId; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RespostaAtributoKey that = (RespostaAtributoKey) o;
        return getRepostaId() == that.getRepostaId() && getAtributoId() == that.getAtributoId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRepostaId(), getAtributoId());
    }
}
