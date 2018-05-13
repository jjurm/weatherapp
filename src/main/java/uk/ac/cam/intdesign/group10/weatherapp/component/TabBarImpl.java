package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class TabBarImpl extends JPanel implements TabBar {

    public TabBarImpl() {
        setLayout(new BorderLayout());
        // just for test, remove it for real implementation
        add(new TestComponent("TabBar", Color.LIGHT_GRAY));
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // TODO update weather icons
    }
}
