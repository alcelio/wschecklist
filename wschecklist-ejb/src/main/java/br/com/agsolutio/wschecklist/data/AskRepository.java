package br.com.agsolutio.wschecklist.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.agsolutio.wschecklist.model.Ask;

@ApplicationScoped
public class AskRepository {

    @Inject
    private EntityManager em;

    public Ask findById(Long id) {
        return em.find(Ask.class, id);
    }


    public List<Ask> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ask> criteria = cb.createQuery(Ask.class);
        Root<Ask> ask = criteria.from(Ask.class);
        criteria.select(ask);
        return em.createQuery(criteria).getResultList();
    }
}
