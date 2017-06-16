
package br.com.agsolutio.wschecklist.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.agsolutio.wschecklist.model.AppointmentBook;

/**
 * Classe que executa os métodos de busca na entidade {@link AppointmentBook}
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 *
 *@since 15/06/2017
 */
@ApplicationScoped
public class AppointmentBookRepository {

    @Inject
    private EntityManager em;

    public AppointmentBook findById(Long id) {
        return em.find(AppointmentBook.class, id);
    }

    public AppointmentBook findByConsultantAsId(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AppointmentBook> criteria = cb.createQuery(AppointmentBook.class);
        Root<AppointmentBook> appointment = criteria.from(AppointmentBook.class);
        criteria.select(appointment).where(cb.equal(appointment.get("cosnsultant").get("idConsultalt"), id));
        return em.createQuery(criteria).getSingleResult();
    }

    public AppointmentBook findByConsultantAsMatricula(String matricula) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AppointmentBook> criteria = cb.createQuery(AppointmentBook.class);
        Root<AppointmentBook> appointment = criteria.from(AppointmentBook.class);
        criteria.select(appointment).where(cb.equal(appointment.get("cosnsultant").get("matricula"), matricula));
        return em.createQuery(criteria).getSingleResult();
    }
    
    public AppointmentBook findByAppontamentAsConsultant(Long idConsultant) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AppointmentBook> criteria = cb.createQuery(AppointmentBook.class);
        Root<AppointmentBook> appointment = criteria.from(AppointmentBook.class);
        criteria.select(appointment).where(cb.equal(appointment.get("cosnsultant").get("idConsultalt"), idConsultant));
        return em.createQuery(criteria).getSingleResult();
    }
    
    public List<AppointmentBook>  findAllAppointments() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AppointmentBook> criteria = cb.createQuery(AppointmentBook.class);
        Root<AppointmentBook> appointment = criteria.from(AppointmentBook.class);
        criteria.select(appointment).orderBy(cb.asc(appointment.get("cosnsultant").get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    
}
