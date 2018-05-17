package uk.ac.cam.intdesign.group10.weatherapp.component;

import javax.swing.*;

import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

import java.awt.*;
import java.util.ArrayList;

public class TemperatureOverviewImpl extends JPanel implements TemperatureOverview {
    final String DEGREE  = "\u00b0" + "C";
    private RightNowLabel temperatureLabel = new RightNowLabel(0);
    private WeatherDetailsImpl weatherDetails = new WeatherDetailsImpl();

    public TemperatureOverviewImpl(){
        setLayout(new GridLayout(0, 1));
        add(temperatureLabel);
        add(weatherDetails);

    }
    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        temperatureLabel.update(data.actualTemperature, data.type.getImage());
        weatherDetails.acceptWeatherData(data);
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
