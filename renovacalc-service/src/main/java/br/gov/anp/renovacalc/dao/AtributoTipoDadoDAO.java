/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 11/04/2018
 *          Arquivo: AtributoTipoDadoDAO
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.AtributoTipoDado;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface para DAO com métodos de CRUD para a classe AtributoTipoDado.
 * Implementado pelo Spring.
 */
public interface AtributoTipoDadoDAO extends CrudRepository<AtributoTipoDado, Long> {
}
