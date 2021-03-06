/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 18/04/2018
 *          Arquivo: ServiceRespostaTest
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

import br.gov.anp.renovacalc.dao.RotaRespostaDAO;
import br.gov.anp.renovacalc.models.*;
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

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/testContext.xml" })
@WebAppConfiguration(value = "")
public class ServiceRespostaTest {

    private RespostaService respostaService;

    @Mock
    RotaRespostaDAO rotaRespostaDAO;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        Assert.assertNotNull(rotaRespostaDAO);

        respostaService = new RespostaService();

        respostaService.setRotaRespostaDAO(rotaRespostaDAO);
    }

    @Test
    public void deveSalvarResposta() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        rotaParam.setId(1);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setDescricao("atual");
        situacaoParam.setCodigo((short) 1);

        RotaVersao versaoParam = new RotaVersao();
        versaoParam.setRota(rotaParam);
        versaoParam.setSituacao(situacaoParam);
        versaoParam.setNumVersao(1);
        versaoParam.setId(1);

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(rotaParam);

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
        respostaExpected.setId(1);

        when(rotaRespostaDAO.save(respostaParam)).thenReturn(respostaExpected);

        RotaResposta retornado = respostaService.salvarResposta(respostaParam);
        Assert.assertEquals(respostaExpected, retornado);

    }

    @Test
    public void deveEncontrarPorUsina() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        rotaParam.setId(1);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setDescricao("atual");
        situacaoParam.setCodigo((short) 1);

        RotaVersao versaoParam = new RotaVersao();
        versaoParam.setRota(rotaParam);
        versaoParam.setSituacao(situacaoParam);
        versaoParam.setNumVersao(1);
        versaoParam.setId(1);

        RotaUsina usinaParam = new RotaUsina();
        usinaParam.setCnpj("00.000.000/0000-00");
        usinaParam.setRota(rotaParam);

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
        respostaExpected.setId(1);

        when(rotaRespostaDAO.recuperarRespostaAtivaPorUsinaID(usinaParam.getId())).thenReturn(respostaExpected);

        RotaResposta retornado = respostaService.recuperarRespostaAtivaPorUsinaID(usinaParam.getId());
        Assert.assertEquals(respostaExpected, retornado);
    }

    @Test
    public void deveRecuperarEntradas() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        rotaParam.setId(1);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setDescricao("atual");
        situacaoParam.setCodigo((short) 1);

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
        tipoAtributo.setCodigo((short) 1);
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

        Set<RotaAtributoResposta> esperado = new HashSet<>();
        esperado.add(respostaATR4);
        esperado.add(respostaATR5);

        Set<RotaAtributoResposta> retornado = respostaService.recuperarEntradas(respostaParam);

        Assert.assertEquals(esperado, retornado);

    }
}
