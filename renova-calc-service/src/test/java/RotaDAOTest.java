/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: RotaDAOTest
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

import br.gov.anp.renovacalc.dao.RotaDAO;
import br.gov.anp.renovacalc.exception.RecursoNaoEncontradoException;
import br.gov.anp.renovacalc.models.Rota;
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
public class RotaDAOTest {

    @Autowired
    RotaDAO rotaDAO;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    public void deveEncontrarRotaPorNomeCompleto() {
        Rota rotaParam = new Rota(0, "aaaaa");
        em.persist(rotaParam);
        rotaParam = new Rota(0, "aaaab");
        em.persist(rotaParam);

        List<Rota> returned = rotaDAO.encontrarPorNome("aaaaa");
        Assert.assertNotNull(returned);

        Assert.assertEquals(1, returned.size());
        Assert.assertEquals("aaaaa", returned.get(0).getNome());

        returned = rotaDAO.encontrarPorNome("aaaab");
        Assert.assertNotNull(returned);

        Assert.assertEquals(1, returned.size());
        Assert.assertEquals("aaaab", returned.get(0).getNome());
    }

    @Test
    @Transactional
    public void deveEncontrarRotaPorNomeParcial() {
        Rota rotaParam = new Rota(0, "aaaaa");
        em.persist(rotaParam);
        rotaParam = new Rota(0, "aaaab");
        em.persist(rotaParam);

        em.flush();

        List<Rota> returned = rotaDAO.encontrarPorNome("aa");
        Assert.assertNotNull(returned);
        Assert.assertTrue(returned.size() > 0);

        Assert.assertEquals("aaaaa", returned.get(0).getNome());
        Assert.assertEquals("aaaab", returned.get(1).getNome());
    }


}
