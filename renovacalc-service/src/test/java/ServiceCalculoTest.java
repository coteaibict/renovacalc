/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 27/04/2018
 *          Arquivo: ServiceCalculoTest
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

import br.gov.anp.renovacalc.exception.DependenciasCiclicasException;
import br.gov.anp.renovacalc.exception.InputObrigatorioException;
import br.gov.anp.renovacalc.models.*;
import br.gov.anp.renovacalc.service.AtributoService;
import br.gov.anp.renovacalc.service.CalculoService;
import br.gov.anp.renovacalc.service.RespostaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.script.ScriptException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/testContext.xml" })
@WebAppConfiguration(value = "")
public class ServiceCalculoTest {

    private CalculoService calculoService;

    @Mock
    private AtributoService atributoService;

    @Mock
    private RespostaService respostaService;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);

        calculoService = new CalculoService();

        calculoService.setAtributoService(atributoService);
        calculoService.setRespostaService(respostaService);
    }

    @Test
    public void deveAvaliarResposta() {

        // Preparando a resposta a ser enviada para o método
        RotaVersao versaoParam = auxSetupRota();

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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");

        RotaAtributo atributoParamInput4 = new RotaAtributo();
        atributoParamInput4.setTag("ATR4");
        atributoParamInput4.setTipo(tipoAtributo);

        RotaAtributoResposta respostaATR4 = new RotaAtributoResposta();
        respostaATR4.setValor("1");
        respostaATR4.setAtributo(atributoParamInput4);
        respostaParam.adicionarRespostaAtributo(respostaATR4);

        RotaAtributo atributoParamInput5 = new RotaAtributo();
        atributoParamInput5.setTag("ATR5");
        atributoParamInput5.setTipo(tipoAtributo);

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

        // Declarando os atributos calculados da rota a serem retornados pelo mock


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

        // Setup mock do atributoService

        Set<RotaAtributo> calculadosMock = new HashSet<>();
        calculadosMock.add(atributoParamCalc1);
        calculadosMock.add(atributoParamCalc2);
        calculadosMock.add(atributoParamCalc3);

        Set<RotaAtributo> obrigatoriosMock = new HashSet<>();
        obrigatoriosMock.add(atributoParamInput4);
        obrigatoriosMock.add(atributoParamInput5);

        when(atributoService.recuperarCalculadosPorVersao(respostaParam.getVersao().getId())).thenReturn(calculadosMock);

        when(atributoService.recuperarInputObrigatorioPorVersao(respostaParam.getVersao().getId())).thenReturn( obrigatoriosMock);

        List<RotaAtributo> ordenadosMock = new ArrayList<>();
        ordenadosMock.add(atributoParamCalc3);
        ordenadosMock.add(atributoParamCalc2);
        ordenadosMock.add(atributoParamCalc1);

        // Setup mock de respostaService

        Set<RotaAtributoResposta> inputsMock = new HashSet<>();
        inputsMock.add(respostaATR4);
        inputsMock.add(respostaATR5);
        when(respostaService.recuperarEntradas(respostaParam)).thenReturn(inputsMock);


        try {
            when(atributoService.ordernarPorDependencias(anySet())).thenReturn(ordenadosMock);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        }


        // Testando o método avaliarResposta

        RotaResposta retornado = null;
        try {
             retornado = calculoService.avaliarResposta(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        } catch (InputObrigatorioException e) {
            Assert.fail("Exceção de InputObrigatório não esperada!");
        }

        Assert.assertEquals(respostaExpected, retornado);

    }

    @Test(expected = DependenciasCiclicasException.class)
    public void avaliarRespostaDeveLancarExcecaoDependenciaCiclica()
            throws DependenciasCiclicasException {
        // Preparando a resposta a ser enviada para o método
        RotaVersao versaoParam = auxSetupRotaInconsistente();

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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");

        RotaAtributo atributoParamInput3 = new RotaAtributo();
        atributoParamInput3.setTag("ATR3");
        atributoParamInput3.setTipo(tipoAtributo);

        RotaAtributoResposta respostaATR4 = new RotaAtributoResposta();
        respostaATR4.setValor("1");
        respostaATR4.setAtributo(atributoParamInput3);
        respostaParam.adicionarRespostaAtributo(respostaATR4);


        RotaAtributo atributoParamCalc1 = new RotaAtributo();
        atributoParamCalc1.setTipo(tipoAtributo);
        atributoParamCalc1.setTag("ATR1");
        atributoParamCalc1.setFormula("ATR2 + ATR3");

        RotaAtributo atributoParamCalc2 = new RotaAtributo();
        atributoParamCalc2.setTipo(tipoAtributo);
        atributoParamCalc2.setTag("ATR2");
        atributoParamCalc2.setFormula("ATR1 + ATR3");

        Set<RotaAtributo> calculadosMock = new HashSet<>();
        calculadosMock.add(atributoParamCalc1);
        calculadosMock.add(atributoParamCalc2);

        when(atributoService.recuperarCalculadosPorVersao(respostaParam.getVersao().getId())).thenReturn(calculadosMock);

        Set<RotaAtributo> obrigatoriosMock = new HashSet<>();
        obrigatoriosMock.add(atributoParamInput3);

        when(atributoService.recuperarInputObrigatorioPorVersao(respostaParam.getVersao().getId())).thenReturn( obrigatoriosMock);

        when(atributoService.ordernarPorDependencias(anySet())).thenThrow(DependenciasCiclicasException.class);

        // Testando o método avaliarResposta

        RotaResposta retornado = null;
        try {
            retornado = calculoService.avaliarResposta(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (InputObrigatorioException e) {
            Assert.fail("Excecao de InputObrigatorio não esperada!");
        }

    }

    @Test(expected = InputObrigatorioException.class)
    public void avaliarRespostaDeveLancarInputObrigatorioException() throws InputObrigatorioException {

        // Preparando a resposta a ser enviada para o método
        RotaVersao versaoParam = auxSetupRota();

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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");

        RotaAtributo atributoParamInput4 = new RotaAtributo();
        atributoParamInput4.setTag("ATR4");
        atributoParamInput4.setTipo(tipoAtributo);

        RotaAtributoResposta respostaATR4 = new RotaAtributoResposta();
        respostaATR4.setValor("1");
        respostaATR4.setAtributo(atributoParamInput4);
        respostaParam.adicionarRespostaAtributo(respostaATR4);

        RotaAtributo atributoParamInput5 = new RotaAtributo();
        atributoParamInput5.setTag("ATR5");
        atributoParamInput5.setTipo(tipoAtributo);

        /* Resposta ATR5 faltando! */


        // Declarando os atributos calculados da rota a serem retornados pelo mock


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

        // Setup mock do atributoService

        Set<RotaAtributo> calculadosMock = new HashSet<>();
        calculadosMock.add(atributoParamCalc1);
        calculadosMock.add(atributoParamCalc2);
        calculadosMock.add(atributoParamCalc3);

        Set<RotaAtributo> obrigatoriosMock = new HashSet<>();
        obrigatoriosMock.add(atributoParamInput4);
        obrigatoriosMock.add(atributoParamInput5);

        when(atributoService.recuperarCalculadosPorVersao(respostaParam.getVersao().getId())).thenReturn(calculadosMock);

        when(atributoService.recuperarInputObrigatorioPorVersao(respostaParam.getVersao().getId())).thenReturn(obrigatoriosMock);

        List<RotaAtributo> ordenadosMock = new ArrayList<>();
        ordenadosMock.add(atributoParamCalc3);
        ordenadosMock.add(atributoParamCalc2);
        ordenadosMock.add(atributoParamCalc1);

        // Setup mock de respostaService

        Set<RotaAtributoResposta> inputsMock = new HashSet<>();
        inputsMock.add(respostaATR4);
        when(respostaService.recuperarEntradas(respostaParam)).thenReturn(inputsMock);


        try {
            when(atributoService.ordernarPorDependencias(anySet())).thenReturn(ordenadosMock);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        }


        // Testando o método avaliarResposta

        RotaResposta retornado = null;
        try {
            retornado = calculoService.avaliarResposta(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        }

    }

    @Test(expected = InputObrigatorioException.class)
    public void avaliarRespostaDeveLancarInputObrigatorioExceptionEmStringVazia() throws InputObrigatorioException {

        // Preparando a resposta a ser enviada para o método
        RotaVersao versaoParam = auxSetupRota();

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

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");

        RotaAtributo atributoParamInput4 = new RotaAtributo();
        atributoParamInput4.setTag("ATR4");
        atributoParamInput4.setTipo(tipoAtributo);

        RotaAtributoResposta respostaATR4 = new RotaAtributoResposta();
        respostaATR4.setValor("1");
        respostaATR4.setAtributo(atributoParamInput4);
        respostaParam.adicionarRespostaAtributo(respostaATR4);

        RotaAtributo atributoParamInput5 = new RotaAtributo();
        atributoParamInput5.setTag("ATR5");
        atributoParamInput5.setTipo(tipoAtributo);

        /* Resposta ATR5 vazia! */
        RotaAtributoResposta respostaATR5 = new RotaAtributoResposta();
        respostaATR5.setValor("");
        respostaATR5.setAtributo(atributoParamInput5);
        respostaParam.adicionarRespostaAtributo(respostaATR5);



        // Declarando os atributos calculados da rota a serem retornados pelo mock


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

        // Setup mock do atributoService

        Set<RotaAtributo> calculadosMock = new HashSet<>();
        calculadosMock.add(atributoParamCalc1);
        calculadosMock.add(atributoParamCalc2);
        calculadosMock.add(atributoParamCalc3);

        Set<RotaAtributo> obrigatoriosMock = new HashSet<>();
        obrigatoriosMock.add(atributoParamInput4);
        obrigatoriosMock.add(atributoParamInput5);

        when(atributoService.recuperarCalculadosPorVersao(respostaParam.getVersao().getId())).thenReturn(calculadosMock);

        when(atributoService.recuperarInputObrigatorioPorVersao(respostaParam.getVersao().getId())).thenReturn(obrigatoriosMock);

        List<RotaAtributo> ordenadosMock = new ArrayList<>();
        ordenadosMock.add(atributoParamCalc3);
        ordenadosMock.add(atributoParamCalc2);
        ordenadosMock.add(atributoParamCalc1);

        // Setup mock de respostaService

        Set<RotaAtributoResposta> inputsMock = new HashSet<>();
        inputsMock.add(respostaATR4);
        inputsMock.add(respostaATR5);
        when(respostaService.recuperarEntradas(respostaParam)).thenReturn(inputsMock);


        try {
            when(atributoService.ordernarPorDependencias(anySet())).thenReturn(ordenadosMock);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        }


        // Testando o método avaliarResposta

        try {
            calculoService.avaliarResposta(respostaParam);
        } catch (ScriptException e) {
            Assert.fail("Erro na avaliação do script: " + e);
        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada: " + e);
        }

    }

    // Métodos auxiliares

    private RotaVersao auxSetupRota() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        rotaParam.setId(1);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setDescricao("atual");
        situacaoParam.setCodigo('1');

        RotaVersao versaoParam = new RotaVersao();
        versaoParam.setRota(rotaParam);
        versaoParam.setSituacao(situacaoParam);
        versaoParam.setNumVersao(1);
        versaoParam.setId(1);

        RotaSessao sessaoParam = new RotaSessao();
        sessaoParam.setOrdem(1);
        sessaoParam.setResultado(false);
        sessaoParam.setRotaVersao(versaoParam);

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");


        // Definindo atributos
        // A formula final fica: ATR1 = (ATR4 + ATR5) + (ATR4 * ATR5)

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

        RotaAtributo atributoParamInput4 = new RotaAtributo();
        atributoParamInput4.setTipo(tipoAtributo);
        atributoParamInput4.setTag("ATR4");

        RotaAtributo atributoParamInput5 = new RotaAtributo();
        atributoParamInput5.setTipo(tipoAtributo);
        atributoParamInput5.setTag("ATR5");

        sessaoParam.adicionarAtributo(atributoParamCalc1, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc2, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc3, "0");
        sessaoParam.adicionarAtributo(atributoParamInput4, "0");
        sessaoParam.adicionarAtributo(atributoParamInput5, "0");

        return versaoParam;
    }

    private RotaVersao auxSetupRotaInconsistente() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        rotaParam.setId(1);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setDescricao("atual");
        situacaoParam.setCodigo('1');

        RotaVersao versaoParam = new RotaVersao();
        versaoParam.setRota(rotaParam);
        versaoParam.setSituacao(situacaoParam);
        versaoParam.setNumVersao(1);
        versaoParam.setId(1);

        RotaSessao sessaoParam = new RotaSessao();
        sessaoParam.setOrdem(1);
        sessaoParam.setResultado(false);
        sessaoParam.setRotaVersao(versaoParam);

        AtributoTipoDado tipoAtributo = new AtributoTipoDado();
        tipoAtributo.setCodigo('1');
        tipoAtributo.setDescricao("numerico");


        // Definindo atributos
        // A formula final fica inconsistente, pois ATR1 e ATR2 dependem um do outro

        RotaAtributo atributoParamCalc1 = new RotaAtributo();
        atributoParamCalc1.setTipo(tipoAtributo);
        atributoParamCalc1.setTag("ATR1");
        atributoParamCalc1.setFormula("ATR2 + ATR3");

        RotaAtributo atributoParamCalc2 = new RotaAtributo();
        atributoParamCalc2.setTipo(tipoAtributo);
        atributoParamCalc2.setTag("ATR2");
        atributoParamCalc2.setFormula("ATR1 + ATR3");


        RotaAtributo atributoParamCalc3 = new RotaAtributo();
        atributoParamCalc3.setTipo(tipoAtributo);
        atributoParamCalc3.setTag("ATR3");

        sessaoParam.adicionarAtributo(atributoParamCalc1, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc2, "0");
        sessaoParam.adicionarAtributo(atributoParamCalc3, "0");

        return versaoParam;
    }
}
