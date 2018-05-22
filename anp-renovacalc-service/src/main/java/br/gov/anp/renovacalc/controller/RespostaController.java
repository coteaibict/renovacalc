/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 19/04/2018
 *          Arquivo: RespostaController
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.controller;

import br.gov.anp.renovacalc.exception.DependenciasCiclicasException;
import br.gov.anp.renovacalc.exception.InputObrigatorioException;
import br.gov.anp.renovacalc.exception.ValorInvalidoException;
import br.gov.anp.renovacalc.models.RotaResposta;
import br.gov.anp.renovacalc.service.CalculoService;
import br.gov.anp.renovacalc.service.RespostaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;

/**
 * Classe controladora que lida com as chamadas relacionadas a uma simulação (avaliação, salvar).
 *
 */
@CrossOrigin("*")
@ComponentScan("br.gov.anp.renovacalc")
@RestController
@RequestMapping("/api/respostas")
public class RespostaController {

    /**
     * Instância de Resposta service, eventualmente será utilizada para
     * recuperar e persistir simulações em banco
     */
    @Autowired
    private RespostaService respostaService;

    /**
     * Instância de CalculoService, usada para avaliar a simulação
     */
    @Autowired
    private CalculoService calculoService;

    private Logger logger = Logger.getLogger(RespostaController.class);

    /**
     * Método controlador que é chamado para avaliar uma simulação do usuário
     * @param simulacao: Objeto RotaResposta contendo a simulação do usuário.
     * @return o mesmo objeto RotaResposta, porém com os valores dos atributos
     * calculados adicionados.
     * @throws DependenciasCiclicasException: Caso a rota tenha dependência cíclica
     * entre os atributos, através de suas fórmulas (erro de cadastro de rota)
     * @throws ScriptException: Caso haja algum erro na avaliação da fórmula
     * (erro de cadastro da rota)
     * @throws InputObrigatorioException: Caso o valor de algum atributo marcado
     * como obrigatório esteja faltando.
     * @throws ValorInvalidoException quando o usuário envia um valor
     * não numérico em um campo numérico
     */
    @RequestMapping(value = "/simular", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RotaResposta calcularSimulacao(@RequestBody RotaResposta simulacao)
            throws DependenciasCiclicasException, ScriptException, InputObrigatorioException, ValorInvalidoException {
        return calculoService.avaliarResposta(simulacao);
    }

    /**
     * Método tratador de exceção que lida com exceções de erro na fórmula
     * e envia uma resposta HTTP 500
     * @param e: Uma exceção do tipo DependenciasCiclicasException ou ScriptException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ DependenciasCiclicasException.class, ScriptException.class })
    String handleErroAvaliacao(Exception e) {
        return "ERRO_NA_FORMULA";
    }

    /**
     * Método tratador de exceção que captura uma exceção InputObrigatorioException
     * e envia uma resposta HTTP 400
     * @param e: Exceção que indica que um input obrigatório não foi submetido
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ InputObrigatorioException.class })
    String handleInputObrigatorio(InputObrigatorioException e) {
        return "INPUT_OBRIGATORIO_FALTANTE";
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ValorInvalidoException.class })
    String handleValorInvalido(ValorInvalidoException e) {
        return e.getMessage();
    }

}
