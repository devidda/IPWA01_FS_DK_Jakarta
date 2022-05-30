package de.iu.jakarta_jsf.data;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.*;

import java.io.*;
import java.util.*;

/**
 * This class simply accesses a csv file with CO2 data and visualizes it using the PrimeFaces framework.
 */
@ManagedBean
@Named
@SessionScoped
public class Datasource implements Serializable {

    private static final String DATA_FILE_PATH = "ghg-emissions.csv";

    List<List<String>> data = readFile();
    List<String> countryChard = data.get(2);

    public List<List<String>> getData() {
        return data;
    }

    public List<String> getCountryChard() {
        return countryChard;
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

    /**
     * Returns content of a data cell and a "No data recorded."-statement if its empty.
     */
    public String emptyCheck(String dataCell) {
        if (!dataCell.equals("")) {
            return dataCell;
        } else {
            return "No data recorded.";
        }
    }

    /**
     * Converts a string into a float
     * @param  input a string which is convertible to float
     */
    public Float convertFloat(String input) {
        return Float.parseFloat(input);
    }

    /**
     * Sort-Function to compare two given values. Used to sort the MtCO2e-values in the data table.
     */
    public int sort(String ent1, String ent2) {
        float e1 = Float.parseFloat(ent1);
        float e2 = Float.parseFloat(ent2);
        return Float.compare(e1, e2);
    }

    /**
     * Creates a LineChartModel with the PrimeFaces framework to be displayed on the main page (index.xhtml).
     * On first setup it displays the data for the CO2 emission of the USA.
     */
    public LineChartModel createChartModel() {
        LineChartModel model = new LineChartModel();
        LineChartSeries series = new LineChartSeries();
        List<String> c = countryChard.subList(2, countryChard.size());
        List<Float> fList = new ArrayList<>();

        for (String s : c) {
            fList.add(Float.parseFloat(s));
        }
        for (int i = 0; i < fList.size(); i++) {
            series.set(data.get(0).get(2 + i), fList.get(i));
        }

        series.setLabel(countryChard.get(0));
        model.addSeries(series);
        model.setLegendPosition("e");
        model.setSeriesColors("62656a,a5a5a5,929397");
        model.setAnimate(true);
        model.setTitle("Trend of the CO2-emissions of " + countryChard.get(0) + " in MtCO2e");

        Axis y = model.getAxis(AxisType.Y);
        y.setMin(Collections.min(fList));
        y.setMax(Collections.max(fList));
        y.setLabel("MtCO2e");

        Axis x = model.getAxis(AxisType.X);
        x.setMin(1990);
        x.setMax(2020);
        x.setTickInterval("5");
        x.setLabel("Year");

        return model;
    }

    /**
     * Reloads the displayed LineChartModel to visualize the data of the selected country.
     */
    public void updateChar(List<String > countryName) {
        countryChard = countryName;
        PrimeFaces.current().ajax().update("chart");
    }

}