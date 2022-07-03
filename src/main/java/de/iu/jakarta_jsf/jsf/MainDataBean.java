package de.iu.jakarta_jsf.jsf;

import de.iu.jakarta_jsf.persistence.MainCo2DataEntity;
import de.iu.jakarta_jsf.services.DataService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import java.io.Serializable;
import java.util.List;

/**
 * This class is the Backing-Bean for the index.xhtml page
 * and provides the selected data for the LineChartModel and the datatable.
 */
@Named
@SessionScoped
public class MainDataBean implements Serializable {

    @Inject
    DataService dataService;

    List<MainCo2DataEntity> data;

    /**
     * This variable specifies which country should be displayed as a LineChart-model. (default: United States)
     */
    String countryLabel = "United States";

    /**
     * Loads the default dataset from the database.
     */
    @PostConstruct
    public void init() {
        data = dataService.getLatestData();
    }

    public List<MainCo2DataEntity> getData() {
        return data;
    }

    public DataService getDataService() {
        return dataService;
    }

    public void setData(List<MainCo2DataEntity> data) {
        this.data = data;
    }

    /**
     * Returns a primefaces-LineChartModel using data it loads for the given countryLabel-variable.
     * It is called at the top in index.xhtml
     */
    public LineChartModel createChartModel() {
        LineChartModel model = new LineChartModel();
        LineChartSeries series = new LineChartSeries();

        List<MainCo2DataEntity> countryData = dataService.getMainDataByCountry(countryLabel);

        for (MainCo2DataEntity mde : countryData) {
            series.set(mde.getYear(), mde.getEmission());
        }

        series.setLabel(countryLabel);
        model.addSeries(series);
        model.setLegendPosition("e");
        model.setSeriesColors("62656a,a5a5a5,929397");
        model.setAnimate(true);
        model.setTitle("Trend of the CO2-emissions of " + countryLabel + " in MtCO2e");

        Axis y = model.getAxis(AxisType.Y);
        y.setLabel("MtCO2e");

        Axis x = model.getAxis(AxisType.X);
        x.setTickInterval("5");
        x.setLabel("Year");

        return model;
    }

    /**
     * Reloads the displayed LineChartModel to visualize the data of the selected country.
     */
    public void updateChar(String countryLabel) {
        this.countryLabel = countryLabel;
        PrimeFaces.current().ajax().update("lineChart");
    }
}
