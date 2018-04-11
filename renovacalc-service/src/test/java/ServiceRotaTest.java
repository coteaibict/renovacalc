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
        Rota rotaParam = new Rota(0, "etanol");
        Rota rotaExpected = new Rota(1, "etanol");
        when(rotaDAO.save(rotaParam)).thenReturn(rotaExpected);

        Rota returned = rotaService.salvar(rotaParam);
        Assert.assertEquals(rotaExpected, returned);
    }

    @Test
    public void deveSalvarVersaoDeRota() throws RecursoNaoEncontradoException {
        Rota rotaParam = new Rota(1, "etanol");
        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(1, "atual");
        RotaVersao rotaVersaoExpected = new RotaVersao(rotaParam, situacaoParam, 1);

        when(rotaVersaoDAO.save(new RotaVersao(rotaParam, situacaoParam, 1))).thenReturn(rotaVersaoExpected);

        RotaVersao returned = rotaService.salvarVersao(new RotaVersao(rotaParam, situacaoParam, 1));
        Assert.assertEquals(rotaVersaoExpected, returned);
    }

    @Test
    public void deveEncontrarRotaPorID() {
        Rota rotaExpected = new Rota(1, "etanol");
        when(rotaDAO.findOne((long) 1)).thenReturn(rotaExpected);

        Rota returned = rotaService.encontrarPorID(1);
        Assert.assertEquals(rotaExpected, returned);
    }

    @Test
    public void deveEncontrarVersoesDeRotaPorIDDaRota() {
        Rota rota = new Rota(1, "etanol");
        RotaVersaoSituacao situacao = new RotaVersaoSituacao(1, "atual");
        List<RotaVersao> versaoListExpected = Arrays
                .asList(new RotaVersao(1, rota, situacao, 1), new RotaVersao(2, rota, situacao, 2),
                        new RotaVersao(3, rota, situacao, 3));

        when(rotaVersaoDAO.findByRotaId((long) 1)).thenReturn(versaoListExpected);

        List<RotaVersao> returned = rotaService.encontrarVersoesPorRotaID(1);

        Assert.assertEquals(versaoListExpected, returned);
    }

    @Test
    public void deveEncontrarRotaPorNome() {
        List<Rota> rotaExpectedList = Arrays.asList(new Rota(1, "etanol"));
        when(rotaDAO.findByNomeLike("et")).thenReturn(rotaExpectedList);

        List<Rota> returned = rotaService.encontrarPorNome("et");
        Assert.assertEquals(rotaExpectedList, returned);
    }
}
