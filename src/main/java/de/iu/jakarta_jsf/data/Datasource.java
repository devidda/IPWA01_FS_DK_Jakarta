package de.iu.jakarta_jsf.data;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.*;

import java.io.*;
import java.text.Collator;
import java.util.*;

@ManagedBean
@Named
@SessionScoped
public class Datasource implements Serializable {

    // private static final String dataFilePath = "src\\main\\resources\\global_co2.csv";
    private static final String dataFilePath = "ghg-emissions.csv";

    List<List<String>> data = readFile();

    List<String> countryChard = data.get(2);

    private List<List<String>> readFile() {

        // https://www.baeldung.com/java-csv-file-array
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(dataFilePath));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    public List getData() {
        return data;
    }

    public String emptyCheck(String dataCell) {
        if (!dataCell.equals("")) {
            return dataCell;
        } else {
            return "No data recorded.";
        }
    }

    public Float convertFloat(String input) {
        return Float.parseFloat(input);
    }

    public int sort(String ent1, String ent2) {
        float e1 = Float.parseFloat(ent1);
        float e2 = Float.parseFloat(ent2);
        return Float.compare(e1, e2);
    }

    public LineChartModel createChartModel() {
        LineChartModel model = new LineChartModel();
        LineChartSeries series = new LineChartSeries();
        List<String> c = countryChard.subList(2, countryChard.size());

        // c.remove(0);
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

    public void updateChar(List<String > countryName) {
        countryChard = countryName;
        PrimeFaces.current().ajax().update("chart");
    }

}