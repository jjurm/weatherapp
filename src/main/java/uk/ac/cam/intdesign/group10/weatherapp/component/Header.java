package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataConsumer;

public class Header extends JPanel implements WeatherDataConsumer, LocationConsumer {

    private WeatherApp app;
    private HomeScreen homeScreen;

    public Toolbar toolbar;
    public TabBar tabbar;

    public Header(WeatherApp app, HomeScreen homeScreen) {
        this.app = app;
        this.homeScreen = homeScreen;

        // create toolbar, tabbar
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

        toolbar = new ToolbarImpl();
        add(toolbar.getRootComponent());

        tabbar = new TabBarImpl(app, homeScreen);
        add(tabbar.getRootComponent());
    }

    @Override
    public void acceptLocation(Location location) {
        // location is displayed in Toolbar
        toolbar.acceptLocation(location);
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // weather icons are displayed in tabs, so update TabBar with new data
        tabbar.acceptWeatherData(data);
    }
}
