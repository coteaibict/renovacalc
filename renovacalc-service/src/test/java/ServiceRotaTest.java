/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaServiceTest
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

import br.gov.anp.renovacalc.dao.RotaDAO;
import br.gov.anp.renovacalc.dao.RotaVersaoDAO;
import br.gov.anp.renovacalc.exception.RecursoNaoEncontradoException;
import br.gov.anp.renovacalc.models.Rota;
import br.gov.anp.renovacalc.models.RotaVersao;
import br.gov.anp.renovacalc.models.RotaVersaoSituacao;
import br.gov.anp.renovacalc.service.RotaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/testContext.xml" })
@WebAppConfiguration(value = "")
public class ServiceRotaTest {

    private RotaService rotaService;

    @Mock
    private RotaDAO rotaDAO;
    @Mock
    private RotaVersaoDAO rotaVersaoDAO;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        Assert.assertNotNull(rotaDAO);
        Assert.assertNotNull(rotaVersaoDAO);

        rotaService = new RotaService();

        rotaService.setRotaDAO(rotaDAO);
        rotaService.setRotaVersaoDAO(rotaVersaoDAO);
    }

    @Test
    public void deveSalvarRota() {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        Rota rotaExpected = new Rota();
        rotaExpected.setNome("etanol");
        rotaExpected.setId(1);
        when(rotaDAO.save(rotaParam)).thenReturn(rotaExpected);

        Rota retornado = rotaService.salvar(rotaParam);
        Assert.assertEquals(rotaExpected, retornado);
    }

    @Test
    public void deveSalvarVersaoDeRota() throws RecursoNaoEncontradoException {
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        rotaParam.setId(1);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setCodigo(1);
        situacaoParam.setDescricao("atual");

        RotaVersao rotaVersaoExpected = new RotaVersao();
        rotaVersaoExpected.setRota(rotaParam);
        rotaVersaoExpected.setSituacao(situacaoParam);
        rotaVersaoExpected.setNumVersao(1);
        rotaVersaoExpected.setId(1);

        RotaVersao rotaVersaoParam = new RotaVersao();
        rotaVersaoParam.setRota(rotaParam);
        rotaVersaoParam.setSituacao(situacaoParam);
        rotaVersaoParam.setNumVersao(1);

        when(rotaVersaoDAO.save(rotaVersaoParam)).thenReturn(rotaVersaoExpected);

        RotaVersao retornado = rotaService.salvarVersao(rotaVersaoParam);
        Assert.assertEquals(rotaVersaoExpected, retornado);
    }

    @Test
    public void deveEncontrarRotaPorID() {
        Rota rotaExpected = new Rota();
        rotaExpected.setNome("etanol");
        rotaExpected.setId(1);
        when(rotaDAO.findOne((long) 1)).thenReturn(rotaExpected);

        Rota retornado = rotaService.encontrarPorID(1);
        Assert.assertEquals(rotaExpected, retornado);
    }

    @Test
    public void deveEncontrarVersoesDeRotaPorIDDaRota() {
        Rota rota = new Rota();
        rota.setNome("etanol");
        rota.setId(1);

        RotaVersaoSituacao situacao = new RotaVersaoSituacao();
        situacao.setCodigo(1);
        situacao.setDescricao("atual");

        List<RotaVersao> versaoListExpected = new ArrayList<>();

        RotaVersao rota_tmp = new RotaVersao();
        rota_tmp.setRota(rota);
        rota_tmp.setSituacao(situacao);
        rota_tmp.setNumVersao(1);
        rota_tmp.setId(1);
        versaoListExpected.add(rota_tmp);

        rota_tmp = new RotaVersao(rota, situacao, 2);
        rota_tmp.setRota(rota);
        rota_tmp.setSituacao(situacao);
        rota_tmp.setNumVersao(2);
        rota_tmp.setId(2);
        versaoListExpected.add(rota_tmp);

        rota_tmp = new RotaVersao(rota, situacao, 3);
        rota_tmp.setRota(rota);
        rota_tmp.setSituacao(situacao);
        rota_tmp.setNumVersao(3);
        rota_tmp.setId(3);
        versaoListExpected.add(rota_tmp);

        when(rotaVersaoDAO.findByRotaId((long) 1)).thenReturn(versaoListExpected);

        List<RotaVersao> retornado = rotaService.encontrarVersoesPorRotaID(1);

        Assert.assertEquals(versaoListExpected, retornado);
    }

    @Test
    public void deveEncontrarRotaPorNome() {
        Rota rota = new Rota();
        rota.setNome("etanol");
        rota.setId(1);

        List<Rota> rotaExpectedList = Arrays.asList(rota);
        when(rotaDAO.findByNomeLike("et")).thenReturn(rotaExpectedList);

        List<Rota> retornado = rotaService.encontrarPorNome("et");
        Assert.assertEquals(rotaExpectedList, retornado);
    }
}
