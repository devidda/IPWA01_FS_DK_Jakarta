package de.iu.jakarta_jsf.services;

import java.io.Serializable;
import java.util.List;

/**
 * Validates the entered user-credentials in the login.xhtml.
 */
public interface AuthService extends Serializable {

    /**
     * Check if the entered credentials are valid.
     * Returns a boolean list with values if the credentials are valid
     * and if the user is an Admin.
     */
    List<Boolean> validate(String emailAdress, String password);
}
