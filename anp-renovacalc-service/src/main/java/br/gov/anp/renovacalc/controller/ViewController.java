package br.gov.anp.renovacalc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Classe controladora para redirecionar páginas para o Angular
 */
@CrossOrigin("*")
@Controller
public class ViewController {

    /**
     * Faz o redirect para o arquivo principal do Angular. Lá o tratamento é feito.
     * Qualquer caminho-raiz deve ser adicionado aqui.
     */
   @RequestMapping({"/", "/rotas/*", "/notfound", "/error"})
   public String all() {
       return "forward:/index.html";
   }
   
}
