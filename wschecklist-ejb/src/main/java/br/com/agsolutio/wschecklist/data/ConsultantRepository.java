
package br.com.agsolutio.wschecklist.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.agsolutio.wschecklist.model.Consultant;
import br.com.agsolutio.wschecklist.model.Sector;

/**
 * Classe que executa os métodos de busca na entidade {@link Consultant}
 * 
 * @author "Alcélio Gomes {@link doalcelio@gmail.com}"
 *
 *@since 15/06/2017
 */
@ApplicationScoped
public class ConsultantRepository {

    @Inject
    private EntityManager em;

    public Consultant findById(Long id) {
        return em.find(Consultant.class, id);
    }

    public Consultant findByMatricula(String matricula) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Consultant> criteria = cb.createQuery(Consultant.class);
        Root<Consultant> consultant = criteria.from(Consultant.class);
        criteria.select(consultant).where(cb.equal(consultant.get("matricula"), matricula));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Consultant> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Consultant> criteria = cb.createQuery(Consultant.class);
        Root<Consultant> consultant = criteria.from(Consultant.class);
        criteria.select(consultant).orderBy(cb.asc(consultant.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Consultant findBySector(Sector sector) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Consultant> criteria = cb.createQuery(Consultant.class);
        Root<Consultant> consultant = criteria.from(Consultant.class);
        criteria.select(consultant).where(cb.equal(consultant.get("setor").get("nomeSetor"), sector.getNomeSetor()));
        return em.createQuery(criteria).getSingleResult();
    }
}
