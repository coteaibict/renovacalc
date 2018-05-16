/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 10/04/2018
 *          Arquivo: SessaoAtributoKey
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que guarda as chaves estrangeiras da tabela SessaoAtributo.
 * Usada como chave composta de RotaSessaoAtributo
 */
@Embeddable
public class SessaoAtributoKey implements Serializable {

    private long sessaoId;

    private long atributoId;

    public SessaoAtributoKey() {
    }

    public long getSessaoId() { return sessaoId; }
    public void setSessaoId(long sessaoId) { this.sessaoId = sessaoId; }

    public long getAtributoId() { return atributoId; }
    public void setAtributoId(long atributoId) { this.atributoId = atributoId; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SessaoAtributoKey that = (SessaoAtributoKey) o;
        return getSessaoId() == that.getSessaoId() &&
               getAtributoId() == that.getAtributoId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessaoId(), getAtributoId());
    }
}
