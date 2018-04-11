/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaDAO
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.models.Rota;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RotaDAO extends GenericDAO<Rota> {

    public RotaDAO() {
        super(Rota.class);
    }

    /**
     * Método que retorna todas as rotas cujo nome começam com o
     * determinado prefixo
     * @param prefixo
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Rota> encontrarPorNome(String prefixo) {
        Query query = em.unwrap(Session.class).getNamedQuery(Rota.QUERY_ENCONTRAR_POR_PADRAO_NOME);
        query.setString("padrao", prefixo + "%");
        return (List<Rota>) query.list();
    }

}
