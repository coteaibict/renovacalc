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
@Table(name = "TRNB_ATRIBUTO_TIPO_DADO")
public class AtributoTipoDado {

    private Character codigo;

    private String descricao;

    public AtributoTipoDado() {
    }

    public AtributoTipoDado(char codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Id
    @Column(name = "COD_TIPO_DADO")
    public Character getCodigo() { return codigo; }
    public void setCodigo(Character codigo) { this.codigo = codigo; }

    @Column(name = "DSC_TIPO_DADO", length = 10)
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
