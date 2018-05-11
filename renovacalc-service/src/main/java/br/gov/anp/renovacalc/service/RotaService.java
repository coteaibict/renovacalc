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

    @Autowired
    private EntityManager em;

    private Logger logger = Logger.getLogger(RotaService.class);

    public void setup() {
        Rota rota = salvar(new Rota("etanol"));

        RotaVersaoSituacao situacao = situacaoDAO.save(new RotaVersaoSituacao(1, "atual"));

        RotaVersao versao = new RotaVersao(rota, situacao, 1);


        AtributoTipoDado tipo = new AtributoTipoDado(1, "numerico");
        em.persist(tipo);
        AtributoTipoDado tipo2 = new AtributoTipoDado(2, "selecionavel");
        em.persist(tipo2);

        // Sessao 1

        RotaSessao sessao1 = new RotaSessao(versao, "sessao1", 1, false);
        RotaSessao sessaoFilha1 = new RotaSessao(versao, "filha1", 1, false);

        sessaoFilha1.setSuperior(sessao1);
        sessao1.getSessoesFilhas().add(sessaoFilha1);

        RotaAtributo atributo = new RotaAtributo("atributo1", "blabla",
                "m", "ATR1", 0, 0, "", 0, false, tipo);

//        atributo.adicionarItem(new RotaAtributoItem("item1", atributo));
        sessao1.adicionarAtributo(atributo,"0");

        versao.adicionarSessao(sessao1);

        // Sessao 2

        RotaSessao sessao2 = new RotaSessao(versao, "sessao2", 2, false);
        RotaSessao sessaoFilha2 = new RotaSessao(versao, "filha2", 1, false);

        sessaoFilha2.setSuperior(sessao2);
        sessao2.getSessoesFilhas().add(sessaoFilha2);

        sessao2.adicionarAtributo(new RotaAtributo("atributo2", "blablabla",
                "m", "ATR2", 10, 0, "", 0, false, tipo), "0");

        versao.adicionarSessao(sessao2);

        // Sessao Resposta


        RotaSessao sessaoResposta = new RotaSessao(versao, "sessaoResposta", 1, true);

        sessaoResposta.adicionarAtributo(new RotaAtributo("resultado", "resultado final",
                "m", "RES1", 10, 0, "ATR1 + ATR2", 1, true, tipo), "0");

        versao.adicionarSessao(sessaoResposta);

        // Salvando

        rotaVersaoDAO.save(versao);
    }

    public void tst() {
    }

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
