package de.iu.jakarta_jsf.persistence;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Startup;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.iu.jakarta_jsf.persistence.UserEntity.COLUMN_MAIL_ADDRESS;

/**
 * Helper-class to initialize a few entries of user credentials in the database.
 * setupTestData() method gets executed when the application is started.
 */
@Singleton
@Startup
public class TestDataGenerator {

    @Inject
    UserDAO userDAO;

    @Inject
    MainCo2DataDAO dataDAO;

    private static final String DATA_FILE_PATH = "ghg-emissions.csv";
//    @Inject
//    RequestDAO requestDAO;
//
//    @PersistenceContext
//    EntityManager em;

    @PostConstruct
    public void setupTestData() {
        UserEntity user1 = new UserEntity();
        user1.setMailAddress("admin@foo.bar");
        user1.setPassword("1234");
        user1.setAdmin(true);
        userDAO.persist(user1);

        UserEntity user2 = new UserEntity();
        user2.setMailAddress("researcher@foo.bar");
        user2.setPassword("1234");
        user2.setAdmin(false);
        userDAO.persist(user2);

        generateDataFromCsv();

    }

    private void generateDataFromCsv() {
        List<List<String>> data = readFile();
        List<List<String>> withoutHeaderData = data.subList(1, data.size());
        List<String> headerData = data.get(0);

        List<MainCo2DataEntity> dataEntities = new ArrayList<>();

        for (List<String> country : withoutHeaderData) {
            for (int i = 2; i < country.size(); i++) {
                if (country.get(i).equals("false")) continue;
                MainCo2DataEntity mde = new MainCo2DataEntity();
                mde.setCountry(country.get(0));
                mde.setYear(Integer.parseInt(headerData.get(i)));
                mde.setEmission(Float.parseFloat(country.get(i)));
                dataEntities.add(mde);
            }
        }

        for (MainCo2DataEntity mde : dataEntities) {
            dataDAO.persist(mde);
        }
    }

    /**
     * Reads in the csv file for further processing.
     */
    private List<List<String>> readFile() {
        List<List<String>> records = new ArrayList<List<String>>();

        try (CSVReader csvReader = new CSVReader(new FileReader(DATA_FILE_PATH));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

//        MainCo2DataEntity d1 = new MainCo2DataEntity();
//        d1.setCountry("USA");
//        d1.setYear(2021);
//        d1.setEmission(42);
//        dataDAO.persist(d1);
//
//        MainCo2DataEntity d2 = new MainCo2DataEntity();
//        d2.setCountry("USA");
//        d2.setYear(2022);
//        d2.setEmission(60);
//        dataDAO.persist(d2);
//
//        MainCo2DataEntity d3 = new MainCo2DataEntity();
//        d3.setCountry("Germany");
//        d3.setYear(2018);
//        d3.setEmission(43);
//        dataDAO.persist(d3);
//
//        MainCo2DataEntity d4 = new MainCo2DataEntity();
//        d4.setCountry("Germany");
//        d4.setYear(2017);
//        d4.setEmission(666);
//        dataDAO.persist(d4);
}
