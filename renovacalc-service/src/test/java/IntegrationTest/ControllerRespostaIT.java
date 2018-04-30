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

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(versaoParam.getRota());
        em.persist(usinaParam);

        Timestamp timeParam = new Timestamp(System.currentTimeMillis());

        RotaResposta respostaParam = new RotaResposta();
        respostaParam.setNomeUsina("usina1");
        respostaParam.setEndereco("endereco");
        respostaParam.setEnderecoNumero("1");
        respostaParam.setEnderecoComplemento("complemento");
        respostaParam.setEnderecoBairro("bairro");
        respostaParam.setEnderecoCEP("70000000");
        respostaParam.setNomeContato("nome");
        respostaParam.setTelefoneContato("900000000");
        respostaParam.setEmailContato("email@email.com");
        respostaParam.setAtivo(true);
        respostaParam.setDataEnvio(timeParam);
        respostaParam.setUsina(usinaParam);
        respostaParam.setVersao(versaoParam);

        RotaAtributo atributoParam = versaoParam.getSessoes().iterator().next()
                .getAtributos().iterator().next().getAtributo();

        RotaAtributoResposta item = new RotaAtributoResposta();
        item.setValor("0");
        item.setAvaliacaoANP(false);
        item.setObservacaoNaoConformidade("");
        item.setResposta(respostaParam);
        item.setAtributo(atributoParam);

        respostaParam.adicionarRespostaAtributo(item);

        try {
            respostaController.adicionarResposta(respostaParam);
        } catch (ArgumentoInvalidoException e) {
            Assert.fail();
        }

        RotaResposta retornado = em.find(RotaResposta.class, (long) 1);
        Assert.assertNotNull(retornado);
        Assert.assertEquals("usina1", retornado.getNomeUsina());
        Assert.assertEquals("0", retornado.getRespostas().iterator().next().getValor());
        Assert.assertEquals("nome", retornado.getNomeContato());


    }

    @Test(expected = ArgumentoInvalidoException.class)
    @Transactional
    public void naoDeveSalvarRespostaComIDNaoNulo() throws ArgumentoInvalidoException {
        RotaVersao versaoParam = auxiliarCarregarRotaVersao();

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(versaoParam.getRota());
        em.persist(usinaParam);

        Timestamp timeParam = new Timestamp(System.currentTimeMillis());

        RotaResposta respostaParam = new RotaResposta();
        respostaParam.setNomeUsina("usina1");
        respostaParam.setEndereco("endereco");
        respostaParam.setEnderecoNumero("1");
        respostaParam.setEnderecoComplemento("complemento");
        respostaParam.setEnderecoBairro("bairro");
        respostaParam.setEnderecoCEP("70000000");
        respostaParam.setNomeContato("nome");
        respostaParam.setTelefoneContato("900000000");
        respostaParam.setEmailContato("email@email.com");
        respostaParam.setAtivo(true);
        respostaParam.setDataEnvio(timeParam);
        respostaParam.setUsina(usinaParam);
        respostaParam.setVersao(versaoParam);

        // ID nao nulo
        respostaParam.setId(1);


        RotaAtributo atributoParam = versaoParam.getSessoes().iterator().next()
                                    .getAtributos().iterator().next().getAtributo();

        RotaAtributoResposta item = new RotaAtributoResposta();
        item.setValor("0");
        item.setAvaliacaoANP(false);
        item.setObservacaoNaoConformidade("");
        item.setResposta(respostaParam);
        item.setAtributo(atributoParam);

        respostaParam.adicionarRespostaAtributo(item);

        respostaController.adicionarResposta(respostaParam);
    }


    @Test
    @Transactional
    public void deveRecuperarRespostaAtivaPorUsina() {
        RotaVersao versaoParam = auxiliarCarregarRotaVersao();

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(versaoParam.getRota());
        em.persist(usinaParam);

        Timestamp timeParam = new Timestamp(System.currentTimeMillis());

        RotaResposta respostaParam = new RotaResposta();
        respostaParam.setNomeUsina("usina1");
        respostaParam.setEndereco("endereco");
        respostaParam.setEnderecoNumero("1");
        respostaParam.setEnderecoComplemento("complemento");
        respostaParam.setEnderecoBairro("bairro");
        respostaParam.setEnderecoCEP("70000000");
        respostaParam.setNomeContato("nome");
        respostaParam.setTelefoneContato("900000000");
        respostaParam.setEmailContato("email@email.com");
        respostaParam.setAtivo(true);
        respostaParam.setDataEnvio(timeParam);
        respostaParam.setUsina(usinaParam);
        respostaParam.setVersao(versaoParam);

        RotaAtributo atributoParam = versaoParam.getSessoes().iterator().next()
                .getAtributos().iterator().next().getAtributo();

        RotaAtributoResposta item = new RotaAtributoResposta();
        item.setValor("0");
        item.setAvaliacaoANP(false);
        item.setObservacaoNaoConformidade("");
        item.setResposta(respostaParam);
        item.setAtributo(atributoParam);

        respostaParam.adicionarRespostaAtributo(item);
        em.persist(respostaParam);

        RotaResposta retornado = respostaController.recuperarRespostaAtivaPorUsina(usinaParam.getId());
        Assert.assertEquals(respostaParam, retornado);
    }



    // Métodos auxiliares

    public RotaVersao auxiliarCarregarRotaVersao() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setCodigo(1);
        situacaoParam.setDescricao("atual");
        em.persist(situacaoParam);

        RotaVersao versaoParam = new RotaVersao();
        versaoParam.setRota(rotaParam);
        versaoParam.setSituacao(situacaoParam);
        versaoParam.setNumVersao(1);


        RotaSessao sessaoParam = new RotaSessao();
        sessaoParam.setRotaVersao(versaoParam);
        sessaoParam.setDescricao("sessao2");
        sessaoParam.setOrdem(2);
        sessaoParam.setResultado(false);


        AtributoTipoDado tipoParam = new AtributoTipoDado();
        tipoParam.setCodigo(2);
        tipoParam.setDescricao("selectionavel");
        em.persist(tipoParam);

        RotaAtributo atributoParam = new RotaAtributo();
        atributoParam.setNome("atributo1");
        atributoParam.setDescricao("blabla");
        atributoParam.setUnidadeMedida("");
        atributoParam.setTag("ATR1");
        atributoParam.setTamanho(0);
        atributoParam.setPrecisao(0);
        atributoParam.setFormula("");
        atributoParam.setPeso(0);
        atributoParam.setPrincipal(false);
        atributoParam.setTipo(tipoParam);


        RotaAtributoItem itemParam = new RotaAtributoItem();
        itemParam.setDescricao("item1");
        itemParam.setAtributo(atributoParam);


        sessaoParam.adicionarAtributo(atributoParam ,"item1");

        versaoParam.adicionarSessao(sessaoParam);
        em.persist(versaoParam);

        return versaoParam;
    }
}
