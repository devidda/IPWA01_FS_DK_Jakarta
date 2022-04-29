package de.iu.jakarta_jsf.services;

import java.io.Serializable;

public interface AuthService extends Serializable {
    boolean validate(String emailAdress, String password);
}
