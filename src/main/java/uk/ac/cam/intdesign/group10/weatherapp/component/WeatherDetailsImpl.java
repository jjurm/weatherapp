package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class WeatherDetailsImpl extends VBox implements WeatherDetails{
    final String DEGREE  = "\u00b0" + "C";
    private Label minTempLabel = new Label("Min Temperature: ");
    private Label maxTempLabel = new Label("Max Temperature: ");
    private Label feelsLikeLabel = new Label("Feels Like: ");
    private Label windSpeedLabel = new Label("Wind Speed: ");
    private Label humidityLabel = new Label("Humidity: ");
    private Label precipitationLabel = new Label("Precipitation: ");
    private Label sunriseLabel = new Label("Sunrise: ");
    private Label sunsetLabel = new Label("Sunset: ");

    public WeatherDetailsImpl(){

        getStyleClass().add("weather-details");

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(minTempLabel);
        labels.add(maxTempLabel);
        labels.add(feelsLikeLabel);
        labels.add(windSpeedLabel);
        labels.add(humidityLabel);
        labels.add(precipitationLabel);
        labels.add(sunriseLabel);
        labels.add(sunsetLabel);
        for(Label l : labels){
            getChildren().add(l);
//            l.setAlignmentX(Component.LEFT_ALIGNMENT);

        }
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        minTempLabel.setText("Min Temperature: " + data.minTemperature + DEGREE);
        maxTempLabel.setText("Max Temperature: " + data.maxTemperature + DEGREE);
        feelsLikeLabel.setText("Feels Like: " + data.feelsLikeTemperature + DEGREE);
        windSpeedLabel.setText("Wind Speed: " + data.windSpeed + DEGREE);
        humidityLabel.setText("Humidity: " + data.humidity + "%");
        precipitationLabel.setText("Precipitation: " + data.precipitation + "%");
        sunriseLabel.setText("Sunrise: " + data.sunrise.toString());
        sunsetLabel.setText("Sunset: " + data.sunset.toString());
    }
}
