package uk.ac.cam.intdesign.group10.weatherapp.screen;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class WelcomeScreen extends JPanel implements Screen {

    public WelcomeScreen() {
        setLayout(new BorderLayout());
        // just for test, remove it for real implementation
        add(new TestComponent("Welcome screen", Color.yellow));
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // welcome screen doesn't display weather data, so do nothing if data come
    }
}
