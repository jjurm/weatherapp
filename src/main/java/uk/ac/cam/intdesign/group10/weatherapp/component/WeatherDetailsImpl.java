package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.Component;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class WeatherDetailsImpl extends GridPane implements WeatherDetails{
    final String DEGREE  = "\u00b0" + "C";
    private Label minTempLabel = new Label("Min:\n");
    private Label maxTempLabel = new Label("Max:\n");
    private Label feelsLikeLabel = new Label("Feels Like:\t\t");
    private Label windSpeedLabel = new Label("Wind Speed:\t\t");
    private Label humidityLabel = new Label("Humidity:\t\t");
    private Label precipitationLabel = new Label("Precipitation:\t\t");
    private Label sunriseLabel = new Label("Sunrise:\t\t\t");
    private Label sunsetLabel = new Label("Sunset:\t\t\t");

    public WeatherDetailsImpl(){

        getStyleClass().add("weather-details");

        ArrayList<Label> labels = new ArrayList<>();

        GridPane.setConstraints(minTempLabel, 0, 0);
        GridPane.setConstraints(maxTempLabel, 1, 0);
        GridPane.setConstraints(feelsLikeLabel, 0, 1);
        GridPane.setConstraints(windSpeedLabel, 0, 2);
        GridPane.setConstraints(humidityLabel, 0, 3);
        GridPane.setConstraints(precipitationLabel, 0, 4);
        GridPane.setConstraints(sunriseLabel, 0, 5);
        GridPane.setConstraints(sunsetLabel, 0, 6);
        
        minTempLabel.setFont( new Font( minTempLabel.getFont().toString(), 32 ) );
        maxTempLabel.setFont( new Font( maxTempLabel.getFont().toString(), 32 ) );
        
        labels.add(minTempLabel);
        labels.add(maxTempLabel);
        labels.add(feelsLikeLabel);
        labels.add(windSpeedLabel);
        labels.add(humidityLabel);
        labels.add(precipitationLabel);
        labels.add(sunriseLabel);
        labels.add(sunsetLabel);
        for(Label l : labels)
        {
            getChildren().add(l);

        }
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data)
    {        
        minTempLabel.setText("Min:\n" + data.minTemperature + DEGREE);
        maxTempLabel.setText("Max:\n" + data.maxTemperature + DEGREE);
        
        feelsLikeLabel.setText("Feels Like:\t\t" + data.feelsLikeTemperature + DEGREE);
        windSpeedLabel.setText("Wind Speed:\t\t" + data.windSpeed + DEGREE);
        humidityLabel.setText("Humidity:\t\t" + data.humidity + "%");
        precipitationLabel.setText("Precipitation:\t\t" + data.precipitation + "%");
        sunriseLabel.setText("Sunrise:\t\t\t" + data.sunrise.toString());
        sunsetLabel.setText("Sunset:\t\t\t" + data.sunset.toString());
    }
}
