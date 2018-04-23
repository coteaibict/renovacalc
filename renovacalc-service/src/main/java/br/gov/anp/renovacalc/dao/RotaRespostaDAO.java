/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 18/04/2018
 *          Arquivo: RotaRespostaDAO
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.RotaResposta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RotaRespostaDAO extends CrudRepository<RotaResposta, Long> {

    /**
     * Método que retorna a submissao atual de uma usina
     * Assume que existe apenas uma RotaResposta atual no banco para esta usina
     * @param usinaID
     * @return RotaResposta atual para usina especificada
     */
    @Query("SELECT r FROM RotaResposta r "
            + "WHERE r.ativo = true "
                + "AND r.usina.id = :usinaID")
    RotaResposta recuperarRespostaAtivaPorUsinaID(@Param("usinaID") long usinaID);

}
