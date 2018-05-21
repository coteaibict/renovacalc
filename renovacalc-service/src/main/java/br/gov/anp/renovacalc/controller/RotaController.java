/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RotaController
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.controller;

import br.gov.anp.renovacalc.exception.RecursoNaoEncontradoException;
import br.gov.anp.renovacalc.models.Rota;
import br.gov.anp.renovacalc.models.RotaVersao;
import br.gov.anp.renovacalc.service.RotaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe controladora que lida com as chamadas relacionadas a uma rota
 * (salvar, recuperar, recuperar versão).
 */
@CrossOrigin("*")
@ComponentScan("br.gov.anp.renovacalc")
@RestController
@RequestMapping("/api/rotas")
public class RotaController {

    /**
     * Instância de RotaService, usada para recuperar e eventualmente persistir
     * rotas e versões de rotas
     */
    @Autowired
    private RotaService rotaService;

    private Logger logger = Logger.getLogger(RotaController.class);

    /**
     * Método controlador que é chamado para recuperar todas as rotas.
     * Associado ao método GET em /
     * @return Iterable com cada rota cadastrada
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Rota> recuperarRotas() {
        return rotaService.recuperarRotas();
    }

    /**
     * Método controlador que é chamado para recuperar a rota em sua versão atual.
     * Associado ao método GET em /rotas/:id
     * Caso a rota não exista ou não possua versão associada, retorna o identificador
     * "ID_NAO_ZERO" em uma resposta HTTP 404
     * @throws RecursoNaoEncontradoException quando a rota não existe ou não possui versão associada
     * @param rotaID o id da rota desejada
     */
    @RequestMapping(value = "/{rotaID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RotaVersao recuperarVersaoAtualDeRota(@PathVariable("rotaID") long rotaID) throws RecursoNaoEncontradoException {
        RotaVersao versao = rotaService.recuperarVersaoAtualDeRota(rotaID);
        if (versao == null) {
            throw new RecursoNaoEncontradoException("ROTA_NAO_ENCONTRADA");
        }
        return versao;
    }

    /**
     * Método controlador que é chamado para retornar todas as versões de uma rota.
     * Associado ao método GET em /{rotaID}/versoes
     * Recebe o ID da rota através do parametro {rotaID} na URL
     * Caso a rota não exista, retorna apenas uma lista vazia
     * @param rotaID
     * @return Lista de versões da rota de ID rotaID
     */
    @RequestMapping(value = "/{rotaID}/versoes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RotaVersao> recuperarVersoes(@PathVariable("rotaID") long rotaID) {
        logger.trace("Entrando em recuperarVersoes()");

        List<RotaVersao> versoes = rotaService.encontrarVersoesPorRotaID(rotaID);

        logger.trace("Saindo de recuperarVersoes()");

        return versoes;
    }

    // Tratadores de exceções


    /**
     * Método tratador de exceções que captura uma exceção RecursoNaoEncontradoException
     * e retorna uma resposta HTTP 404
     * @param e: Exceção que possui uma mensagem interna com código do motivo
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ RecursoNaoEncontradoException.class })
    String handleRecursoNaoEncontrado(RecursoNaoEncontradoException e) {
        return e.getMessage();
    }


}
