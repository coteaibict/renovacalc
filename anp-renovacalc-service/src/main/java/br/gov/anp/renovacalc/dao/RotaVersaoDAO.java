/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaVersaoDAO
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaVersao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface para DAO com métodos de CRUD para a classe RotaVersao.
 * Implementado pelo Spring.
 */
@Repository
public interface RotaVersaoDAO extends CrudRepository<RotaVersao, Long> {

    /**
     * Método para retornar a versão atual da rota.
     * Assume que a situação cadastrada como atual possui código 1
     * e que apenas uma versão é atual
     * @param rotaId
     * @return A versão atual da rota
     */
    @Query("SELECT v FROM RotaVersao v WHERE v.situacao.id = 1 and v.rota.id = :id")
    RotaVersao versaoAtualPorRota(@Param("id") long rotaId);

    /**
     * Método para retornar todas as versões de uma determinada rota
     * para uma dada situacao
     * Caso a rota ou situacao não exista, retorna apenas a lista vazia.
     * @param rotaId
     * @param situacaoCodigo
     * @return Lista com as versões da rota com ID rotaId e situacao
     * correspondente a situacaoCodigo
     */
    List<RotaVersao> findByRotaIdAndSituacaoCodigo(long rotaId, long situacaoCodigo);

    /**
     * Método para retornar todas as versões de uma determinada rota.
     * Caso a rota não exista, retorna apenas a lista vazia.
     * @param rotaId
     * @return Lista com as versões da rota com ID rotaId
     */
    List<RotaVersao> findByRotaId(long rotaId);

}

