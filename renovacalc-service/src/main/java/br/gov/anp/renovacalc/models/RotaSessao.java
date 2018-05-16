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
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TRNB_ROTA_SESSAO")
public class RotaSessao {
    private long id;

    private String descricao;

    private int ordem;

    private int nivel;

    /**
     * Indica se esta sessao possui apenas atributos-resultado
     */
    private Boolean resultado;

    private RotaVersao rotaVersao;

    private Set<RotaSessaoAtributo> atributos = new HashSet<RotaSessaoAtributo>();

    private RotaSessao superior;


    private Set<RotaSessao> sessoesFilhas = new HashSet<>();

    public RotaSessao() { }

    public RotaSessao(RotaVersao versao, String descricao, int ordem, boolean resultado) {
        this.rotaVersao = versao;
        this.descricao = descricao;
        this.ordem = ordem;
        this.resultado = resultado;
        this.superior = null;
    }

    @Id
    @Column(name = "SEQ_ROTA_SESSAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceSessao")
    @SequenceGenerator(name = "sequenceSessao", sequenceName = "SRNB_ROTA_SESSAO", allocationSize = 2)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "DSC_ROTA_SESSAO")
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Column(name = "NUM_ORDEM")
    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }

    @Column(name = "NUM_NIVEL")
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    @Column(name = "IND_RESULTADO", columnDefinition = "CHAR(1)")
    @Type(type = "yes_no")
    public Boolean isResultado() { return resultado; }
    public void setResultado(Boolean resultado) { this.resultado = resultado; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA_VERSAO", nullable = false)
    public RotaVersao getRotaVersao() { return rotaVersao; }
    public void setRotaVersao(RotaVersao rotaVersao) { this.rotaVersao = rotaVersao; }

    @OneToMany(mappedBy = "sessao", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    public Set<RotaSessaoAtributo> getAtributos() { return atributos; }
    public void setAtributos(Set<RotaSessaoAtributo> atributos) { this.atributos = atributos; }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA_SESSAO_SUPERIOR", nullable = true)
    public RotaSessao getSuperior() { return superior; }
    public void setSuperior(RotaSessao superior) { this.superior = superior; }

    @OneToMany(mappedBy = "superior", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    public Set<RotaSessao> getSessoesFilhas() { return sessoesFilhas; }
    public void setSessoesFilhas(Set<RotaSessao> sessoesFilhas) { this.sessoesFilhas = sessoesFilhas; }

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
