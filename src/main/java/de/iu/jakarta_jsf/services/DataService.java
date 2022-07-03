package de.iu.jakarta_jsf.services;

import de.iu.jakarta_jsf.persistence.MainCo2DataEntity;
import de.iu.jakarta_jsf.persistence.RequestedValueEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Service to provide the Beans access to the stateless MainCo2DataDAO.
 */
public interface DataService extends Serializable {

    /**
     * Updates the MainCo2DataEntities with the data of the given RequestedValueEntity.
     */
    void updateMainDataWithRequest(RequestedValueEntity valueEntity);

    /**
     * Returns the latest MainCo2DataEntity for each individual country in the database through a DAO.
     */
    List<MainCo2DataEntity> getLatestData();

    /**
     * Returns all MainCo2DataEntities by the given country name through a DAO.
     */
    List<MainCo2DataEntity> getMainDataByCountry(String countryLabel);
}
