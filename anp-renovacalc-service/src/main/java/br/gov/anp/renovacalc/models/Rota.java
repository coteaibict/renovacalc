/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: Rota
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */


package br.gov.anp.renovacalc.models;

import javax.persistence.*;
import java.util.Objects;

/**
 * Classe de modelo que representa uma rota
 */
@Entity
@Table(name = "TRNB_ROTA")
public class Rota {

    private long id;

    private String nome;

    public Rota() { }

    public Rota(String nome) {
        this.nome = nome;
    }

    @Id
    @Column(name = "SEQ_ROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceRota")
    @SequenceGenerator(name = "sequenceRota", sequenceName = "SRNB_ROTA", allocationSize = 1)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "NOM_ROTA", length = 200)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rota rota = (Rota) o;
        return getId() == rota.getId() && Objects.equals(getNome(), rota.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome());
    }

    @Override
    public String toString() {
        return "Rota{" + "id=" + id + ", nome='" + nome + '\'' + '}';
    }
}
