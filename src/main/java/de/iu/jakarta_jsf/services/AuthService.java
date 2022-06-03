package de.iu.jakarta_jsf.services;

import java.io.Serializable;
import java.util.List;

public interface AuthService extends Serializable {
    List<Boolean> validate(String emailAdress, String password);
}
