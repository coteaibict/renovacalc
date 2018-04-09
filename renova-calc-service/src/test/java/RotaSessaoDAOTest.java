/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: RotaSessaoDAOTest
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

import br.gov.anp.renovacalc.dao.RotaSessaoDAO;
import br.gov.anp.renovacalc.models.Rota;
import br.gov.anp.renovacalc.models.RotaSessao;
import br.gov.anp.renovacalc.models.RotaVersao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/testContext.xml" })
@WebAppConfiguration(value = "")
public class RotaSessaoDAOTest {

    @Autowired
    private RotaSessaoDAO rotaSessaoDAO;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    public void deveRetornarSessoesPorRotaEVersao() {
    }
}
