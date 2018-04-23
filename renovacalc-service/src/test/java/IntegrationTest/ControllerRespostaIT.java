/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 20/04/2018
 *          Arquivo: ControllerRespostaIT
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package IntegrationTest;

import br.gov.anp.renovacalc.controller.RespostaController;
import br.gov.anp.renovacalc.exception.ArgumentoInvalidoException;
import br.gov.anp.renovacalc.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/testContext.xml" })
@WebAppConfiguration(value = "")
public class ControllerRespostaIT {

    @Autowired
    private EntityManager em;

    @Autowired
    private RespostaController respostaController;



    @Test
    @Transactional
    public void deveSalvarResposta() {
        RotaVersao versaoParam = auxiliarCarregarRotaVersao();

        RotaUsina usinaParam = new RotaUsina("00.000.000/0000-00", versaoParam.getRota());
        em.persist(usinaParam);

        Timestamp timeParam = new Timestamp(System.currentTimeMillis());
        RotaVersao versaoRef = new RotaVersao();
        versaoRef.setId((long) 1);
        RotaResposta respostaParam = new RotaResposta("usina1", "endereco", "1", "complemento", "bairro", "70000000", "nome", "900000000", "email@email.com", true, timeParam, usinaParam, versaoRef);

        RotaAtributo atributoParam = versaoParam.getSessoes().iterator().next()
                                    .getAtributos().iterator().next().getAtributo();

        RotaRespostaAtributo item = new RotaRespostaAtributo("0", false, false, "", respostaParam, atributoParam);

        respostaParam.adicionarRespostaAtributo(item);

        try {
            respostaController.adicionarResposta(respostaParam);
        } catch (ArgumentoInvalidoException e) {
            Assert.fail();
        }

        RotaResposta returned = em.find(RotaResposta.class, (long) 1);
        Assert.assertNotNull(returned);
        Assert.assertEquals("usina1", returned.getNomeUsina());
        Assert.assertEquals("0", returned.getRespostas().iterator().next().getValor());


    }

    @Test(expected = ArgumentoInvalidoException.class)
    @Transactional
    public void naoDeveSalvarRespostaComIDNaoNulo() throws ArgumentoInvalidoException {
        RotaVersao versaoParam = auxiliarCarregarRotaVersao();

        RotaUsina usinaParam = new RotaUsina("00.000.000/0000-00", versaoParam.getRota());
        em.persist(usinaParam);

        Timestamp timeParam = new Timestamp(System.currentTimeMillis());
        RotaVersao versaoRef = new RotaVersao();
        versaoRef.setId((long) 1);

        RotaResposta respostaParam = new RotaResposta("usina1", "endereco", "1", "complemento", "bairro", "70000000", "nome", "900000000", "email@email.com", true, timeParam, usinaParam, versaoRef);

        // ID nao nulo
        respostaParam.setId(1);

        RotaAtributo atributoParam = versaoParam.getSessoes().iterator().next()
                                    .getAtributos().iterator().next().getAtributo();

        RotaRespostaAtributo item = new RotaRespostaAtributo("0", false, false, "", respostaParam, atributoParam);

        respostaParam.adicionarRespostaAtributo(item);

        respostaController.adicionarResposta(respostaParam);
    }


    @Test
    @Transactional
    public void deveRecuperarRespostaAtivaPorUsina() {

        RotaVersao versaoParam = auxiliarCarregarRotaVersao();

        RotaUsina usinaParam = new RotaUsina("00.000.000/0000-00", versaoParam.getRota());
        em.persist(usinaParam);

        Timestamp timeParam = new Timestamp(System.currentTimeMillis());
        RotaResposta respostaParam = new RotaResposta("usina1", "endereco", "1", "complemento", "bairro", "70000000", "nome", "900000000", "email@email.com", true, timeParam, usinaParam, versaoParam);

        RotaAtributo atributoParam = versaoParam.getSessoes().iterator().next()
                .getAtributos().iterator().next().getAtributo();

        RotaRespostaAtributo item = new RotaRespostaAtributo("0", false, false, "", respostaParam, atributoParam);

        respostaParam.adicionarRespostaAtributo(item);

        em.persist(respostaParam);

        RotaResposta returned = respostaController.recuperarRespostaAtivaPorUsina(usinaParam.getId());
        Assert.assertEquals(respostaParam, returned);
    }



    // Métodos auxiliares

    public RotaVersao auxiliarCarregarRotaVersao() {

        Rota rota = new Rota("etanol");
        em.persist(rota);

        RotaVersaoSituacao situacao = new RotaVersaoSituacao(1, "atual");
        em.persist(situacao);

        RotaVersao versao = new RotaVersao(rota, situacao, 1);

        RotaSessao sessao1 = new RotaSessao(versao, "sessao2", 2);

        AtributoTipoDado tipo = new AtributoTipoDado("numerico");
        AtributoTipoDado tipo2 = new AtributoTipoDado("selecionavel");

        RotaAtributo atributo = new RotaAtributo("atributo1", "blabla",
                "", "ATR1", 0, 0, "", tipo2);

        atributo.adicionarItem(new RotaAtributoItem("item1", atributo));
        sessao1.adicionarAtributo(atributo,"item1");

        versao.adicionarSessao(sessao1);
        em.persist(versao);

        return versao;
    }
}
