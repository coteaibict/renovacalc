/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaSessao
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ROTA_SESSAO")
public class RotaSessao {
    private long id;

    private String descricao;

    private int ordem;

    private RotaVersao rotaVersao;

    private Set<RotaSessaoAtributo> atributos = new HashSet<RotaSessaoAtributo>();

    public RotaSessao() { }

    public RotaSessao(RotaVersao versao, String descricao, int ordem) {
        this.rotaVersao = versao;
        this.descricao = descricao;
        this.ordem = ordem;
    }

    @Id
    @Column(name = "SEQ_ROTA_SESSAO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "DSC_ROTA_SESSAO")
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Column(name = "NUM_ORDEM")
    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA_VERSAO", nullable = false)
    public RotaVersao getRotaVersao() { return rotaVersao; }
    public void setRotaVersao(RotaVersao rotaVersao) { this.rotaVersao = rotaVersao; }

    @OneToMany(mappedBy = "sessao", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OrderBy("NUM_ORDEM")
    public Set<RotaSessaoAtributo> getAtributos() { return atributos; }
    public void setAtributos(Set<RotaSessaoAtributo> atributos) { this.atributos = atributos; }

    /**
     * Método para adicionar um atributo diretamente a uma sessao, lidando com a tabela
     * intermediária RotaSessaoAtributo
     * @param atributo
     */
    public void adicionarAtributo(RotaAtributo atributo, String valorPadrao) {
        RotaSessaoAtributo sessaoAtributo = new RotaSessaoAtributo(this, atributo, valorPadrao,
                1, true, true, true, false);
        atributos.add(sessaoAtributo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaSessao that = (RotaSessao) o;
        return getId() == that.getId() &&
               getOrdem() == that.getOrdem() &&
               Objects.equals(getDescricao(), that.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescricao(), getOrdem());
    }
}
