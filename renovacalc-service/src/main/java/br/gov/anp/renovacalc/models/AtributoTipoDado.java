/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 10/04/2018
 *          Arquivo: AtributoTipoDado
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATRIBUTO_TIPO_DADO")
public class AtributoTipoDado {

    private long id;

    private String descricao;

    public AtributoTipoDado() {
    }

    public AtributoTipoDado(String descricao) {
        this.descricao = descricao;
    }

    @Id
    @Column(name = "COD_TIPO_DADO")
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "DSC_TIPO_DADO")
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
