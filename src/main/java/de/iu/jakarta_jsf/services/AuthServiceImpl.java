package de.iu.jakarta_jsf.services;

import de.iu.jakarta_jsf.persistence.UserDAO;
import de.iu.jakarta_jsf.persistence.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UserDAO dao;

    @Override
    public boolean validate(String enteredEmailAddress, String enteredPassword) {

        UserEntity userEntity = dao.findUSerByMailAddress(enteredEmailAddress);

        if (userEntity == null) return false;
        else {
            boolean passwordIsCorrect = enteredPassword.equals(userEntity.getPassword());
            return passwordIsCorrect;
        }

        // return enteredEmailAddress.equals("david.kort@gmx.de") && enteredPassword.equals("fff");
    }
}