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

import br.gov.anp.renovacalc.exception.ArgumentoInvalidoException;
import br.gov.anp.renovacalc.exception.DependenciasCiclicasException;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin("*")
@ComponentScan("br.gov.anp.renovacalc")
@RestController
@RequestMapping("/api/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @Autowired
    private CalculoService calculoService;

    private Logger logger = Logger.getLogger(RespostaController.class);

    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public void setup() {
        respostaService.setup();
    }

    @RequestMapping(value = "/recuperar", method = RequestMethod.POST)
    public RotaResposta recuperarRespostaAtivaPorUsina(@RequestParam("usinaID") long usinaID) {
        return respostaService.recuperarRespostaAtivaPorUsinaID(usinaID);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RotaResposta adicionarResposta(@RequestBody RotaResposta resposta) throws ArgumentoInvalidoException, DependenciasCiclicasException, ScriptException {
        logger.trace("Entrando em adicionarResposta()");

        if (resposta.getId() != 0) {
            throw new ArgumentoInvalidoException("ID_NAO_ZERO");
        }
        RotaResposta calculada = calculoService.avaliarResposta(resposta);
        calculada = respostaService.salvarResposta(calculada);

        logger.info("Objeto RotaResposta de id " + calculada.getId() + " persistido");

        logger.trace("Saindo de adicionarResposta()");

        return calculada;
    }

    @RequestMapping(value = "/simular", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RotaResposta calcularSimulacao(@RequestBody RotaResposta simulacao) throws DependenciasCiclicasException,
            ScriptException {
        return calculoService.avaliarResposta(simulacao);
    }

    /**
     * Método tratador de exceção lida com exceções de erro na fórmula
     * @param e: Uma exceção do tipo DependenciasCiclicasException ou ScriptException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ DependenciasCiclicasException.class, ScriptException.class })
    String handleErroAvaliacao(Exception e) {
        return "ERRO_NA_FORMULA";
    }

    /**
     * Método tratador de exceção que captura uma exceção ArgumentoInvalidoException
     * e envia uma resposta HTTP 400
     * @param e: Exceção que poussui uma mensagem interna com código do motivo
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ArgumentoInvalidoException.class })
    String handleRecursoNaoEncontrado(ArgumentoInvalidoException e) {
        return e.getMessage();
    }
}
