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
 * Classe que faz a relação ManyToMany entre RotaResposta e RotaAtributo.
 */
@Entity
@Table(name = "ROTA_ATRIBUTO_RESPOSTA")
public class RotaAtributoResposta {

    public RespostaAtributoKey id;

    public String valor;

    public boolean avaliacaoANP;

    public String observacaoNaoConformidade;

    public RotaResposta resposta;

    public RotaAtributo atributo;


    public RotaAtributoResposta() {
        // Hibernate não inicializa este campo, causando NullPointerException
        this.id = new RespostaAtributoKey();
    }

    public RotaAtributoResposta(String valor, boolean avaliacaoANP,
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

    @Column(name = "IND_AVALIACAO_ANP")
    public boolean isAvaliacaoANP() { return avaliacaoANP; }
    public void setAvaliacaoANP(boolean avaliacaoANP) { this.avaliacaoANP = avaliacaoANP; }

    @Column(name = "DSC_OBS_NAO_CONFORMIDADE")
    public String getObservacaoNaoConformidade() { return observacaoNaoConformidade; }
    public void setObservacaoNaoConformidade(String observacaoNaoConformidade) { this.observacaoNaoConformidade = observacaoNaoConformidade; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("respostaId")
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    public RotaAtributo getAtributo() { return atributo; }
    public void setAtributo(RotaAtributo atributo) {
        this.atributo = atributo;
        if (atributo != null) {
            this.id.setAtributoId(atributo.getId());
        }
    }
}
