package de.iu.jakarta_jsf.jsf;

import de.iu.jakarta_jsf.services.AuthService;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;

@ManagedBean
@Named
@SessionScoped
public class  LoginBean implements Serializable {

    public static final String INDEX_XHTML_URL = "index.xhtml";
    public static final String LOGIN_XHTML_URL = "login.xhtml";
    public static final String USER_IS_LOGGED_IN = "user_is_logged_in";
    public static final String SECRET_XHTML_URL = "secret.xhtml";

    @Inject
    AuthService authService;

    private String emailAdress;
    private String password;

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validateUsernamePassword() {
        boolean loggedIn = authService.validate(emailAdress, password);
        if (loggedIn) {
            getHttpSession().setAttribute(USER_IS_LOGGED_IN, "true");
            // ?faces-redirect=true solves "one URL behind" problem
            return SECRET_XHTML_URL + "?faces-redirect=true";
        } else {
            return "";
        }
    }

    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public String logout() {
        getHttpSession().invalidate();
        return INDEX_XHTML_URL + "?faces-redirect=true";
    }
}
