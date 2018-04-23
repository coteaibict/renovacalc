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
import br.gov.anp.renovacalc.models.RotaResposta;
import br.gov.anp.renovacalc.service.RespostaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@ComponentScan("br.gov.anp.renovacalc")
@RestController
@RequestMapping("/api/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

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
    public void adicionarResposta(@RequestBody RotaResposta resposta) throws ArgumentoInvalidoException {
        logger.trace("Entrando em adicionarResposta()");

        if (resposta.getId() != 0) {
            throw new ArgumentoInvalidoException("ID_NAO_ZERO");
        }
        resposta = respostaService.salvarResposta(resposta);

        logger.info("Objeto RotaResposta de id " + resposta.getId() + " persistido");

        logger.trace("Saindo de adicionarResposta()");
    }
}
