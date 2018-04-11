/*
 *
 * ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 * ----------------------------------------------------------------
 *         Projeto: renovacalc-service
 * Data de criação: 09/04/2018
 *         Arquivo: GenericDao
 *           Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 * ----------------------------------------------------------------
 *
 *
 */

package br.gov.anp.renovacalc.dao;

import br.gov.anp.renovacalc.exception.RecursoNaoEncontradoException;
import br.gov.anp.renovacalc.models.IIdentificavel;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base de DAO que provê métodos básicos para CRUD
 * A classe que instancia deve sempre especificar entidadeClass
 * através de super(entidadeClass)
 * @param <T> a classe da Entidade a ser gerenciada. Esta deve
 *           implementar IIdentificavel
 */
public abstract class GenericDAO<T extends IIdentificavel> {

    @Autowired
    protected EntityManager em;

    private Class<T> entidadeClass;

    protected Logger logger;

    protected GenericDAO() {
       logger = Logger.getLogger("Default logger");
       logger.warn("Instance class de GenericDAO não foi especificada");
    }

    protected GenericDAO(Class<T> entidadeClass) {
        this.entidadeClass = entidadeClass;
        logger = Logger.getLogger(entidadeClass.getSimpleName() + "DAO");
    }


    /**
     * Método para salvar uma entidade
     * @param entidade
     * @return T com o ID gerado pelo DB
     */
    public T salvar(T entidade) {
        logger.trace("Persistindo " + entidadeClass.getSimpleName());
        em.persist(entidade);
        return entidade;
    }

    /**
     * Método para atualizar uma entidade existente
     * @param entidade
     * @throws RecursoNaoEncontradoException : quando entidade não existe
     * no banco
     * @return T atualizada
     */
    public T atualizar(T entidade) throws RecursoNaoEncontradoException {
        if (em.find(entidadeClass, entidade.getId()) == null) {
            throw new RecursoNaoEncontradoException("ENTIDADE_NAO_ENCONTRADA");
        } else {
            entidade = em.merge(entidade);
        }
        return entidade;
    }

    /**
     * Método para remover uma entidade persistida
     * @param entidade
     */
    public void remover(T entidade) {
        logger.trace("Removendo " + entidadeClass.getSimpleName());
        em.remove(entidade);
    }

    /**
     * Recupera entidade por ID
     * @param id
     * @return T com o ID pedido ou NULL caso não exista
     */
    public T encontrarPorID(long id) {
        return em.find(entidadeClass, id);
    }

    /**
     * Recupera todas as rotas em banco
     * @return Lista de rotas
     */
    @SuppressWarnings("unchecked")
    public List<T> encontrarTodas() {
        return (List<T>) em.unwrap(Session.class).createCriteria(entidadeClass).list();
    }
}
