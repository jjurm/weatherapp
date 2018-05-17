package uk.ac.cam.intdesign.group10.weatherapp.component;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

import javax.swing.*;
import java.util.ArrayList;

public class WeatherDetailsImpl extends JPanel implements WeatherDetails{
    final String DEGREE  = "\u00b0" + "C";
    private JLabel minTempLabel = new JLabel("Min Temperature: ");
    private JLabel maxTempLabel = new JLabel("Max Temperature: ");
    private JLabel feelsLikeLabel = new JLabel("Feels Like: ");
    private JLabel windSpeedLabel = new JLabel("Wind Speed: ");
    private JLabel humidityLabel = new JLabel("Humidity: ");
    private JLabel precipitationLabel = new JLabel("Precipitation: ");
    private JLabel sunriseLabel = new JLabel("Sunrise: ");
    private JLabel sunsetLabel = new JLabel("Sunset: ");
    public WeatherDetailsImpl(){

        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(minTempLabel);
        labels.add(maxTempLabel);
        labels.add(feelsLikeLabel);
        labels.add(windSpeedLabel);
        labels.add(humidityLabel);
        labels.add(precipitationLabel);
        labels.add(sunriseLabel);
        labels.add(sunsetLabel);
        for(JLabel l : labels){
            add(l);
//            l.setAlignmentX(Component.LEFT_ALIGNMENT);

        }
    }
    @Override
    public JComponent getRootComponent() {
        return null;
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
