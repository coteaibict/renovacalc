/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 17/04/2018
 *          Arquivo: RotaRespostaAtributo
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models;

import br.gov.anp.renovacalc.models.keys.RespostaAtributoKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Classe que faz a relação ManyToMany entre RotaResposta e RotaAtributo,
 * possuindo também o valor da submissão e a resposta das avaliações
 */
@Entity
@Table(name = "TRNB_ROTA_ATRIBUTO_RESPOSTA")
public class RotaAtributoResposta {

    /**
     * Chave primária composta da tabela
     */
    public RespostaAtributoKey id;

    public String valor;

    public char avaliacaoANP;

    public String observacaoNaoConformidade;

    public RotaResposta resposta;

    public RotaAtributo atributo;


    public RotaAtributoResposta() {
        // Hibernate não inicializa este campo, causando NullPointerException
        this.id = new RespostaAtributoKey();
    }

    public RotaAtributoResposta(String valor, char avaliacaoANP,
            String observacaoNaoConformidade, RotaResposta resposta, RotaAtributo atributo) {
        this.valor = valor;
        this.avaliacaoANP = avaliacaoANP;
        this.observacaoNaoConformidade = observacaoNaoConformidade;
        this.resposta = resposta;
        this.atributo = atributo;

        // Hibernate não inicializa este campo, causando NullPointerException
        this.id = new RespostaAtributoKey();
    }

    @EmbeddedId
    public RespostaAtributoKey getId() { return id; }
    public void setId(RespostaAtributoKey id) {
        this.id = id;
        if (id == null) {
            this.id = new RespostaAtributoKey();
        }
    }

    @Column(name = "DSC_RESPOSTA")
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    @Column(name = "IND_AVALIACAO_ANP", columnDefinition = "CHAR(1)")
    public char getAvaliacaoANP() { return avaliacaoANP; }
    public void setAvaliacaoANP(char avaliacaoANP) { this.avaliacaoANP = avaliacaoANP; }

    @Column(name = "DSC_OBSERVAO_NAO_CONFORMIDADE")
    public String getObservacaoNaoConformidade() { return observacaoNaoConformidade; }
    public void setObservacaoNaoConformidade(String observacaoNaoConformidade) { this.observacaoNaoConformidade = observacaoNaoConformidade; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("respostaId")

    @JoinColumn(name = "SEQ_ROTA_RESPOSTA")
    public RotaResposta getResposta() { return resposta; }
    public void setResposta(RotaResposta resposta) {
        this.resposta = resposta;
        if (resposta != null) {
            this.id.setRespostaId(resposta.getId());
        }
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @MapsId("atributoId")
    @JoinColumn(name = "SEQ_ROTA_ATRIBUTO")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public RotaAtributo getAtributo() { return atributo; }
    public void setAtributo(RotaAtributo atributo) {
        this.atributo = atributo;
        if (atributo != null) {
            this.id.setAtributoId(atributo.getId());
        }
    }
}
