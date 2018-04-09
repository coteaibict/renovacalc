/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: RotaSessaoDAO
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaSessao;
import org.springframework.stereotype.Repository;

@Repository
public class RotaSessaoDAO extends GenericDAO<RotaSessao> {
    public RotaSessaoDAO() {
        super(RotaSessao.class);
    }
}
