package de.iu.jakarta_jsf.jsf;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    @Override
    public boolean validate(String emailAdress, String password) {
        return emailAdress.equals("david.kort@gmx.de") && password.equals("fff");
    }
}