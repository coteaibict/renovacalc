/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 09/04/2018
 *          Arquivo: RotaAtributo
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "ROTA_ATRIBUTO")
public class RotaAtributo implements IIdentificavel {

    private long id;

    private String nome;

    private String descricao;

    private String unidadeMedida;

    private String tag;

    private int tamanho;

    private int precisao;

    private String formula;

    //private AtributoTipoDado tipo;

    public RotaAtributo() { }

    public RotaAtributo(String nome, String descricao, String unidadeMedida, String tag, int tamanho,
            int precisao, String formula) {
        this.nome = nome;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.tag = tag;
        this.tamanho = tamanho;
        this.precisao = precisao;
        this.formula = formula;
    }

    @Id
    @Column(name = "SEQ_ROTA_ATRIBUTO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "NOM_ATRIBUTO")
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Column(name = "DSC_ATRIBUTO")
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Column(name = "DSC_UNIDADE_MEDIDA")
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    @Column(name = "COD_TAG")
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    @Column(name = "NUM_TAMANHO")
    public int getTamanho() { return tamanho; }
    public void setTamanho(int tamanho) { this.tamanho = tamanho; }

    @Column(name = "NUM_PRECISAO")
    public int getPrecisao() { return precisao; }
    public void setPrecisao(int precisao) { this.precisao = precisao; }

    @Column(name = "DSC_FORMULA")
    public String getFormula() { return formula; }
    public void setFormula(String formula) { this.formula = formula; }

//    @ManyToOne(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.JOIN)
//    @JoinColumn(name = "COD_TIPO_DADO", nullable = false)
//    public AtributoTipoDado getTipo() { return tipo; }
//    public void setTipo(AtributoTipoDado tipo) { this.tipo = tipo; }

}
