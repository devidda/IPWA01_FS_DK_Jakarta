package de.iu.jakarta_jsf.services;

import de.iu.jakarta_jsf.persistence.RequestEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Service to provide the Beans access to the RequestDAO and MainDataCo2DataDAO.
 * Also, it combines the two different DAOs in its methods.
 */
public interface ReService extends Serializable {

    /**
     * Returns all requests by the given authorMail (the email-address of a user).
     */
    List<RequestEntity> findRequestByAuthor(String authorMail);

    void saveRequestEntity(RequestEntity request);

    /**
     * Returns a list with the all the possible expressions of the RequestEntity's status-attribute.
     */
    List<String> getStatusList();

    /**
     * Updates the MainCO2Data in the database (used when clicked on "save changes" in management.xhtml).
     * If there is a new approved request in the datatable, which hasn't been saved in database before,
     * those RequestedValueEntities of this request will be merged into the MainData.
     */
    void updateEntity(RequestEntity request);

    /**
     * Returns all requests (no email-address to specify needed).
     */
    List<RequestEntity> getAllRequests();
}
