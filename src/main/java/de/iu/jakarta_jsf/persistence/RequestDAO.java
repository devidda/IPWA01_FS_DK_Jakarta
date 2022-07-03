package de.iu.jakarta_jsf.persistence;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.util.List;

import static de.iu.jakarta_jsf.persistence.RequestEntity.COLUMN_AUTHOR_NAME;

/**
 * DAO for accessing, modifying and storing the RequestEntities
 * which are used by the management.xhtml page for displaying the requests of users.
 */
@Stateless
public class RequestDAO {

    @PersistenceContext
    EntityManager em;

    public void persist(RequestEntity r) {
        em.persist(r);
    }

    public void merge(RequestEntity r) {
        em.merge(r);
    }

    /**
     * Returns all requests of the user specified by the given emailAddress.
     */
    public List<RequestEntity> findRequestsByMailAddress(String emailAddress) {

        // SELECT * FROM Request WHERE authorName = emailAddress
        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<RequestEntity> query = criteria.createQuery(RequestEntity.class);

        Root<RequestEntity> fromTableRequest = query.from(RequestEntity.class);
        Path<Object> columnMailAddress = fromTableRequest.get(COLUMN_AUTHOR_NAME);

        query.select(fromTableRequest);
        query.where(criteria.equal(columnMailAddress, emailAddress));

        TypedQuery<RequestEntity> typedQuery = em.createQuery(query);

        // Execute query
        List<RequestEntity> resultList = typedQuery.getResultList();


        if (resultList.isEmpty()) return null;
        else return resultList;
    }

    /**
     * Returns all requests (used for a logged in admin).
     */
    public List<RequestEntity> getAllRequests() {

        // SELECT * FROM Request
        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<RequestEntity> query = criteria.createQuery(RequestEntity.class);

        Root<RequestEntity> fromTableRequest = query.from(RequestEntity.class);

        query.select(fromTableRequest);

        TypedQuery<RequestEntity> typedQuery = em.createQuery(query);

        // Execute query
        List<RequestEntity> resultList = typedQuery.getResultList();

        if (resultList.isEmpty()) return null;
        else return resultList;
    }
}
