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
        Rota rotaParam = new Rota();
        rotaParam.setNome("etanol");
        em.persist(rotaParam);

        RotaVersaoSituacao situacaoParam = new RotaVersaoSituacao();
        situacaoParam.setCodigo(1);
        situacaoParam.setDescricao("atual");
        em.persist(situacaoParam);

        RotaVersao versaoParam = new RotaVersao();
        versaoParam.setRota(rotaParam);
        versaoParam.setSituacao(situacaoParam);
        versaoParam.setNumVersao(1);


        RotaSessao sessaoParam = new RotaSessao();
        sessaoParam.setRotaVersao(versaoParam);
        sessaoParam.setDescricao("sessao2");
        sessaoParam.setOrdem(2);
        sessaoParam.setResultado(false);

        AtributoTipoDado tipoParam = new AtributoTipoDado();
        tipoParam.setCodigo(1);
        tipoParam.setDescricao("numerico");

        RotaAtributo atr = new RotaAtributo();
        atr.setNome("atributo1");
        atr.setDescricao("blabla");
        atr.setUnidadeMedida("m");
        atr.setTag("ATR1");
        atr.setTamanho(10);
        atr.setPrecisao(0);
        atr.setFormula("1+1");
        atr.setPeso(0);
        atr.setPrincipal(false);
        atr.setTipo(tipoParam);


        sessaoParam.adicionarAtributo(atr, "0");
        versaoParam.adicionarSessao(sessaoParam);

        em.persist(versaoParam);

        List<RotaAtributo> atributos = (List<RotaAtributo>) em.unwrap(Session.class)
                .createCriteria(RotaAtributo.class).list();

        Assert.assertEquals(1, atributos.size());
    }
}
