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

/**
 * Exceção que indica que uma rota possui dependência cíclica entre seus atributos,
 * através de suas fórmulas.
 * Exemplo: ATR1 = ATR2 + 2
 *          ATR2 = ATR1 * 2
 * Neste caso, a exceção deve ser lançada.
 */
public class DependenciasCiclicasException extends Exception {

    public DependenciasCiclicasException() {
        super();
    }

    public DependenciasCiclicasException(String mensagem) {
        super(mensagem);
    }

}
