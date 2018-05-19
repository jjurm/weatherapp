package uk.ac.cam.intdesign.group10.weatherapp.content;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.TemperatureOverview;
import uk.ac.cam.intdesign.group10.weatherapp.component.TemperatureOverviewImpl;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class OverviewContentPanel extends VBox implements ContentPanel {

    private WeatherApp app;
    private HomeScreen homeScreen;

    private final TemperatureOverview temperatureOverview;

    public OverviewContentPanel(WeatherApp app, HomeScreen homeScreen) {
        this.app = app;
        this.homeScreen = homeScreen;

        getStyleClass().add("panel-overview");

        temperatureOverview = new TemperatureOverviewImpl();
        getChildren().add(temperatureOverview.getRootNode());

        //getChildren().add(new TestRowWithButtons(app, homeScreen));
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // push down to separate components
        temperatureOverview.acceptWeatherData(data);
    }
}
