package uk.ac.cam.intdesign.group10.weatherapp.component;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

import javax.swing.*;

public class DaySummaryImpl extends GridPane implements DaySummary {
    private Label weatherTypeLabel = new Label();
    private Label temperatureLabel = new Label();
    private ImageView image = new ImageView();
    private Label dateLabel = new Label();

    public DaySummaryImpl(){
        setHgap(20);
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        weatherTypeLabel.setText(data.type.getDescription());

    }

    @Override
    public Node getRootNode() {
        return this;
    }
}
