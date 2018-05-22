/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 18/04/2018
 *          Arquivo: RespostaService
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.service;

import br.gov.anp.renovacalc.dao.*;
import br.gov.anp.renovacalc.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe de serviço para tratar acesso e avaliação de simulações de usuário (objetos RotaResposta)
 * Provê métodos para recuperar/salvar simulações, além de avaliar simulações.
 *
 */
@Service
@Transactional
public class RespostaService {
    @Autowired
    RotaRespostaDAO rotaRespostaDAO;

    @Autowired
    private RotaDAO rotaDAO;

    @Autowired
    private RotaVersaoDAO rotaVersaoDAO;

    @Autowired
    private RotaAtributoDAO atributoDAO;

    @Autowired
    private RotaUsinaDAO usinaDAO;

    public RespostaService() { }

    /**
     * Abstração de serviço para salvar uma resposta em banco
     * @param resposta
     * @return RotaResposta com ID gerado pelo banco
     */
    public RotaResposta salvarResposta(RotaResposta resposta) {
        resposta = rotaRespostaDAO.save(resposta);
        return resposta;
    }

    /**
     * Abstração de serviço para retornar a resposta atual
     * enviada pela usina  com :usinaID
     * @param usinaID
     * @return RotaResposta atual de :usinaID
     */
    public RotaResposta recuperarRespostaAtivaPorUsinaID(long usinaID) {
        return rotaRespostaDAO.recuperarRespostaAtivaPorUsinaID(usinaID);
    }


    /**
     * Método que retorna as submissões do usuário. Não retorna valores que estejam
     * relacionados a atributos calculados.
     * @return
     */
    public Set<RotaAtributoResposta> recuperarEntradas(RotaResposta resposta) {
        Set<RotaAtributoResposta> filtrado = new HashSet<>();

        for (RotaAtributoResposta atual : resposta.getRespostas()) {
            if ((atual.getAtributo().getFormula() == null) ||
                    ((atual.getAtributo().getFormula() != null) &&
                            atual.getAtributo().getFormula().isEmpty())) {
                filtrado.add(atual);
            }
        }
        return filtrado;
    }

    // Getters/Setters

    public RotaRespostaDAO getRotaRespostaDAO() { return rotaRespostaDAO; }
    public void setRotaRespostaDAO(RotaRespostaDAO rotaRespostaDAO) { this.rotaRespostaDAO = rotaRespostaDAO; }
}
