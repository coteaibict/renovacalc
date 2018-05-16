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

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TRNB_ROTA_ATRIBUTO")
public class RotaAtributo {

    private long id;

    private String nome;

    private String descricao;

    private String unidadeMedida;

    private String tag;

    private int tamanho;

    private int precisao;

    private String formula;

    /**
     * Campo utilizado para identificar a importancia do atributo,
     * em caso de este ser um atributo-resposta a ser exibido
     */
    private int peso;

    /**
     * Campo utilizado para informar se este é um atributo-resposta principal
     */
    private boolean principal;

    private AtributoTipoDado tipo;

    private Set<RotaAtributoItem> items = new HashSet<>();

    public RotaAtributo() { }

    public RotaAtributo(String nome, String descricao, String unidadeMedida, String tag, int tamanho, int precisao,
            String formula, int peso, boolean principal, AtributoTipoDado tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.tag = tag;
        this.tamanho = tamanho;
        this.precisao = precisao;
        this.formula = formula;
        this.peso = peso;
        this.principal = principal;
        this.tipo = tipo;
    }

    @Id
    @Column(name = "SEQ_ROTA_ATRIBUTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceAtributo")
    @SequenceGenerator(name = "sequenceAtributo", sequenceName = "SRNB_ROTA_ATRIBUTO", allocationSize = 1)
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

    @Column(name = "VAL_PESO_ATRIBUTO")
    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }

    @Column(name = "IND_PRINCIPAL", columnDefinition = "CHAR(1)")
    @Type(type = "yes_no")
    public boolean isPrincipal() { return principal; }
    public void setPrincipal(boolean principal) { this.principal = principal; }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "COD_TIPO_DADO", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public AtributoTipoDado getTipo() { return tipo; }
    public void setTipo(AtributoTipoDado tipo) { this.tipo = tipo; }

    @OneToMany(mappedBy = "atributo", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    public Set<RotaAtributoItem> getItems() { return items; }
    public void setItems(Set<RotaAtributoItem> items) { this.items = items; }


    /**
     * Método para adicionar um item diretamente a um atributo
     * @param item
     */
    public void adicionarItem(RotaAtributoItem item) {
        item.setAtributo(this);
        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaAtributo atributo = (RotaAtributo) o;
        return getId() == atributo.getId() && getTamanho() == atributo.getTamanho() && getPrecisao() == atributo
                .getPrecisao() && getPeso() == atributo.getPeso() && isPrincipal() == atributo.isPrincipal() && Objects
                .equals(getNome(), atributo.getNome()) && Objects.equals(getDescricao(), atributo.getDescricao())
                && Objects.equals(getUnidadeMedida(), atributo.getUnidadeMedida()) && Objects
                .equals(getTag(), atributo.getTag()) && Objects.equals(getFormula(), atributo.getFormula())
                && getTipo().getCodigo().equals(atributo.getTipo().getCodigo());
    }

    @Override
    public int hashCode() {

        return Objects
                .hash(getId(), getNome(), getDescricao(), getUnidadeMedida(), getTag(), getTamanho(), getPrecisao(),
                        getFormula(), getPeso(), isPrincipal(), getTipo().getCodigo());
    }
}
