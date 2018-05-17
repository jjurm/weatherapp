package uk.ac.cam.intdesign.group10.weatherapp.component;

import javax.swing.*;

import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

import java.awt.*;
import java.util.ArrayList;

public class TemperatureOverviewImpl extends JPanel implements TemperatureOverview {
    final String DEGREE  = "\u00b0" + "C";
    private RightNowLabel temperatureLabel = new RightNowLabel(0);
    private JLabel minTempLabel = new JLabel("Min Temperature: ");
    private JLabel maxTempLabel = new JLabel("Max Temperature: ");
    private JLabel feelsLikeLabel = new JLabel("Feels Like: ");
    private JLabel windSpeedLabel = new JLabel("Wind Speed: ");
    private JLabel humidityLabel = new JLabel("Humidity: ");
    private JLabel precipitationLabel = new JLabel("Precipitation: ");
    private JLabel sunriseLabel = new JLabel("Sunrise: ");
    private JLabel sunsetLabel = new JLabel("Sunset: ");

    public TemperatureOverviewImpl(){
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridLayout(0, 1));
        ArrayList<JLabel> labels = new ArrayList<>();
        JPanel detailsPanel = new JPanel();
        labels.add(minTempLabel);
        labels.add(maxTempLabel);
        labels.add(feelsLikeLabel);
        labels.add(windSpeedLabel);
        labels.add(humidityLabel);
        labels.add(precipitationLabel);
        labels.add(sunriseLabel);
        labels.add(sunsetLabel);
        add(temperatureLabel);
        for(JLabel l : labels){
            detailsPanel.add(l);
//            l.setAlignmentX(Component.LEFT_ALIGNMENT);

        }
        add(detailsPanel);

    }
    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // TODO
        temperatureLabel.update(data.actualTemperature, data.type.getImage());
        minTempLabel.setText("Min Temperature: " + data.minTemperature + DEGREE);
        maxTempLabel.setText("Max Temperature: " + data.maxTemperature + DEGREE);
        feelsLikeLabel.setText("Feels Like: " + data.feelsLikeTemperature + DEGREE);
        windSpeedLabel.setText("Wind Speed: " + data.windSpeed + DEGREE);
        humidityLabel.setText("Humidity: " + data.humidity + "%");
        precipitationLabel.setText("Precipitation: " + data.precipitation + "%");
        sunriseLabel.setText("Sunrise: " + data.sunrise.toString());
        sunsetLabel.setText("Sunset: " + data.sunset.toString());
    }
    private class RightNowLabel extends JPanel{
        private JLabel rightNowLabel = new JLabel("Right Now");
        private JLabel tempLabel = new JLabel();
        private JLabel imageLabel = new JLabel();
        public RightNowLabel(double temp) {
            setLayout(new GridLayout(0, 1));
            tempLabel.setFont(rightNowLabel.getFont().deriveFont(65.0f));
            add(rightNowLabel);
            add(tempLabel);
            add(imageLabel);
        }
        public void update(double temp, Image weatherType){
            tempLabel.setText(temp + DEGREE);
            tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageLabel.setIcon(new ImageIcon(weatherType.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        }
    }
}
