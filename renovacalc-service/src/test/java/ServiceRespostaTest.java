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
        Rota rotaParam = new Rota("etanol");
        rotaParam.setId(1);
        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao("atual");
        situacaoParam.setCodigo(1);
        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam, 1);
        versaoParam.setId(1);

        RotaUsina usinaParam = new RotaUsina("00.000.000/0000-00", rotaParam);
        Timestamp timeParam = new Timestamp(System.currentTimeMillis());

        RotaResposta respostaParam = new RotaResposta("usina1", "endereco", "1", "complemento", "bairro", "70000000", "nome", "900000000", "email@email.com", true, timeParam, usinaParam, versaoParam);

        RotaResposta respostaExpected = new RotaResposta("usina1", "endereco", "1", "complemento", "bairro", "70000000", "nome", "900000000", "email@email.com", true, timeParam, usinaParam, versaoParam);
        respostaExpected.setId(1);
        when(rotaRespostaDAO.save(respostaParam)).thenReturn(respostaExpected);

        RotaResposta returned = respostaService.salvarResposta(respostaParam);
        Assert.assertEquals(respostaExpected, returned);

    }

    public void deveEncontrarPorUsina() {
        Rota rotaParam = new Rota("etanol");
        rotaParam.setId(1);
        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(1, "atual");
        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam, 1);
        versaoParam.setId(1);

        RotaUsina usinaParam = new RotaUsina("00.000.000/0000-00", rotaParam);
        Timestamp timeParam = new Timestamp(System.currentTimeMillis());

        RotaResposta respostaExpected = new RotaResposta("usina1", "endereco", "1", "complemento", "bairro", "70000000", "nome", "900000000", "email@email.com", true, timeParam, usinaParam, versaoParam);
        respostaExpected.setId(1);

        when(rotaRespostaDAO.recuperarRespostaAtivaPorUsinaID(usinaParam.getId())).thenReturn(respostaExpected);

        RotaResposta returned = respostaService.recuperarRespostaAtivaPorUsinaID(usinaParam.getId());
        Assert.assertEquals(respostaExpected, returned);
    }
}
