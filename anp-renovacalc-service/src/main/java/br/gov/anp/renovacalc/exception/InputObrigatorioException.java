/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 17/05/2018
 *          Arquivo: InputObrigatorioException
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.exception;

/**
 * Exceção que indica que algum valor para um atributo obrigatório não foi submetido
 * no objeto RotaResposta.
 */
public class InputObrigatorioException extends Exception {

    public InputObrigatorioException() { super(); }

    public InputObrigatorioException(String message) { super(message); }
}
