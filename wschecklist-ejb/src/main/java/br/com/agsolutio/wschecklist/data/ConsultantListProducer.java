
package br.com.agsolutio.wschecklist.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.agsolutio.wschecklist.model.Consultant;
/**
 * Classe com metodo que produzem as lsitagens do consultores
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 *
 *@since 15/06/2017
 */
@RequestScoped
public class ConsultantListProducer {

    @Inject
    private ConsultantRepository consultantRepository;

    private List<Consultant> consultants;

    // @Named provides access the return value via the EL variable name "consultants" in the UI (e.g.,
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Consultant> getConsultants() {
        return consultants;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Consultant consultant) {
        retrieveAllMembersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
    	consultants = consultantRepository.findAllOrderedByName();
    }
}
