package de.iu.jakarta_jsf.persistence;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Startup;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
}
