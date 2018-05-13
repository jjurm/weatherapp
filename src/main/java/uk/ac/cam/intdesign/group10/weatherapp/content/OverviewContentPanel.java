package uk.ac.cam.intdesign.group10.weatherapp.content;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.TemperatureOverviewImpl;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestRowWithButtons;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class OverviewContentPanel extends JPanel implements ContentPanel {

    private WeatherApp app;
    private HomeScreen homeScreen;

    private final TemperatureOverviewImpl temperatureOverview;
    private final TestRowWithButtons testRow;

    public OverviewContentPanel(WeatherApp app, HomeScreen homeScreen) {
        this.app = app;
        this.homeScreen = homeScreen;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        temperatureOverview = new TemperatureOverviewImpl();
        add(temperatureOverview);

        testRow = new TestRowWithButtons(app, homeScreen);
        add(testRow);
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // push down to separate components
        temperatureOverview.acceptWeatherData(data);
    }
}
