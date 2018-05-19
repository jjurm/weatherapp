package uk.ac.cam.intdesign.group10.weatherapp.screen;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.LocationPickingDialogImpl;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.AppComponent;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.location.AutomaticLocationDetectorImpl;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class WelcomeScreen extends JPanel implements Screen, AppComponent {

    private WeatherApp app;

    public WelcomeScreen(WeatherApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        // just for test, remove it for real implementation
        add(new TestComponent("Welcome screen", Color.yellow), BorderLayout.NORTH);
        add(new TestComponent("We need your location to continue...", Color.WHITE), BorderLayout.CENTER);
        Button pickLocationButton = new Button("Pick Location");
        LocationPickingDialogImpl locationPickingDialog = new LocationPickingDialogImpl();
        locationPickingDialog.subscribe(this);

        pickLocationButton.addActionListener(e -> app.openLocationPickingDialog());

        add(pickLocationButton, BorderLayout.SOUTH);
        Button detectLocationButton = new Button("Detect Location Automatically");
        detectLocationButton.addActionListener(e -> acceptLocation(new AutomaticLocationDetectorImpl().detectLocation()));
        add(detectLocationButton, BorderLayout.SOUTH);
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // welcome screen doesn't display weather data, so do nothing if data come
    }

    @Override
    public void acceptLocation(Location location) {
        // the screen should change to HomeScreen as soon as location is chosen, so we don't care
        HomeScreen homeScreen = new HomeScreen(app);
        app.changeScreen(homeScreen);
        homeScreen.acceptLocation(location);
    }
}
