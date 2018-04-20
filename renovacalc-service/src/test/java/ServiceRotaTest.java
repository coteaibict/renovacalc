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
        Rota rotaParam = new Rota("etanol");
        Rota rotaExpected = new Rota("etanol");
        rotaExpected.setId(1);
        when(rotaDAO.save(rotaParam)).thenReturn(rotaExpected);

        Rota returned = rotaService.salvar(rotaParam);
        Assert.assertEquals(rotaExpected, returned);
    }

    @Test
    public void deveSalvarVersaoDeRota() throws RecursoNaoEncontradoException {
        Rota rotaParam = new Rota("etanol");
        rotaParam.setId(1);
        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(1, "atual");
        RotaVersao rotaVersaoExpected = new RotaVersao(rotaParam, situacaoParam, 1);
        rotaVersaoExpected.setId(1);

        when(rotaVersaoDAO.save(new RotaVersao(rotaParam, situacaoParam, 1))).thenReturn(rotaVersaoExpected);

        RotaVersao returned = rotaService.salvarVersao(new RotaVersao(rotaParam, situacaoParam, 1));
        Assert.assertEquals(rotaVersaoExpected, returned);
    }

    @Test
    public void deveEncontrarRotaPorID() {
        Rota rotaExpected = new Rota("etanol");
        rotaExpected.setId(1);
        when(rotaDAO.findOne((long) 1)).thenReturn(rotaExpected);

        Rota returned = rotaService.encontrarPorID(1);
        Assert.assertEquals(rotaExpected, returned);
    }

    @Test
    public void deveEncontrarVersoesDeRotaPorIDDaRota() {
        Rota rota = new Rota("etanol");
        rota.setId(1);
        RotaVersaoSituacao situacao = new RotaVersaoSituacao("atual");
        situacao.setCodigo(1);

        List<RotaVersao> versaoListExpected = new ArrayList<>();

        RotaVersao rota_tmp = new RotaVersao(rota, situacao, 1);
        rota_tmp.setId(1);
        versaoListExpected.add(rota_tmp);

        rota_tmp = new RotaVersao(rota, situacao, 2);
        rota_tmp.setId(2);
        versaoListExpected.add(rota_tmp);

        rota_tmp = new RotaVersao(rota, situacao, 3);
        rota_tmp.setId(3);
        versaoListExpected.add(rota_tmp);

        when(rotaVersaoDAO.findByRotaId((long) 1)).thenReturn(versaoListExpected);

        List<RotaVersao> returned = rotaService.encontrarVersoesPorRotaID(1);

        Assert.assertEquals(versaoListExpected, returned);
    }

    @Test
    public void deveEncontrarRotaPorNome() {
        Rota rota = new Rota("etanol");
        rota.setId(1);
        List<Rota> rotaExpectedList = Arrays.asList(rota);
        when(rotaDAO.findByNomeLike("et")).thenReturn(rotaExpectedList);

        List<Rota> returned = rotaService.encontrarPorNome("et");
        Assert.assertEquals(rotaExpectedList, returned);
    }
}
