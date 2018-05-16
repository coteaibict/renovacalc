/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 18/04/2018
 *          Arquivo: RotaUsina
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;

/**
 * Classe que representa uma Usina, conforme o modelo de dados
 */
@Entity
@Table(name = "TRNB_ROTA_USINA")
public class RotaUsina {

    private long id;

    private String cnpj;

    private Rota rota;

    public RotaUsina() { }

    public RotaUsina(String cnpj, Rota rota) {
        this.cnpj = cnpj;
        this.rota = rota;
    }

    @Id
    @Column(name = "SEQ_ROTA_USINA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceUsina")
    @SequenceGenerator(name = "sequenceUsina", sequenceName = "SRNB_ROTA_USINA", allocationSize = 1)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "NUM_CNPJ_USINA", length = 14, columnDefinition = "CHAR")
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA", nullable = false)
    public Rota getRota() { return rota; }
    public void setRota(Rota rota) { this.rota = rota; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaUsina rotaUsina = (RotaUsina) o;
        return getId() == rotaUsina.getId() && Objects.equals(getCnpj(), rotaUsina.getCnpj());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getCnpj());
    }
}
