/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 11/04/2018
 *          Arquivo: RotaVersaoTest
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

import br.gov.anp.renovacalc.models.Rota;
import br.gov.anp.renovacalc.models.RotaSessao;
import br.gov.anp.renovacalc.models.RotaVersao;
import br.gov.anp.renovacalc.models.RotaVersaoSituacao;
import org.hibernate.Session;
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
public class ModelRotaVersaoTest {

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    public void deveAdicionarSessoes() {
        Rota rotaParam = new Rota("etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(1, "atual");
        em.persist(situacaoParam);

        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam, 1);
        RotaSessao sessao1 = new RotaSessao(versaoParam, "sessao2", 2);
        RotaSessao sessao2 = new RotaSessao(versaoParam, "sessao1", 1);

        versaoParam.adicionarSessao(sessao1);
        versaoParam.adicionarSessao(sessao2);

        em.persist(versaoParam);


        List<RotaSessao> sessoes = (List<RotaSessao>) em.unwrap(Session.class)
                .createCriteria(RotaSessao.class).list();

        Assert.assertEquals(2, sessoes.size());
    }

}
