/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 06/04/2018
 *         Arquivo: RecursoNaoEncontradoException
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.exception;

/**
 * Classe de exceção usada para indicar que o usuário tentou
 * acessar um recurso inexistente.
 */
public class RecursoNaoEncontradoException extends Exception {

    public RecursoNaoEncontradoException() {
        super();
    }

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
