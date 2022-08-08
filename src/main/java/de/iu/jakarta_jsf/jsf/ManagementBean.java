package de.iu.jakarta_jsf.jsf;

import de.iu.jakarta_jsf.persistence.*;
import de.iu.jakarta_jsf.services.ReService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static de.iu.jakarta_jsf.jsf.LoginBean.USER_IS_ADMIN;
import static de.iu.jakarta_jsf.jsf.LoginBean.USER_EMAIL;
import static de.iu.jakarta_jsf.jsf.LoginBean.getHttpSession;

/**
 * This class is the Backing-Bean for the management.xhtml page
 * and manages the user-requests to change or add data into the database.
 */
@Named
@ViewScoped
public class ManagementBean implements Serializable {

    @Inject
    ReService requestService;

    /**
     * Infos about the logged-in user.
     */
    boolean isAdmin = userIsAdmin(getHttpSession(false));
    String userEmail = findUserEmail(getHttpSession(false));

    /**
     * Variables to store new created requests to change the values of data
     * in the database and to provide a list of all the requests.
     */
    private List<RequestEntity> requests;
    List<RequestedValueEntity> inputs = new ArrayList<>();
    private RequestedValueEntity selectedRequestedValueEntity;


    @PostConstruct
    public void init() {
        requests = getUserRequestList();
    }

    /**
     * Returns the isAdmin-attribute from the FacesContext (previously added by the LoginBean)
     */
    private boolean userIsAdmin(HttpSession session) {
        return session != null && session.getAttribute(USER_IS_ADMIN) != null && session.getAttribute(USER_IS_ADMIN).equals("true");
    }

    /**
     * Returns the user's email-address from the FacesContext (previously added by the LoginBean)
     */
    private String findUserEmail(HttpSession session) {
        if(session != null && session.getAttribute(USER_EMAIL) != null) {
            return String.valueOf(session.getAttribute(USER_EMAIL));
        } else {
            return "unknown-user";
        }
    }

    /**
     * Shows a message that a row in the requestsCreation-datatable has been edited.
     */
    public void onRowEdit(RowEditEvent<RequestedValueEntity> event) {
        FacesMessage msg = new FacesMessage("Row Edited", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Shows a message that a row-edit in the requestsCreation-datatable has been cancelled.
     */
    public void onRowCancel(RowEditEvent<RequestedValueEntity> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Adds a new row to the editable requestCreation-datatable with the current year already set.
     */
    public void addNewInput() {
        RequestedValueEntity entity = new RequestedValueEntity();
        entity.setYear(LocalDate.now().getYear());
        inputs.add(entity);

        FacesMessage msg = new FacesMessage("New Row added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Submits a configured request with it's connected requested-value-changes (the individual rows which could be added and edited)
     */
    public void submitDataInput() {
        RequestEntity request = new RequestEntity();
        request.setAuthorName(userEmail);
        request.setDate(LocalDate.now().toString());
        List<RequestedValueEntity> subRequests = new ArrayList<>(inputs);
        request.setSubRequests(subRequests);
        requestService.saveRequestEntity(request);
        inputs.clear();
        requests = getUserRequestList();
    }

    /**
     * Deletes row in the requestCreation-datatable.
     * This function is called by a contextmenu of the datatable.
     */
    public void deleteRequestedValueEntity() {
        inputs.remove(selectedRequestedValueEntity);
        selectedRequestedValueEntity = null;
    }

    /**
     * Loads all requests of all users if the logged-in user is an admin.
     * If the logged-in user is not an admin, only the user's requests
     * (identified through the email-address) will be loaded.
     */
    private List<RequestEntity> getUserRequestList() {
        if (isAdmin) {
            return requestService.getAllRequests();
        }
        List<RequestEntity> results = requestService.findRequestByAuthor(userEmail);
        if (results == null) System.out.println("RequestEntities not found.");
        else System.out.println("RequestEntities found.");
        return results;
    }

    /**
     * Returns a string depending on whether the user is a peer-reviewer (admin-rights) or a researcher.
     */
    public String generateSignedInMessage() {
        if (isAdmin) return "Your are now logged in as a Peer-Reviewer!";
        else return "Your are now logged in as a Researcher!";
    }

    /**
     * Updates the requests (when the admin clicks on 'save changes')
     */
    public void updateRequests() {
        for (RequestEntity re : requests) {
            requestService.updateEntity(re);
        }
    }

    /**
     * getter and setter
     */
    public ReService getRequestService() {
        return requestService;
    }

    public List<RequestedValueEntity> getInputs() {
        return inputs;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setInputs(List<RequestedValueEntity> inputs) {
        this.inputs = inputs;
    }

    public List<RequestEntity> getRequests() {
        return requests;
    }

    public RequestedValueEntity getSelectedRequestedValueEntity() {
        return selectedRequestedValueEntity;
    }

    public void setSelectedRequestedValueEntity(RequestedValueEntity selectedRequestedValueEntity) {
        this.selectedRequestedValueEntity = selectedRequestedValueEntity;
    }
}
