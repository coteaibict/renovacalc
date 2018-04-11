/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 11/04/2018
 *          Arquivo: RotaSessaoTest
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

import br.gov.anp.renovacalc.models.*;
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
public class ModelRotaSessaoTest {
    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    public void deveAdicionarSessoes() {
        Rota rotaParam = new Rota(0, "etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao(0, "atual");
        em.persist(situacaoParam);

        RotaVersao versaoParam = new RotaVersao(rotaParam, situacaoParam, 1);
        RotaSessao sessao1 = new RotaSessao(versaoParam, "sessao2", 2);
        RotaAtributo atr = new RotaAtributo("atributo1", "blabla",
                "m", "ATR1", 10, 0, "1+1" );
        sessao1.adicionarAtributo(atr, "0");
        versaoParam.adicionarSessao(sessao1);

        em.persist(versaoParam);

        List<RotaAtributo> atributos = (List<RotaAtributo>) em.unwrap(Session.class)
                .createCriteria(RotaAtributo.class).list();

        Assert.assertEquals(1, atributos.size());
    }
}
