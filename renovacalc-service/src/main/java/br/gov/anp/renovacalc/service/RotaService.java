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
import br.gov.anp.renovacalc.exception.RecursoNaoEncontradoException;
import br.gov.anp.renovacalc.models.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void setup() {
        Rota rota = salvar(new Rota(0, "etanol"));
        RotaVersaoSituacao situacao = situacaoDAO.salvar(new RotaVersaoSituacao(1, "atual"));
        RotaVersao versao = new RotaVersao(rota, situacao, 1);
        RotaSessao sessao1 = new RotaSessao(versao, "sessao2", 2);
        RotaSessao sessao2 = new RotaSessao(versao, "sessao1", 1);
        sessao1.adicionarAtributo(new RotaAtributo("atributo1", "blabla",
                "m", "ATR1", 10, 0, "1+1" ), "0");
        sessao2.adicionarAtributo(new RotaAtributo("atributo2", "blablabla",
                "m", "ATR2", 10, 0, "1+1" ), "0");
        versao.adicionarSessao(sessao1);
        versao.adicionarSessao(sessao2);
        rotaVersaoDAO.salvar(versao);
    }

    public void del() {
        RotaAtributo atr = atributoDAO.encontrarTodas().get(0);
        atributoDAO.remover(atr);
    }

    /**
     * Abstração de serviço para salvar uma rota nova
     * @param rota
     * @return Rota salva com ID gerado pelo DB
     */
    public Rota salvar(Rota rota) {
        rota = rotaDAO.salvar(rota);
        return rota;
    }

    /**
     * Abstração de serviço para atualizar uma rota
     * @param rota
     * @throws RecursoNaoEncontradoException : quando rota já possui
     * um ID que nao tem correspondente no banco
     * @return Rota atualizada
     */
    public Rota atualizar(Rota rota) throws RecursoNaoEncontradoException {
        rota = rotaDAO.atualizar(rota);
        return rota;
    }

    /**
     * Abstração de serviço para criar uma nova versão de rota
     * @param versao
     * @return Objeto RotaVersão com ID gerado pelo DB
     */
    public RotaVersao salvarVersao(RotaVersao versao) {
        return rotaVersaoDAO.salvar(versao);
    }

    /**
     * Abstração de serviço para recuperar uma rota por ID
     * @param id
     * @return A rota encontrada caso exista, NULL caso não exista
     */
    public Rota encontrarPorID(long id) {
        return rotaDAO.encontrarPorID(id);
    }

    /**
     * Abstração de serviço para retornar todas as versões de uma
     * determinada rota.
     * @param rotaId
     * @return Lista com as versões (RotaVersao)
     */
    public List<RotaVersao> encontrarVersoesPorRotaID(long rotaId) {
        return rotaVersaoDAO.encontrarPorRotaID(rotaId);
    }

    /**
     * Abstração de serviço que retorna todas as rotas cujo nome
     * começam com o prefixo enviado
     * @param prefixo
     * @return Lista de rotas recuperadas
     */
    public List<Rota> encontrarPorNome(String prefixo) {
        return rotaDAO.encontrarPorNome(prefixo);
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
        return situacaoDAO.encontrarPorID(id);
    }
}
