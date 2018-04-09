/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: RotaVersaoDAOTest
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

import br.gov.anp.renovacalc.dao.RotaVersaoDAO;
import br.gov.anp.renovacalc.exception.RecursoNaoEncontradoException;
import br.gov.anp.renovacalc.models.Rota;
import br.gov.anp.renovacalc.models.RotaVersao;
import br.gov.anp.renovacalc.models.RotaVersaoSituacao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/testContext.xml" })
@WebAppConfiguration(value = "")
public class RotaVersaoDAOTest {

    @Autowired
    RotaVersaoDAO rotaVersaoDAO;

    @Autowired
    private EntityManager em;



    @Test
    @Transactional
    public void deveEncontrarVersoesdeRotaPorIDDaRota() {
        Rota rotaParam = new Rota(0, "etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(0, "atual");
        em.persist(situacaoParam);

        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam, 1);
        em.persist(versaoParam);

        versaoParam = new RotaVersao(rotaParam, situacaoParam, 2);
        em.persist(versaoParam);

        em.flush();

        List<RotaVersao> returned = rotaVersaoDAO.encontrarPorRotaID(rotaParam.getId());

        Assert.assertEquals(2, returned.size());

        Assert.assertEquals(1, returned.get(0).getNumVersao());
        Assert.assertEquals(2, returned.get(1).getNumVersao());
    }

    @Test
    @Transactional
    public void naoDeveEncontrarVersoesdeRotaPorIDDaRota() {
        Rota rotaParam = new Rota(0, "etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(1, "atual");
        em.persist(situacaoParam);

        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam, 1);
        em.persist(versaoParam);

        versaoParam = new RotaVersao(rotaParam, situacaoParam, 2);
        em.persist(versaoParam);

        em.flush();

        List<RotaVersao> returned = rotaVersaoDAO.encontrarPorRotaID(-1);

        Assert.assertEquals(0, returned.size());
    }

    @Test
    @Transactional
    public void deveRetornarVersoesAtuaisDeRota() {
        Rota rotaParam = new Rota(0, "etanol");
        em.persist(rotaParam);
        RotaVersaoSituacao situacaoParam_passada = new RotaVersaoSituacao(1, "passada");
        em.persist(situacaoParam_passada);
        RotaVersaoSituacao situacaoParam_atual = new RotaVersaoSituacao(2, "atual");
        em.persist(situacaoParam_atual);

        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam_passada, 1);
        em.persist(versaoParam);
        versaoParam = new RotaVersao(rotaParam, situacaoParam_atual, 2);
        em.persist(versaoParam);

        List<RotaVersao> returned = rotaVersaoDAO
                .encontrarVersoesPorSituacaoERotaID(rotaParam.getId(), situacaoParam_atual.getId());

        Assert.assertEquals(1, returned.size());
        Assert.assertEquals(2, returned.get(0).getNumVersao());
    }

}

