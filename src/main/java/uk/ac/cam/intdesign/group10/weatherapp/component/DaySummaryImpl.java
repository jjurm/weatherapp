package uk.ac.cam.intdesign.group10.weatherapp.component;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

import javax.swing.*;

public class DaySummaryImpl extends JPanel implements DaySummary {
    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {

    }
}
