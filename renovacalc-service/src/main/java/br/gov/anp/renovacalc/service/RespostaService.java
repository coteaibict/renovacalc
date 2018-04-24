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

    public void setup () {
        Rota rotaParam = new Rota("etanol");
        rotaDAO.save(rotaParam);
        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(1, "atual");
        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam, 1);
        rotaVersaoDAO.save(versaoParam);

        AtributoTipoDado tipo = new AtributoTipoDado(1, "numerico");
        AtributoTipoDado tipo2 = new AtributoTipoDado(2, "selecionavel");

        RotaAtributo atributoParam = new RotaAtributo("atributo1", "blabla",
                "", "ATR1", 0, 0, "", 0, false, tipo);

        atributoDAO.save(atributoParam);

        RotaUsina usinaParam = new RotaUsina("00.000.000/0000-00", rotaParam);
        usinaDAO.save(usinaParam);
        Timestamp timeParam = new Timestamp(System.currentTimeMillis());

        RotaResposta respostaParam = new RotaResposta("usina1", "endereco", "1", "complemento", "bairro", "70000000", "nome", "900000000", "email@email.com", true, timeParam, usinaParam, versaoParam);

        RotaAtributoResposta item = new RotaAtributoResposta("0", false, "", respostaParam, atributoParam);

        respostaParam.adicionarRespostaAtributo(item);

        rotaRespostaDAO.save(respostaParam);


    }

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

    public RotaRespostaDAO getRotaRespostaDAO() { return rotaRespostaDAO; }
    public void setRotaRespostaDAO(RotaRespostaDAO rotaRespostaDAO) { this.rotaRespostaDAO = rotaRespostaDAO; }
}
