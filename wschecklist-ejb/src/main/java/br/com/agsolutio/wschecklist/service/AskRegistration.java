
package br.com.agsolutio.wschecklist.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.agsolutio.wschecklist.model.Ask;
/**
 * Classe que executa a persistencia da entidade {@link Ask} no banco de dados
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 *
 *@since 15/06/2017
 */
// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class AskRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Ask> askEventSrc;

    public void register(Ask ask) throws Exception {
        log.info("Registering " + ask.getIdAsk());
        em.merge(ask);
        askEventSrc.fire(ask);
    }
    
    public void delete(Ask ask){
        log.info("Delete " + ask.getIdAsk());
        em.remove(em.getReference(Ask.class, ask.getIdAsk()));
        askEventSrc.fire(ask);
    }
    
    public void deleteById(Long id){
        log.info("Deleting id:" + id);
        em.remove(em.getReference(Ask.class, id));
    }
}
