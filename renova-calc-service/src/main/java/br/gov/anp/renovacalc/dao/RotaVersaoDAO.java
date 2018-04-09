/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: RotaVersaoDAO
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaVersao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RotaVersaoDAO extends GenericDAO<RotaVersao> {

    public RotaVersaoDAO() {
       super(RotaVersao.class);
    }

    /**
     * Método para retornar todas as versões de uma determinada rota
     * para uma dada situacao
     * Caso a rota ou situacao não exista, retorna apenas a lista vazia.
     * @param rotaId
     * @param situacaoId
     * @return Lista com as versões da rota com ID rotaId e situacao
     * correspondente a situacaoId
     */
    @SuppressWarnings("unchecked")
    public List<RotaVersao> encontrarVersoesPorSituacaoERotaID(long rotaId, long situacaoId) {
        Query query = em.unwrap(Session.class).getNamedQuery(RotaVersao.QUERY_ENCONTRAR_POR_ROTA_E_SITUACAO);
        query.setLong("rotaId", rotaId);
        query.setLong("situacaoId", situacaoId);
        return (List<RotaVersao>) query.list();
    }

    /**
     * Método para retornar todas as versões de uma determinada rota.
     * Caso a rota não exista, retorna apenas a lista vazia.
     * @param rotaId
     * @return Lista com as versões da rota com ID rotaId
     */
    @SuppressWarnings("unchecked")
    public List<RotaVersao> encontrarPorRotaID(long rotaId) {
        Query query = em.unwrap(Session.class).getNamedQuery(RotaVersao.QUERY_ENCONTRAR_POR_ROTA_ID);
        query.setLong("rotaId", rotaId);
        return (List<RotaVersao>) query.list();
    }
}

