/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 09/04/2018
 *         Arquivo: RotaSessaoAtributo
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models;

import br.gov.anp.renovacalc.models.keys.SessaoAtributoKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Classe que faz a relação ManyToMany entre RotaSessao e RotaAtributo.
 */
@Entity
@Table(name = "ROTA_SESSAO_ATRIBUTO")
public class RotaSessaoAtributo {

    private SessaoAtributoKey id;

    private String valorPadrao;

    private int ordem;

    private boolean obrigatorio;

    private boolean visivel;

    private boolean editavel;

    private boolean avaliado;

    private RotaSessao sessao;

    private RotaAtributo atributo;

    public RotaSessaoAtributo() {
       // Hibernate não inicializa este campo, causando NullPointerException
       this.id = new SessaoAtributoKey();
    }

    public RotaSessaoAtributo(RotaSessao sessao, RotaAtributo atributo, String valorPadrao, int ordem, boolean obrigatorio,
            boolean visivel, boolean editavel, boolean avaliado) {
        this.valorPadrao = valorPadrao;
        this.ordem = ordem;
        this.sessao = sessao;
        this.atributo = atributo;
        this.obrigatorio = obrigatorio;
        this.visivel = visivel;
        this.editavel = editavel;
        this.avaliado = avaliado;

        // Hibernate não inicializa este campo, causando NullPointerException
        this.id = new SessaoAtributoKey();
    }

    @EmbeddedId
    public SessaoAtributoKey getId() { return id; }
    public void setId(SessaoAtributoKey id) { this.id = id; }

    @Column(name = "VAL_PADRAO")
    public String getValorPadrao() { return valorPadrao; }
    public void setValorPadrao(String valorPadrao) { this.valorPadrao = valorPadrao; }

    @Column(name = "NUM_ORDEM")
    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }

    @Column(name = "IND_OBRIGATORIO")
    @Type(type="true_false")
    public boolean isObrigatorio() { return obrigatorio; }
    public void setObrigatorio(boolean obrigatorio) { this.obrigatorio = obrigatorio; }

    @Column(name = "IND_VISIVEL")
    public boolean isVisivel() { return visivel; }
    public void setVisivel(boolean visivel) { this.visivel = visivel; }

    @Column(name = "IND_EDITAVEL")
    @Type(type="true_false")
    public boolean isEditavel() { return editavel; }
    public void setEditavel(boolean editavel) { this.editavel = editavel; }

    @Column(name = "IND_AVALIADO")
    @Type(type="true_false")
    public boolean isAvaliado() { return avaliado; }
    public void setAvaliado(boolean avaliado) { this.avaliado = avaliado; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sessaoId")
    public RotaSessao getSessao() { return this.sessao; }
    public void setSessao(RotaSessao sessao) {
        this.sessao = sessao;
        if (sessao != null) {
            this.id.setSessaoId(sessao.getId());
        }
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @Fetch(FetchMode.JOIN)
    @MapsId("atributoId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public RotaAtributo getAtributo() { return this.atributo; }
    public void setAtributo(RotaAtributo atributo) {
        this.atributo = atributo;
        if (atributo != null) {
            this.id.setAtributoId(atributo.getId());
        }

    }
}
