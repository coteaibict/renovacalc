/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 25/04/2018
 *          Arquivo: CalculoService
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.service;

import br.gov.anp.renovacalc.exception.DependenciasCiclicasException;
import br.gov.anp.renovacalc.exception.InputObrigatorioException;
import br.gov.anp.renovacalc.models.RotaAtributo;
import br.gov.anp.renovacalc.models.RotaAtributoResposta;
import br.gov.anp.renovacalc.models.RotaResposta;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * Classe responsável por avaliar uma submissão RotaResposta.
 * Assume que as fórmulas avaliadas sempre retornam um valor numérico.
 */
@Service
public class CalculoService {
    private ScriptEngineManager engineManager;

    @Autowired
    private AtributoService atributoService;

    @Autowired
    private RespostaService respostaService;

    public CalculoService() {
        this.engineManager = new ScriptEngineManager();

        // Setando variáveis que permitem acesso ao java para null
        this.engineManager.put("importPackage", null);
        this.engineManager.put("importClass", null);
        this.engineManager.put("Packages", null);
        this.engineManager.put("java", null);
        this.engineManager.put("javax", null);
        this.engineManager.put("org", null);
        this.engineManager.put("edu", null);
        this.engineManager.put("com", null);
        this.engineManager.put("net", null);
        this.engineManager.put("br", null);
    }

    /**
     * Método que avalia todos os atributos calculados de uma simulação e
     * adiciona seus resultados como RotaAtributoResposta.
     * Assume que todos os inputs obrigatórios já foram enviados pelo usuário.
     * Assume que as fórmulas avaliadas sempre retornam um valor numérico.
     * @param resposta: a simulação do usuário
     * @return a simulação com os valores calculados adicionados
     * @throws DependenciasCiclicasException: Caso haja dependência cíclica entre as fórmulas
     * @throws ScriptException: Caso haja algum erro na avaliação das fórmulas
     */
    public RotaResposta avaliarResposta(RotaResposta resposta)
            throws DependenciasCiclicasException, ScriptException, InputObrigatorioException {

        // Carrega todos os atributos calculados da rota
        Set<RotaAtributo> calculados = atributoService.recuperarCalculadosPorVersao(resposta.
                                                                    getVersao().getId());

        // Carrega todos os atributos input obrigatórios da rota e coloca as tags em um conjunto
        Set<RotaAtributo> obrigatoriosSet = atributoService.recuperarInputObrigatorioPorVersao(resposta.
                getVersao().getId());

        Set<String> tagsObrigatoriosSet = new HashSet<>();

        for (RotaAtributo atributo : obrigatoriosSet) {
            tagsObrigatoriosSet.add(atributo.getTag());
        }

        // Ordena os atributos calculados por suas dependências através de suas fórmulas
        List<RotaAtributo> ordenados = atributoService.ordernarPorDependencias(calculados);

        // Carrega entradas em um contexto para ser passado para o script
        Map<String, Object> ctx = criarContextoComInput(resposta);

        // Confere se todos os atributos obrigatórios foram submetidos
        for (RotaAtributo atributo : obrigatoriosSet ) {
            if (!ctx.containsKey(atributo.getTag()) || ctx.get(atributo.getTag()).equals("")) {
                throw new InputObrigatorioException();
            }
        }

        for ( RotaAtributo atual : ordenados ) {
            Double resultado = avaliarFormula(atual.getFormula(), ctx);
            ctx.put(atual.getTag(), resultado);

            // Adiciona na simulacao do usuario
            RotaAtributoResposta atributoResposta = new RotaAtributoResposta();
            atributoResposta.setAtributo(atual);
            atributoResposta.setValor(resultado.toString());
            atributoResposta.setObservacaoNaoConformidade("");
            resposta.adicionarRespostaAtributo(atributoResposta);
        }


        return resposta;
    }

    // Getter/Setter

    public AtributoService getAtributoService() { return atributoService; }
    public void setAtributoService(AtributoService atributoService) { this.atributoService = atributoService; }

    public RespostaService getRespostaService() { return respostaService; }
    public void setRespostaService(RespostaService respostaService) { this.respostaService = respostaService; }

    // Métodos privados

    /**
     * Coloca todos os valores dados como input pelo usuário
     * em um Map, para ser usado como contexto da avaliação das
     * fórmulas
     * @param resposta: Simulação da qual serão extraídos os valores
     *                submetidos
     * @return Contexto contendo (tag_atributo, valorInput)
     */
    private Map<String, Object> criarContextoComInput(RotaResposta resposta) {
        Set<RotaAtributoResposta> entradas = respostaService.recuperarEntradas(resposta);
        Map<String, Object> ctx = new HashMap<>();

        // Passa por cada entrada, adicionando no contexto. Faz o tratamento caso
        // seja um valor numérico
        for (RotaAtributoResposta atual : entradas) {
            if (NumberUtils.isCreatable(atual.getValor())) {
                ctx.put(atual.getAtributo().getTag(), NumberUtils.createDouble(atual.getValor()));
            } else {
                ctx.put(atual.getAtributo().getTag(), atual.getValor());
            }
        }

        return ctx;
    }

    /**
     * Método que avalia uma expressão, dado um contexto de variáveis.
     * Assume que a expressão sempre retorna um valor numérico
     * @param expr: a expressão a ser avaliada, que deve retornar um
     *            valor numérico
     * @param ctx: o contexto, isto é, um conjunto de pares chave/valor
     * @return o valor final da fórmula avaliada
     * @throws ScriptException
     */
    private Double avaliarFormula(String expr, Map<String, Object> ctx) throws ScriptException {
        ScriptEngine engine = engineManager.getEngineByName("JavaScript");

        for(Map.Entry<String, Object> pair : ctx.entrySet()) {
            engine.put(pair.getKey(), pair.getValue());
        }

        String predefined = "function SE(_cond, _res1, _res2) { return (_cond ? _res1 : _res2) }; var se = SE;";

        return (Double) engine.eval(predefined + expr);

    }
}
