
package br.com.agsolutio.wschecklist.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.agsolutio.wschecklist.model.Ask;
import br.com.agsolutio.wschecklist.model.Consultant;
/**
 * Classe que executa as peristencia dos objetos {@link Consultant} na base de dados;
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 *
 */
// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ConsultantRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Consultant> consultantEventSrc;

    public void register(Consultant consultant) throws Exception {
        log.info("Registering " + consultant.getNome());
        em.merge(consultant);
        consultantEventSrc.fire(consultant);
    }
    
    public void delete(Consultant consultant){
        log.info("Delete " + consultant.getNome());
        em.remove(em.getReference(Consultant.class, consultant.getIdConsultant()));
        consultantEventSrc.fire(consultant);
    }
    
    public void deleteById(Long id){
        log.info("Deleting id:" + id);
        em.remove(em.getReference(Consultant.class, id));
    }
}
