/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: RotaVersaoSituacaoDAO
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaVersaoSituacao;
import org.springframework.stereotype.Repository;

@Repository
public class RotaVersaoSituacaoDAO extends GenericDAO<RotaVersaoSituacao> {
    public RotaVersaoSituacaoDAO() {
        super(RotaVersaoSituacao.class);
    }

}
