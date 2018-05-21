package uk.ac.cam.intdesign.group10.weatherapp.content;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uk.ac.cam.intdesign.group10.weatherapp.component.DaySummaryImpl;
import uk.ac.cam.intdesign.group10.weatherapp.component.HourRowImpl;
import uk.ac.cam.intdesign.group10.weatherapp.weather.SuitabilityEstimator;
import uk.ac.cam.intdesign.group10.weatherapp.weather.SuitabilityEstimatorImpl;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.DayInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;

public class DayContentPanel extends VBox implements ContentPanel {

    private SuitabilityEstimator estimator = new SuitabilityEstimatorImpl();
    private List<HourRowImpl> rows = new ArrayList<>();
    private int dayIndex;
    private Label lblSuggestedTime;
    private DaySummaryImpl daySummary;

    public DayContentPanel(int dayIndex) {
        this.dayIndex = dayIndex;
        getStyleClass().add("panel-day");

        createComponents();
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    private void createComponents() {
        // only create components once when DayContentPanel is created, don't fill with content
        daySummary = new DaySummaryImpl(dayIndex);
        getChildren().add(daySummary);
        lblSuggestedTime = new Label("Suggested time:");
        lblSuggestedTime.getStyleClass().add("suggested-time");
        getChildren().add(lblSuggestedTime);

        for (int i = 0; i < 24; i++) {
            HourRowImpl hr = new HourRowImpl(i, i + 1);
            rows.add(hr);
            getChildren().add(hr);
        }

    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // now put the data into previously created components

        DayInfo dayInfo = data.days.get(dayIndex);
        Map<HourInfo, Double> estimations = estimator.estimate(dayInfo);

        Optional<HourInfo> max = estimations.keySet().stream().max(Comparator.comparing(estimations::get));
        if (max.isPresent()) {
            int h1 = max.get().time.getHour();
            lblSuggestedTime.setText(String.format("Suggested time: %2d - %2d", h1, h1+1));
        }

        // delegate each row's responsibility to that row
        rows.forEach(row -> row.acceptWeatherData(dayInfo, estimations));
        daySummary.acceptWeatherData(data);

    }

}
