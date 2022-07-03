package de.iu.jakarta_jsf.services;

import de.iu.jakarta_jsf.persistence.RequestDAO;
import de.iu.jakarta_jsf.persistence.RequestEntity;
import de.iu.jakarta_jsf.persistence.RequestedValueEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class ReServiceImpl implements ReService{

    public static final String REVIEWING = "REVIEWING";
    public static final String APPROVED = "APPROVED";
    public static final String DENIED = "DENIED";

    @Inject
    RequestDAO requestDAO;

    @Inject
    DataService dataService;

    @Override
    public List<RequestEntity> findRequestByAuthor(String authorMail) {
        return requestDAO.findRequestsByMailAddress(authorMail);
    }

    @Override
    public List<RequestEntity> getAllRequests() {
        return requestDAO.getAllRequests();
    }

    @Override
    public void saveRequestEntity(RequestEntity request) {
        requestDAO.persist(request);
    }

    @Override
    public void updateEntity(RequestEntity request) {
        requestDAO.merge(request);
        if (request.getStatus().equals(APPROVED) && request.getAlreadySaved() == false) {
            for (RequestedValueEntity rve : request.getSubRequests()) {
                dataService.updateMainDataWithRequest(rve);
            }
        }
    }

    @Override
    public List<String> getStatusList() {
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add(REVIEWING);
        statusList.add(APPROVED);
        statusList.add(DENIED);
        return statusList;
    }
}
