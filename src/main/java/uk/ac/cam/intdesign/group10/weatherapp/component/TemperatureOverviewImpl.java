package uk.ac.cam.intdesign.group10.weatherapp.component;

import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class TemperatureOverviewImpl extends JPanel implements TemperatureOverview {

    public TemperatureOverviewImpl(){
    }
    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // TODO
    }
}
