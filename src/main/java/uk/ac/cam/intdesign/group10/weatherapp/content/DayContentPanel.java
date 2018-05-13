package uk.ac.cam.intdesign.group10.weatherapp.content;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.component.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class DayContentPanel extends JPanel implements ContentPanel {

    public DayContentPanel() {
        setLayout(new BorderLayout());
        // just for test, remove it
        add(new TestComponent("Day overview", Color.pink));
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
