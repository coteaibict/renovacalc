/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 10/04/2018
 *          Arquivo: RotaAtributoDAO
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaAtributo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Interface para DAO com métodos de CRUD para a classe RotaAtributo.
 * Implementado pelo Spring.
 */
@Repository
public interface RotaAtributoDAO extends CrudRepository<RotaAtributo, Long> {

    /**
     * Método para retornar todos os atributos calculados de uma
     * determinada versão
     * @param versaoID
     * @return Conjunto com todos os atributos obrigatórios da versão
     */
    @Query("SELECT atributo FROM RotaSessaoAtributo sa "
            + "INNER JOIN sa.sessao sessao "
            + "INNER JOIN sa.atributo atributo "
            + "WHERE sa.sessao.rotaVersao.id = :id AND sa.atributo.formula IS NOT NULL")
    public Set<RotaAtributo> recuperarCalculadosPorVersao(@Param("id") long versaoID);

    /**
     * Método para retornar todos os atributos input obrigatórios
     * determinada versão
     * @param versaoID
     * @return Conjunto com todos os atributos input da versão
     */
    @Query("SELECT atributo FROM RotaSessaoAtributo sa "
            + "INNER JOIN sa.sessao sessao "
            + "INNER JOIN sa.atributo atributo "
            + "WHERE sa.sessao.rotaVersao.id = :id AND sa.atributo.formula IS NULL AND sa.obrigatorio IS TRUE")
    public Set<RotaAtributo> recuperarInputObrigatorioPorVersao(@Param("id") long versaoID);
}
