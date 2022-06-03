package de.iu.jakarta_jsf.services;

import de.iu.jakarta_jsf.persistence.UserDAO;
import de.iu.jakarta_jsf.persistence.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UserDAO dao;

    /**
     * Check if the entered credentials are valid.
     * @param enteredEmailAddress
     * @param enteredPassword
     * @return a boolean list with values if the credentials are valid
     *         and if the user is an Admin
     */
    @Override
    public List<Boolean> validate(String enteredEmailAddress, String enteredPassword) {

        UserEntity userEntity = dao.findUSerByMailAddress(enteredEmailAddress);

        if (userEntity == null) return null;
        else {
            boolean passwordIsCorrect = enteredPassword.equals(userEntity.getPassword());
            boolean isAdmin = userEntity.getAdmin();
            List<Boolean> values = new ArrayList<>();
            values.add(passwordIsCorrect);
            values.add(isAdmin);
            return values;
        }
    }
}