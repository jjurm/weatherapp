package uk.ac.cam.intdesign.group10.weatherapp.component;

import javafx.embed.swing.SwingFXUtils;
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
    final String DEGREE  = "\u00b0" + "C";
    private WeatherData.DayInfo dayInfo;

    public DaySummaryImpl(WeatherData.DayInfo dayInfo){
        setHgap(20);
        this.dayInfo = dayInfo;
        GridPane.setConstraints(dateLabel, 0, 0);
        GridPane.setConstraints(image, 1, 0);
        GridPane.setConstraints(weatherTypeLabel, 0, 1);
        GridPane.setConstraints(temperatureLabel, 1, 1);
        getChildren().addAll(weatherTypeLabel, temperatureLabel, image, dateLabel);

    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        weatherTypeLabel.setText(data.type.getDescription());
        temperatureLabel.setText(data.maxTemperature + DEGREE);
        dateLabel.setText(dayInfo.day.toString());
        image.setImage(SwingFXUtils.toFXImage(data.type.getImage(), null));
    }

    @Override
    public Node getRootNode() {
        return this;
    }
}
