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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Interface que define o acesso de dados para Rota, incluindo CRUD básico
 * A classe concreta é implementada automaticamente pela framework Spring
 */
@Repository
public interface RotaDAO extends CrudRepository<Rota, Long> {


    /**
     * Método que retorna todas as rotas cujo nome começam com o
     * determinado prefixo
     * @param nome
     * @return
     */
    List<Rota> findByNomeLike(String nome);


}
