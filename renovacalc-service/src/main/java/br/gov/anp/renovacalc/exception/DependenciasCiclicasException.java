/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 26/04/2018
 *          Arquivo: DependenciasCiclicasException
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.exception;

public class DependenciasCiclicasException extends Exception {

    public DependenciasCiclicasException() {
        super();
    }

    public DependenciasCiclicasException(String mensagem) {
        super(mensagem);
    }

}
