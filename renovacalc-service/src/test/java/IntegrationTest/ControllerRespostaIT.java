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
import br.gov.anp.renovacalc.exception.DependenciasCiclicasException;
import br.gov.anp.renovacalc.exception.InputObrigatorioException;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.script.ScriptException;

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
    public void deveAvaliarResposta() {

        // Preparando a resposta a ser enviada para o método
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setCodigo('1');
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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");
        em.persist(tipoAtributo);

        // Declarando os atributos input

        RotaAtributo atributoParamInput4 = new RotaAtributo();
        atributoParamInput4.setTag("ATR4");
        atributoParamInput4.setTipo(tipoAtributo);

        RotaAtributo atributoParamInput5 = new RotaAtributo();
        atributoParamInput5.setTag("ATR5");
        atributoParamInput5.setTipo(tipoAtributo);

        // Declarando os atributos calculados


        RotaAtributo atributoParamCalc1 = new RotaAtributo();
        atributoParamCalc1.setTipo(tipoAtributo);
        atributoParamCalc1.setTag("ATR1");
        atributoParamCalc1.setFormula("ATR2 + ATR3");

        RotaAtributo atributoParamCalc2 = new RotaAtributo();
        atributoParamCalc2.setTipo(tipoAtributo);
        atributoParamCalc2.setTag("ATR2");
        atributoParamCalc2.setFormula("ATR4 + ATR5");

        RotaAtributo atributoParamCalc3 = new RotaAtributo();
        atributoParamCalc3.setTipo(tipoAtributo);
        atributoParamCalc3.setTag("ATR3");
        atributoParamCalc3.setFormula("ATR4 * ATR5");

        sessaoParam.adicionarAtributo(atributoParamInput4, "0");
        sessaoParam.adicionarAtributo(atributoParamInput5, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc1, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc2, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc3, "0");


        versaoParam.adicionarSessao(sessaoParam);
        em.persist(versaoParam);

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(versaoParam.getRota());

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


        RotaAtributoResposta respostaATR4 = new RotaAtributoResposta();
        respostaATR4.setValor("1");
        respostaATR4.setAtributo(atributoParamInput4);
        respostaParam.adicionarRespostaAtributo(respostaATR4);


        RotaAtributoResposta respostaATR5 = new RotaAtributoResposta();
        respostaATR5.setValor("2");
        respostaATR5.setAtributo(atributoParamInput5);
        respostaParam.adicionarRespostaAtributo(respostaATR5);


        // Preparando o retorno esperado

        RotaResposta respostaExpected = new RotaResposta();
        respostaExpected.setNomeUsina("usina1");
        respostaExpected.setEndereco("endereco");
        respostaExpected.setEnderecoNumero("1");
        respostaExpected.setEnderecoComplemento("complemento");
        respostaExpected.setEnderecoBairro("bairro");
        respostaExpected.setEnderecoCEP("70000000");
        respostaExpected.setNomeContato("nome");
        respostaExpected.setTelefoneContato("900000000");
        respostaExpected.setEmailContato("email@email.com");
        respostaExpected.setAtivo(true);
        respostaExpected.setDataEnvio(timeParam);
        respostaExpected.setUsina(usinaParam);
        respostaExpected.setVersao(versaoParam);


        // Adicionando resultados esperados na resposta esperada

        RotaAtributoResposta respostaATR1_expected = new RotaAtributoResposta();
        respostaATR1_expected.setValor("5");
        respostaATR1_expected.setAtributo(atributoParamCalc1);
        respostaExpected.adicionarRespostaAtributo(respostaATR1_expected);

        RotaAtributoResposta respostaATR2_expected = new RotaAtributoResposta();
        respostaATR2_expected.setValor("3");
        respostaATR2_expected.setAtributo(atributoParamCalc2);
        respostaExpected.adicionarRespostaAtributo(respostaATR2_expected);

        RotaAtributoResposta respostaATR3_expected = new RotaAtributoResposta();
        respostaATR3_expected.setValor("2");
        respostaATR3_expected.setAtributo(atributoParamCalc3);
        respostaExpected.adicionarRespostaAtributo(respostaATR3_expected);

        RotaAtributoResposta respostaATR4_expected = new RotaAtributoResposta();
        respostaATR4_expected.setValor("1");
        respostaATR4_expected.setAtributo(atributoParamInput4);
        respostaExpected.adicionarRespostaAtributo(respostaATR4_expected);

        RotaAtributoResposta respostaATR5_expected = new RotaAtributoResposta();
        respostaATR5_expected.setValor("2");
        respostaATR5_expected.setAtributo(atributoParamInput5);
        respostaExpected.adicionarRespostaAtributo(respostaATR5_expected);

        // Testando o método avaliarResposta

        RotaResposta retornado = null;
        try {
            retornado = respostaController.calcularSimulacao(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        } catch (InputObrigatorioException e) {
            Assert.fail("Exceção de InputObrigatório não esperada!");
        }

        Assert.assertEquals(respostaExpected, retornado);

    }

    @Test(expected = DependenciasCiclicasException.class )
    @Transactional
    public void deveLancarExcecaoDependenciaCiclica() throws DependenciasCiclicasException{

        // Preparando a resposta a ser enviada para o método
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setCodigo('1');
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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");
        em.persist(tipoAtributo);

        // Declarando os atributos input

        RotaAtributo atributoParamInput3 = new RotaAtributo();
        atributoParamInput3.setTag("ATR3");
        atributoParamInput3.setTipo(tipoAtributo);

        // Declarando os atributos calculados


        RotaAtributo atributoParamCalc1 = new RotaAtributo();
        atributoParamCalc1.setTipo(tipoAtributo);
        atributoParamCalc1.setTag("ATR1");
        atributoParamCalc1.setFormula("ATR2 + ATR3");

        RotaAtributo atributoParamCalc2 = new RotaAtributo();
        atributoParamCalc2.setTipo(tipoAtributo);
        atributoParamCalc2.setTag("ATR2");
        atributoParamCalc2.setFormula("ATR1 * ATR3");


        sessaoParam.adicionarAtributo(atributoParamInput3, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc1, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc2, "0");


        versaoParam.adicionarSessao(sessaoParam);
        em.persist(versaoParam);

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(versaoParam.getRota());

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


        RotaAtributoResposta respostaATR3 = new RotaAtributoResposta();
        respostaATR3.setValor("1");
        respostaATR3.setAtributo(atributoParamInput3);
        respostaParam.adicionarRespostaAtributo(respostaATR3);

        // Testando o método avaliarResposta

        RotaResposta retornado = null;
        try {
            retornado = respostaController.calcularSimulacao(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (InputObrigatorioException e) {
            Assert.fail("Exceção de InputObrigatório não esperada!");
        }

    }

    @Test(expected = InputObrigatorioException.class)
    @Transactional
    public void deveLancarExcecaoInputObrigatorio() throws InputObrigatorioException {

        // Preparando a resposta a ser enviada para o método
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setCodigo('1');
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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");
        em.persist(tipoAtributo);

        // Declarando os atributos input

        RotaAtributo atributoParamInput4 = new RotaAtributo();
        atributoParamInput4.setTag("ATR4");
        atributoParamInput4.setTipo(tipoAtributo);

        RotaAtributo atributoParamInput5 = new RotaAtributo();
        atributoParamInput5.setTag("ATR5");
        atributoParamInput5.setTipo(tipoAtributo);

        // Declarando os atributos calculados


        RotaAtributo atributoParamCalc1 = new RotaAtributo();
        atributoParamCalc1.setTipo(tipoAtributo);
        atributoParamCalc1.setTag("ATR1");
        atributoParamCalc1.setFormula("ATR2 + ATR3");

        RotaAtributo atributoParamCalc2 = new RotaAtributo();
        atributoParamCalc2.setTipo(tipoAtributo);
        atributoParamCalc2.setTag("ATR2");
        atributoParamCalc2.setFormula("ATR4 + ATR5");

        RotaAtributo atributoParamCalc3 = new RotaAtributo();
        atributoParamCalc3.setTipo(tipoAtributo);
        atributoParamCalc3.setTag("ATR3");
        atributoParamCalc3.setFormula("ATR4 * ATR5");

        sessaoParam.adicionarAtributo(atributoParamInput4, "0");
        sessaoParam.adicionarAtributo(atributoParamInput5, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc1, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc2, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc3, "0");


        versaoParam.adicionarSessao(sessaoParam);
        em.persist(versaoParam);

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(versaoParam.getRota());

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


        RotaAtributoResposta respostaATR4 = new RotaAtributoResposta();
        respostaATR4.setValor("1");
        respostaATR4.setAtributo(atributoParamInput4);
        respostaParam.adicionarRespostaAtributo(respostaATR4);

        /* Resposta para ATR5 faltando! */



        // Testando o método avaliarResposta

        RotaResposta retornado = null;
        try {
            retornado = respostaController.calcularSimulacao(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        }

    }

    @Test(expected = InputObrigatorioException.class)
    @Transactional
    public void deveLancarExcecaoInputObrigatorioEmStringVazia() throws InputObrigatorioException {

        // Preparando a resposta a ser enviada para o método
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setCodigo('1');
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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");
        em.persist(tipoAtributo);

        // Declarando os atributos input

        RotaAtributo atributoParamInput4 = new RotaAtributo();
        atributoParamInput4.setTag("ATR4");
        atributoParamInput4.setTipo(tipoAtributo);

        RotaAtributo atributoParamInput5 = new RotaAtributo();
        atributoParamInput5.setTag("ATR5");
        atributoParamInput5.setTipo(tipoAtributo);

        // Declarando os atributos calculados


        RotaAtributo atributoParamCalc1 = new RotaAtributo();
        atributoParamCalc1.setTipo(tipoAtributo);
        atributoParamCalc1.setTag("ATR1");
        atributoParamCalc1.setFormula("ATR2 + ATR3");

        RotaAtributo atributoParamCalc2 = new RotaAtributo();
        atributoParamCalc2.setTipo(tipoAtributo);
        atributoParamCalc2.setTag("ATR2");
        atributoParamCalc2.setFormula("ATR4 + ATR5");

        RotaAtributo atributoParamCalc3 = new RotaAtributo();
        atributoParamCalc3.setTipo(tipoAtributo);
        atributoParamCalc3.setTag("ATR3");
        atributoParamCalc3.setFormula("ATR4 * ATR5");

        sessaoParam.adicionarAtributo(atributoParamInput4, "0");
        sessaoParam.adicionarAtributo(atributoParamInput5, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc1, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc2, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc3, "0");


        versaoParam.adicionarSessao(sessaoParam);
        em.persist(versaoParam);

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(versaoParam.getRota());

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


        RotaAtributoResposta respostaATR4 = new RotaAtributoResposta();
        respostaATR4.setValor("1");
        respostaATR4.setAtributo(atributoParamInput4);
        respostaParam.adicionarRespostaAtributo(respostaATR4);

        RotaAtributoResposta respostaATR5 = new RotaAtributoResposta();
        respostaATR5.setValor("");
        respostaATR5.setAtributo(atributoParamInput5);
        respostaParam.adicionarRespostaAtributo(respostaATR5);


        // Testando o método avaliarResposta

        try {
            respostaController.calcularSimulacao(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        }

    }

}
