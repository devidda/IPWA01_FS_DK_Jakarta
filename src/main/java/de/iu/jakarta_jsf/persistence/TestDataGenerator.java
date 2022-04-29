package de.iu.jakarta_jsf.persistence;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Startup;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
@Startup
public class TestDataGenerator {

    @Inject
    UserDAO userDAO;

    @PostConstruct
    public void setupTestData() {
        UserEntity user1 = new UserEntity();
        user1.setMailAddress("d.trump@foo.bar");
        user1.setPassword("1234");
        userDAO.persist(user1);

        UserEntity user2 = new UserEntity();
        user2.setMailAddress("j.biden@foo.bar");
        user2.setPassword("1234");
        userDAO.persist(user2);
    }

}
