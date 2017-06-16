
package br.com.agsolutio.wschecklist.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.agsolutio.wschecklist.model.AppointmentBook;
/**
 * Classe que executa as peristencia dos objetos {@link AppointmentBook} na base de dados;
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 * @since 15/06/2017
 *
 */
// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class AppointmentBookRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<AppointmentBook> appointmentEventSrc;

    public void register(AppointmentBook appointment) throws Exception {
        log.info("Registering appontment para" + appointment.getCosnsultant().getNome());
        em.merge(appointment);
        appointmentEventSrc.fire(appointment);
    }
    
    public void delete(AppointmentBook appointment){
        log.info("Delete " + appointment.getCosnsultant().getNome());
        em.remove(em.getReference(AppointmentBook.class, appointment.getIdAppointmentBook() ));
        appointmentEventSrc.fire(appointment);
        }
    
    public void deleteById(Long id){
        log.info("Deleting id:" + id);
        em.remove(em.getReference(AppointmentBook.class, id));
    }
}
