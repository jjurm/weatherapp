package uk.ac.cam.intdesign.group10.weatherapp.screen;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.Header;
import uk.ac.cam.intdesign.group10.weatherapp.content.ContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.content.OverviewContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class HomeScreen extends BorderPane implements Screen {

    private WeatherApp app;
    // we store the most recent WeatherData, so that it can be used instantly when we switch ContentPanel
    private WeatherData lastWeatherData = null;

    private final Header header;
    private final ScrollPane contentPanelHolder;
    private ContentPanel currentContentPanel;

    /**
     * Call this to change the HomeScreen content (e.g. from overview to specific day)
     */
    public void changeContentPanel(ContentPanel contentPanel) {
        currentContentPanel = contentPanel;

        // push last weather data to new content panel
        if (lastWeatherData != null) {
            currentContentPanel.acceptWeatherData(lastWeatherData);
            currentContentPanel.acceptWeatherData(lastWeatherData);
        }

        contentPanelHolder.setContent(contentPanel.getRootNode());
    }

    public HomeScreen(WeatherApp app) {
        this.app = app;

        header = new Header(app, this);
        setTop(header.getRootNode());

        contentPanelHolder = new ScrollPane();
        contentPanelHolder.setFitToWidth(true);
        setCenter(contentPanelHolder);

        changeContentPanel(new OverviewContentPanel(app, this));
    }

    @Override
    public Pane getRootNode() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        lastWeatherData = data;

        // must notify both the header and the current ContentPanel
        header.acceptWeatherData(data);
        currentContentPanel.acceptWeatherData(lastWeatherData);
    }

    @Override
    public void acceptLocation(Location location) {
        // notify the Header
        header.acceptLocation(location);
    }
}
