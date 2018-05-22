/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 19/04/2018
 *          Arquivo: RotaUsinaDAO
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaUsina;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface para DAO com métodos de CRUD para a classe RotaUsina.
 * Implementado pelo Spring.
 */
public interface RotaUsinaDAO extends CrudRepository<RotaUsina, Long> {
}
