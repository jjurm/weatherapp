package uk.ac.cam.intdesign.group10.weatherapp.component;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.FxComponent;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataConsumer;

public class Header extends VBox implements WeatherDataConsumer, LocationConsumer, FxComponent {

    private WeatherApp app;
    private HomeScreen homeScreen;

    public Toolbar toolbar;
    public TabBar tabbar;

    public Header(WeatherApp app, HomeScreen homeScreen) {
        this.app = app;
        this.homeScreen = homeScreen;

        getStyleClass().add("header");

        toolbar = new ToolbarImpl();
        getChildren().add(toolbar.getRootNode());

        tabbar = new TabBarImpl(app, homeScreen);
        getChildren().add(tabbar.getRootNode());
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

    @Override
    public Node getRootNode() {
        return this;
    }
}
