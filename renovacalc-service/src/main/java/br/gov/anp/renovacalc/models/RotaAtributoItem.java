/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 11/04/2018
 *          Arquivo: RotaAtributoItem
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "ROTA_ATRIBUTO_ITEM")
public class RotaAtributoItem {
    private long id;

    private String descricao;

    private RotaAtributo atributo;

    public RotaAtributoItem() { }

    public RotaAtributoItem(long id, String descricao, RotaAtributo atributo) {
        this.id = id;
        this.descricao = descricao;
        this.atributo = atributo;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Id
    @Column(name = "SEQ_ROTA_ATRIBUTO_ITEM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceAtributoItem")
    @SequenceGenerator(name = "sequenceAtributoItem", sequenceName = "SICO_ATRIBUTO_ITEM", allocationSize = 1)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "DSC_ROTA_ATRIBUTO_ITEM")
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA_ATRIBUTO", nullable = false)
    public RotaAtributo getAtributo() { return atributo; }
    public void setAtributo(RotaAtributo atributo) { this.atributo = atributo; }
}
