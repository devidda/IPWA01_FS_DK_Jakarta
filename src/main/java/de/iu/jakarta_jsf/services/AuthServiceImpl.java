package de.iu.jakarta_jsf.services;

import de.iu.jakarta_jsf.persistence.UserDAO;
import de.iu.jakarta_jsf.persistence.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UserDAO dao;

    /**
     * Check if the entered credentials are valid.
     * @param enteredEmailAddress
     * @param enteredPassword
     * @return true, if the entered credentials are valid.
     */
    @Override
    public boolean validate(String enteredEmailAddress, String enteredPassword) {

        UserEntity userEntity = dao.findUSerByMailAddress(enteredEmailAddress);

        if (userEntity == null) return false;
        else {
            boolean passwordIsCorrect = enteredPassword.equals(userEntity.getPassword());
            return passwordIsCorrect;
        }
    }
}