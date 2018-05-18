/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 26/04/2018
 *          Arquivo: AtributoService
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.service;

import br.gov.anp.renovacalc.dao.RotaAtributoDAO;
import br.gov.anp.renovacalc.exception.DependenciasCiclicasException;
import br.gov.anp.renovacalc.models.RotaAtributo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AtributoService {

    @Autowired
    private RotaAtributoDAO atributoDAO;

    public AtributoService() { }

    /**
     * Método que retorna uma lista com os atributos passados já ordenados por dependência.
     * Assume que não há nenhum atributo calculado faltando no conjunto atributosCalculados
     * Não é necessário passar os atributos não calculados
     * @param atributosCalculados: Atributos a serem ordenados por dependencia
     * @return Lista com os atributos ordenados, onde as dependências de cada atributo
     *          aparecem antes deste.
     * @throws DependenciasCiclicasException: Caso exista uma dependência cíclica entre atributos.
     */
    public List<RotaAtributo> ordernarPorDependencias(Set<RotaAtributo> atributosCalculados)
            throws DependenciasCiclicasException {
        List<RotaAtributo> ordenados = new ArrayList<>();

        Map<String, Boolean> visitados = new HashMap<String, Boolean>();

        Map<RotaAtributo, Set<RotaAtributo>> dependencias = recuperarDependencias(atributosCalculados);

        for ( RotaAtributo atual : atributosCalculados ) {
            if( !visitados.containsKey(atual.getTag()) ) {
                visitarAtributoDuranteOrdenacao(atual, visitados, ordenados, dependencias);
            }
        }

        return ordenados;
    }

    /**
     * Abstração de serviço que recupera todos os atributos calculados de uma determinada versão
     * @param versaoID: ID da versão de interesse
     * @return Set com cada atributo calculado
     */
    public Set<RotaAtributo> recuperarCalculadosPorVersao(long versaoID) {
        return atributoDAO.recuperarCalculadosPorVersao(versaoID);
    }

    /**
     * Abstração de serviço que recupera todos os atributos input obrigatórios de uma determinada versão
     * @param versaoID: ID da versão de interesse
     * @return Set com cada atributo do tipo input que é obrigatório
     */
    public Set<RotaAtributo> recuperarInputObrigatorioPorVersao(long versaoID) {
        return atributoDAO.recuperarInputObrigatorioPorVersao(versaoID);
    }

    // Getters/Setters

    public RotaAtributoDAO getAtributoDAO() { return atributoDAO; }
    public void setAtributoDAO(RotaAtributoDAO atributoDAO) { this.atributoDAO = atributoDAO; }

    // Métodos privados

    /**
     * Método auxiliar que percorre recursivamente os atributos através de suas dependências,
     * adicionando-os na lista. O percorrimento é feito através de uma busca por profundidade.
     * Um atributo só é adicionado na lista quando suas  dependências já forem adicionadas.
     * @param atual: RotaAtributo que está sendo visitado atualmente
     * @param visitados: Map que indica se um atributo foi visitado ou não.
     *                 Caso o valor de uma chave seja FALSE, a função ainda não
     *                 terminou de passar pelo atributo.
     *                 Caso não contenha a chave, a função não começou a passar pelo atributo.
     * @param ordenados: A lista onde os atributos serão adicionados
     * @param dependencias: Map com as dependências já processadas dos atributos
     * @throws DependenciasCiclicasException: caso haja uma dependencia
     *                 ciclica entre os atributos
     */
    private void visitarAtributoDuranteOrdenacao(RotaAtributo atual, Map<String, Boolean> visitados, List<RotaAtributo> ordenados, Map<RotaAtributo, Set<RotaAtributo>> dependencias)
            throws DependenciasCiclicasException {

        if(visitados.containsKey(atual.getTag()) && visitados.get(atual.getTag()) == Boolean.TRUE) {
            return;
        }

        if(visitados.containsKey(atual.getTag()) && visitados.get(atual.getTag()) == Boolean.FALSE) {
            throw new DependenciasCiclicasException("FORMULA_CICLICA");
        }

        visitados.put(atual.getTag(), Boolean.FALSE);

        // Visita os filhos do atributo atual
        for ( RotaAtributo filho : dependencias.get(atual) ) {
            visitarAtributoDuranteOrdenacao(filho, visitados, ordenados, dependencias);
        }


        visitados.put(atual.getTag(), Boolean.TRUE);
        ordenados.add(atual);
    }

    /**
     * Constroi um map com (atributo, conjunto de filhos), onde
     * os filhos são os atributos usados na fórmula do atributo pai
     * @param atributos
     * @return Map com os atributos e suas dependencias
     */
    private Map<RotaAtributo, Set<RotaAtributo>> recuperarDependencias(Set<RotaAtributo> atributos) {
        Map<RotaAtributo, Set<RotaAtributo>> mapDependencias = new HashMap<>();

        for(Iterator<RotaAtributo> it = atributos.iterator(); it.hasNext();) {
           RotaAtributo atual = it.next();
           String formula = atual.getFormula();
           Set<RotaAtributo> atualDependencias = new HashSet<>();
           if(formula != null && !formula.isEmpty()) {

               // Pra cada atributo, confere se ele aparece na formula e adiciona como dependencia
               for(Iterator<RotaAtributo> it2 = atributos.iterator(); it2.hasNext();) {
                   RotaAtributo atualInterno = it2.next();
                   Pattern r = Pattern.compile("(?:\\b|^|$|\\s)" + atualInterno.getTag() + "(?:\\b|^|$|\\s)");
                   Matcher m = r.matcher(formula);
                   if(m.find()) {
                       atualDependencias.add(atualInterno);
                   }
               }

           }
           mapDependencias.put(atual, atualDependencias);
        }

        return mapDependencias;
    }

}
