package de.iu.jakarta_jsf.persistence;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.util.List;

import static de.iu.jakarta_jsf.persistence.UserEntity.COLUMN_MAIL_ADDRESS;

/**
 * Class to access and store values of the database. (Database Tier)
 */
@Stateless
public class UserDAO {

    @PersistenceContext
    EntityManager em;

    public void persist(UserEntity userEntity) {
        em.persist(userEntity);
    }

    /**
     * Builds and uses a query to access data in the database by the given email-address.
     * If the email-address is found, return a {@link UserEntity} (which also contains the password).
     * If there is no match in the database with the given email-address, return nothing.
     */
    public UserEntity findUSerByMailAddress(String enteredEmailAddress) {

        // SELECT * FROM user WHERE mailAddress = enteredEmailAddress

        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteria.createQuery(UserEntity.class);

        Root<UserEntity> fromTableUSer = query.from(UserEntity.class);
        Path<Object> columnMailAddress = fromTableUSer.get(COLUMN_MAIL_ADDRESS);

        query.select(fromTableUSer);
        query.where(criteria.equal(columnMailAddress, enteredEmailAddress));

        TypedQuery<UserEntity> typedQuery = em.createQuery(query);

        // Execute query
        List<UserEntity> resultList = typedQuery.getResultList();

        if (resultList.isEmpty()) return null;
        else return resultList.get(0);
    }
}
