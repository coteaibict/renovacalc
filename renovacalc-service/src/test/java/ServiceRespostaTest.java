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
        situacaoParam.setCodigo(1);

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

        RotaResposta returned = respostaService.salvarResposta(respostaParam);
        Assert.assertEquals(respostaExpected, returned);

    }

    public void deveEncontrarPorUsina() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        rotaParam.setId(1);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setDescricao("atual");
        situacaoParam.setCodigo(1);

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

        RotaResposta returned = respostaService.recuperarRespostaAtivaPorUsinaID(usinaParam.getId());
        Assert.assertEquals(respostaExpected, returned);
    }
}
