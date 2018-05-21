/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaService
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.service;

import br.gov.anp.renovacalc.dao.*;
import br.gov.anp.renovacalc.models.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Classe de serviço para tratar o acesso a rotas.
 * Provê métodos para recuperar e salvar rotas e versões associadas.
 */
@Service
@Transactional
public class RotaService {

    @Autowired
    private RotaDAO rotaDAO;

    @Autowired
    private RotaVersaoDAO rotaVersaoDAO;

    @Autowired
    private RotaVersaoSituacaoDAO situacaoDAO;

    @Autowired
    private RotaSessaoDAO sessaoDAO;

    @Autowired
    private RotaAtributoDAO atributoDAO;

    private Logger logger = Logger.getLogger(RotaService.class);


    /**
     * Abstração de serviço para salvar uma rota
     * @param rota
     * @return Rota salva com ID gerado pelo DB
     */
    public Rota salvar(Rota rota) {
        rota = rotaDAO.save(rota);
        return rota;
    }

    /**
     * Abstração de serviço para criar uma nova versão de rota
     * @param versao
     * @return Objeto RotaVersão com ID gerado pelo DB
     */
    public RotaVersao salvarVersao(RotaVersao versao) {
        return rotaVersaoDAO.save(versao);
    }

    /**
     * Abstração de serviço para recuperar uma rota por ID
     * @param id
     * @return A rota encontrada caso exista, NULL caso não exista
     */
    public Rota encontrarPorID(long id) {
        return rotaDAO.findOne(id);
    }

    /**
     * Abstração de serviço para retornar todas as versões de uma
     * determinada rota.
     * @param rotaId
     * @return Lista com as versões (RotaVersao)
     */
    public List<RotaVersao> encontrarVersoesPorRotaID(long rotaId) {
        return rotaVersaoDAO.findByRotaId(rotaId);
    }

    /**
     * Abstração de serviço que retorna todas as rotas cujo nome
     * contém :nome
     * @param nome
     * @return Lista de rotas recuperadas
     */
    public List<Rota> encontrarPorNome(String nome) {
        return rotaDAO.findByNomeLike(nome);
    }

    public RotaVersao recuperarVersaoAtualDeRota(long rotaID) {
         return rotaVersaoDAO.versaoAtualPorRota(rotaID);
    }



    // Getters/Setters

    public RotaDAO getRotaDAO() {
        return rotaDAO;
    }

    public void setRotaDAO(RotaDAO rotaDAO) {
        this.rotaDAO = rotaDAO;
    }

    public RotaVersaoDAO getRotaVersaoDAO() {
        return rotaVersaoDAO;
    }

    public void setRotaVersaoDAO(RotaVersaoDAO rotaVersaoDAO) {
        this.rotaVersaoDAO = rotaVersaoDAO;
    }

    public RotaVersaoSituacao encontrarSituacaoPorId(long id) {
        return situacaoDAO.findOne(id);
    }

    public Iterable<Rota> recuperarRotas() {
        return rotaDAO.findAll();
    }
}
