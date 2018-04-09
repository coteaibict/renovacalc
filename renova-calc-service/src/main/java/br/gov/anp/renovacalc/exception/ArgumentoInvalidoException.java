/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc
 * Data de criação: 06/04/2018
 *         Arquivo: ArgumentoInvalidoException
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.exception;

public class ArgumentoInvalidoException extends Exception {

    public ArgumentoInvalidoException() {
        super();
    }

    public ArgumentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}

