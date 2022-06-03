package de.iu.jakarta_jsf.jsf;

import de.iu.jakarta_jsf.services.AuthService;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

/**
 * This class is the Backing-Bean for the login.xhtml page and manages the login-process.
 */
@ManagedBean
@Named
@SessionScoped
public class  LoginBean implements Serializable {

    public static final String INDEX_XHTML_URL = "index.xhtml";
    public static final String LOGIN_XHTML_URL = "login.xhtml";
    public static final String SECRET_XHTML_URL = "management.xhtml";
    public static final String USER_IS_LOGGED_IN = "user_is_logged_in";
    public static final String USER_IS_ADMIN = "user_is_admin";


    /**
     * Invokes an implementation of {@link AuthService} from the application-tier.
     */
    @Inject
    AuthService authService;

    /**
     * Variables to store the user-input of the login.xhtml input-fields for further processing
     */
    private String emailAddress;
    private String password;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Validate the entered user credentials.
     * To validate the login-data an AuthService is used, which is connected to a database
     * of email-addresses and passwords.
     * Forwards to SECRET_XHTML_URL if successful.
     */
    public String validateUsernamePassword() {
        List<Boolean> values = authService.validate(emailAddress, password);
        boolean loggedIn = values.get(0);
        boolean isAdmin = values.get(1);
        if (loggedIn) {
            HttpSession s = getHttpSession(true);
            s.setAttribute(USER_IS_LOGGED_IN, "true");
            s.setAttribute(USER_IS_ADMIN, String.valueOf(isAdmin));

            // ?faces-redirect=true solves "one URL behind" problem
            return SECRET_XHTML_URL + "?faces-redirect=true";
        } else {
            return "";
        }
    }

    /**
     * Get HttpSession object from the FacesContext (JSF) if available or creates a new one if the create-argument is true
     */
    public static HttpSession getHttpSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }

    /**
     * Invalidate the users session and forward to main page.
     */
    public String logout() {
        getHttpSession(false).invalidate();
        return INDEX_XHTML_URL + "?faces-redirect=true";
    }

    /**
     * Redirects user to the hidden page if he is logged in. Otherwise, he is redirected to the login page.
     */
    public String enterMangement() {
        HttpSession session = getHttpSession(false);

        if (userIsLoggedIn(session)) {
            return SECRET_XHTML_URL + "?faces-redirect=true";
        }
        return LOGIN_XHTML_URL + "?faces-redirect=true";

    }

    /**
     * Check if the HttpSession has the USER_IS_LOGGED_IN attribute.
     */
    private boolean userIsLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(USER_IS_LOGGED_IN) != null && session.getAttribute(USER_IS_LOGGED_IN).equals("true");
    }

//    public boolean renderHomeBtn() {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
//
//        String URI = request.getRequestURI();
//
//        if (URI.contains("index.xhtml")) {
//            return false;
//        } else {
//            return true;
//        }
//    }

}
