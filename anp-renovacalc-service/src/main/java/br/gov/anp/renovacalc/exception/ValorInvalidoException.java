/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 21/05/2018
 *          Arquivo: ValorInvalidoException
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.exception;

public class ValorInvalidoException extends Exception {

    public ValorInvalidoException(String message) {
        super(message);
    }

    public ValorInvalidoException() {
    }
}
