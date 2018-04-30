/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 27/04/2018
 *          Arquivo: ServiceAtributoTest
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

import br.gov.anp.renovacalc.dao.RotaAtributoDAO;
import br.gov.anp.renovacalc.exception.DependenciasCiclicasException;
import br.gov.anp.renovacalc.models.AtributoTipoDado;
import br.gov.anp.renovacalc.models.RotaAtributo;
import br.gov.anp.renovacalc.service.AtributoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/testContext.xml" })
@WebAppConfiguration(value = "")
public class ServiceAtributoTest {

    private AtributoService atributoService;

    @Mock
    private RotaAtributoDAO atributoDAO;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);

        atributoService = new AtributoService();

        atributoService.setAtributoDAO(atributoDAO);
    }

    @Test
    public void deveOrdenarPorDependencias() {

        AtributoTipoDado tipo = new AtributoTipoDado();
        tipo.setCodigo(1);
        tipo.setDescricao("numerico");

        RotaAtributo atr1 = new RotaAtributo();
        atr1.setTag("ATR1");
        atr1.setFormula("ATR2 + ATR3");
        atr1.setTipo(tipo);

        RotaAtributo atr2 = new RotaAtributo();
        atr2.setTag("ATR2");
        atr2.setFormula("ATR4");
        atr2.setTipo(tipo);

        RotaAtributo atr3 = new RotaAtributo();
        atr3.setTag("ATR3");
        atr3.setFormula("ATR4");
        atr3.setTipo(tipo);

        Set<RotaAtributo> atributosCalculados = new HashSet<>();
        atributosCalculados.add(atr1);
        atributosCalculados.add(atr2);
        atributosCalculados.add(atr3);
        // Não deve ser necessário adicionar os atributos não calculados

        try {
            List<RotaAtributo> ordenados = atributoService.ordernarPorDependencias(atributosCalculados);

            Iterator<RotaAtributo> it = ordenados.iterator();
            RotaAtributo atual;


            if (!it.hasNext()) {
                Assert.fail("Menos atributos do que esperado");
            }
            atual = it.next();

            // Confere se os dois próximos são ATR2 e ATR3. A ordem não importa.
            if(atual.equals(atr2)) {
                if (!it.hasNext()) {
                    Assert.fail("Menos atributos do que esperado");
                }
                atual = it.next();
                if (!atual.equals(atr3)) {
                    Assert.fail("Atributo não esperado: " + atual.getTag());
                }
            } else if(atual.equals(atr3)) {
                if (!it.hasNext()) {
                    Assert.fail("Menos atributos do que esperado");
                }
                atual = it.next();
                if (!atual.equals(atr2)) {
                    Assert.fail("Atributo não esperado: " + atual.getTag());
                }
            } else {
                Assert.fail("Atributo não esperado: " + atual.getTag());
            }

            if (!it.hasNext()) {
                Assert.fail("Menos atributos do que esperado");
            }
            atual = it.next();
            Assert.assertEquals("Atributo nao experado:" + atual.getTag(), atr1, atual);

            if (it.hasNext()) {
                Assert.fail("Atributos demais!");
            }

        } catch (DependenciasCiclicasException e) {
            Assert.fail("Exceção de dependência cíclica não esperada");
        }

    }

    @Test(expected = DependenciasCiclicasException.class)
    public void deveLancarExcecaoDeDependenciaCiclicaAoOrdenar() throws DependenciasCiclicasException {
        AtributoTipoDado tipo = new AtributoTipoDado();
        tipo.setCodigo(1);
        tipo.setDescricao("numerico");

        RotaAtributo atr1 = new RotaAtributo();
        atr1.setTag("ATR1");
        atr1.setFormula("ATR2");
        atr1.setTipo(tipo);

        RotaAtributo atr2 = new RotaAtributo();
        atr2.setTag("ATR2");
        atr2.setFormula("ATR3");
        atr2.setTipo(tipo);

        RotaAtributo atr3 = new RotaAtributo();
        atr3.setTag("ATR3");
        atr3.setFormula("ATR1");
        atr3.setTipo(tipo);

        Set<RotaAtributo> atributosCalculados = new HashSet<>();
        atributosCalculados.add(atr1);
        atributosCalculados.add(atr2);
        atributosCalculados.add(atr3);

        atributoService.ordernarPorDependencias(atributosCalculados);

    }

}
