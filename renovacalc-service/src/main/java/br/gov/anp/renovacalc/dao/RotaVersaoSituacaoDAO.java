/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaVersaoSituacaoDAO
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaVersaoSituacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que define o acesso de dados para RotaVersaoSituacao, incluindo CRUD básico
 * A classe concreta é implementada automaticamente pela framework Spring
 */
@Repository
public interface RotaVersaoSituacaoDAO extends CrudRepository<RotaVersaoSituacao, Long> {
}
