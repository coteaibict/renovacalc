/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
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

@NamedQueries({
        @NamedQuery(
                name = Rota.QUERY_ENCONTRAR_POR_PADRAO_NOME,
                query = "SELECT r from Rota r where r.nome LIKE :padrao"
        ),
})
@Entity
@Table(name = "ROTA")
public class Rota implements IIdentificavel{

    private long id;

    private String nome;

    public static final String QUERY_ENCONTRAR_POR_PADRAO_NOME = "query.encontrar.por.padrao.nome";

    public Rota() { }

    public Rota(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Rota(String nome) {
        this.nome = nome;
    }

    @Id
    @Column(name = "SEQ_ROTA")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "NOM_ROTA")
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
