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

import br.gov.anp.renovacalc.exception.ArgumentoInvalidoException;
import br.gov.anp.renovacalc.exception.RecursoNaoEncontradoException;
import br.gov.anp.renovacalc.models.Rota;
import br.gov.anp.renovacalc.models.RotaVersao;
import br.gov.anp.renovacalc.models.RotaVersaoSituacao;
import br.gov.anp.renovacalc.service.RotaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@ComponentScan("br.gov.anp.renovacalc")
@RestController
@RequestMapping("/api/rotas")
public class RotaController {
    @Autowired
    private RotaService rotaService;

    private Logger logger = Logger.getLogger(RotaController.class);

    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public void setup() throws RecursoNaoEncontradoException {
        rotaService.setup();
    }

    @RequestMapping(value = "/tst", method = RequestMethod.GET)
    public void del() {
        rotaService.tst();
    }

    /**
     * Método controlador que é chamado para adicionar uma nova rota.
     * Associado ao método POST em /rotas
     * Não deve ser usado para modificar uma rota já existente
     * Recebe a rota através de JSON
     * Caso a rota possua um ID não zero, retorna o identificador
     * "ID_NAO_ZERO" em uma resposta HTTP 400
     * @throws ArgumentoInvalidoException: quando a rota já possui um ID
     * não zero.
     * @param rota
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void adicionarRota(@RequestBody Rota rota) throws ArgumentoInvalidoException {
        logger.trace("Entrando em adicionarRota()");

        if (rota.getId() != 0) {
            throw new ArgumentoInvalidoException("ID_NAO_ZERO");
        }
        rota = rotaService.salvar(rota);

        logger.info("Objeto Rota de id " + rota.getId() + " persistido");

        logger.trace("Saindo de adicionarRota()");
    }

    @RequestMapping(value = "/{rotaID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RotaVersao recuperarVersaoAtualDeRota(@PathVariable("rotaID") long rotaID) {
        return rotaService.recuperarVersaoAtualDeRota(rotaID);
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

    /**
     * Método controlador para adicionar uma nova versão de uma rota
     * Associado ao método POST em /{rotaID}/versoes/
     * Caso a rota não exista, retorna o identificador ROTA_NAO_ENCONTRADA
     * em uma resposta HTTP 404
     * Caso a situacao não exista, retorna o identificador
     * SITUACAO_NAO_ENCONTRADA em uma resposta HTTP 404
     * Caso o objeto RotaVersao possua um campo de identificação de rota
     * com valor diferente de {rotaID}, retorna o identificador ROTA_VERSAO_INVALIDA
     * em uma resposta HTTP 400
     * @throws ArgumentoInvalidoException: quando a rota identificada em versao é
     * inconsistente com {rotaID}
     * @throws RecursoNaoEncontradoException : quando a rota em {rotaID}
     * não existe em banco
     * @param rotaId
     * @param versao
     */
    @RequestMapping(value = "/{rotaId}/versoes/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void adicionarVersao(@PathVariable("rotaId") long rotaId, @RequestBody RotaVersao versao)
            throws RecursoNaoEncontradoException, ArgumentoInvalidoException {
        logger.trace("Entrando em adicionarVersao()");

        // Confere se a rota no objeto RotaVersao possui ID compativel com rotaId
        if (versao.getRota() != null && versao.getRota().getId() != rotaId) {
            throw new ArgumentoInvalidoException("VERSAO_ROTA_INVALIDA");
        }

        if (versao.getSituacao() == null) {
            throw new ArgumentoInvalidoException("VERSAO_SITUACAO_NULA");
        }

        // Confere se a rota e situacao passadas já estão persistidas
        Rota rota = rotaService.encontrarPorID(rotaId);
        if (rota == null) {
            throw new RecursoNaoEncontradoException("ROTA_NAO_ENCONTRADA");
        }

        RotaVersaoSituacao situacao = rotaService.encontrarSituacaoPorId(versao.getSituacao().getCodigo());
        if (situacao == null) {
            throw new RecursoNaoEncontradoException("ROTA_NAO_ENCONTRADA");
        }

        versao.setId(0);
        versao.setRota(rota);
        versao.setSituacao(situacao);
        versao = rotaService.salvarVersao(versao);

        logger.info("Objeto RotaVersao de id " + versao.getId() + " persistido");

        logger.trace("Saindo de adicionarVersao()");
    }

    /**
     * Método controlador para retornar todas as rotas cujo nome contém :nome
     * Associado ao método GET em /rotas/search
     * @param nome
     * @return Lista de rotas cujo nome contém :nome
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rota> encontrarPorNome(@RequestParam(value = "nome", required = true) String nome) {
        logger.trace("Entrando em encontrarPorNome()");

        List<Rota> lp = rotaService.encontrarPorNome(nome);

        logger.trace("Saindo de encontrarPorNome()");
        return lp;
    }

    // Tratadores de exceções

    /**
     * Método tratador de exceção que captura uma exceção ArgumentoInvalidoException
     * e envia uma resposta HTTP 400
     * @param e
     * @param response
     * @throws IOException
     */
    @ExceptionHandler({ ArgumentoInvalidoException.class })
    void handleRequisicaoMalFormada(ArgumentoInvalidoException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * Método tratador de exceções que captura uma exceção RecursoNaoEncontradoException
     * e retorna uma resposta HTTP 404
     * @param e
     * @param response
     * @throws IOException
     */
    @ExceptionHandler({ RecursoNaoEncontradoException.class })
    void handleRecursoNaoEncontrado(RecursoNaoEncontradoException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }


}
