package de.iu.jakarta_jsf.persistence;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for accessing, modifying and storing the MainCO2DataEntities
 * which are used by the data.xhtml page for displaying the data
 */
@Stateless
public class MainCo2DataDAO {

    @PersistenceContext
    EntityManager em;

    public void merge(MainCo2DataEntity dataEntity) {
        em.merge(dataEntity);
    }

    public void persist(MainCo2DataEntity dataEntity) {
        em.persist(dataEntity);
    }

    /**
     * Returns the latest MainCo2DataEntity for each individual country in the database.
     */
    public List<MainCo2DataEntity> getAllLatestData() {

        Query query = em.createQuery("SELECT DISTINCT d.country FROM MainCo2DataEntity d");

        List<String> results = query.getResultList();
        List<MainCo2DataEntity> resultList = new ArrayList<>();


        for (String specificCountry : results) {
            Query query2 = em.createQuery("SELECT m FROM MainCo2DataEntity m WHERE country LIKE '" + specificCountry + "' AND year = (SELECT MAX(year) FROM MainCo2DataEntity WHERE country LIKE '" + specificCountry + "')");
            resultList.addAll(query2.getResultList());
        }
        if (resultList.isEmpty()) return null;
        else return resultList;
    }

    /**
     * First this method checks if there already is a MainCo2DataEntry in the database
     * with the year and country like the given RequestedValueEntity. If there isn't, a new MainCo2DataEntry
     * with the attributes of the given RequestedValueEntity will be created. If there is, the emission attribute
     * of the found MainCo2DataEntry will be updated with the emission-attribute of the RequestedValueEntity.
     */
    public void saveOrUpdate(RequestedValueEntity requestedValue) {

        if (requestedValue.getCountry() == null) return;

        // SELECT * FROM main WHERE country = requestedValue.country AND year = requestedValue.year

        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<MainCo2DataEntity> query = criteria.createQuery(MainCo2DataEntity.class);

        Root<MainCo2DataEntity> root = query.from(MainCo2DataEntity.class);

        Predicate countryParam = criteria.like(root.get("country"), requestedValue.getCountry());
        Predicate yearParam = criteria.equal(root.get("year"), requestedValue.getYear());

        query.select(root);
        query.where(criteria.and(countryParam, yearParam));

        TypedQuery<MainCo2DataEntity> typedQuery = em.createQuery(query);

        // Execute query
        List<MainCo2DataEntity> resultList = typedQuery.getResultList();

        if (resultList.isEmpty()) {
            try {
                MainCo2DataEntity dataEntity = new MainCo2DataEntity();
                dataEntity.setCountry(requestedValue.getCountry());
                dataEntity.setYear(requestedValue.getYear());
                dataEntity.setEmission(requestedValue.getEmission());

                this.persist(dataEntity);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            if (resultList.get(0).getEmission() != requestedValue.getEmission()) {
                try {
                    MainCo2DataEntity updatedEntity = resultList.get(0);
                    updatedEntity.setEmission(requestedValue.getEmission());

                    this.merge(updatedEntity);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Returns all MainCo2DataEntities by the given country name.
     */
    public List<MainCo2DataEntity> getMainDataByCountry(String countryLabel) {

        // SELECT * FROM main WHERE country = countryLabel
        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<MainCo2DataEntity> query = criteria.createQuery(MainCo2DataEntity.class);

        Root<MainCo2DataEntity> root = query.from(MainCo2DataEntity.class);

        query.select(root);
        query.where(criteria.like(root.get("country"), countryLabel));

        TypedQuery<MainCo2DataEntity> typedQuery = em.createQuery(query);

        // Execute query
        return typedQuery.getResultList();
    }
}
