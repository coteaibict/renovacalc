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
import org.springframework.stereotype.Repository;

@Repository
public class RotaAtributoDAO extends GenericDAO<RotaAtributo> {

    public RotaAtributoDAO() {
        super(RotaAtributo.class);
    }
}
