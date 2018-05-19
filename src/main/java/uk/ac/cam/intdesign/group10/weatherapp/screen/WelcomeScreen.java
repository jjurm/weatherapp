package uk.ac.cam.intdesign.group10.weatherapp.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.location.AutomaticLocationDetector;
import uk.ac.cam.intdesign.group10.weatherapp.location.AutomaticLocationDetectorImpl;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class WelcomeScreen extends AnchorPane implements Screen {

    private WeatherApp app;

    public WelcomeScreen(WeatherApp app) {
        this.app = app;

        this.getStyleClass().add("screen-welcome");

        Label h1 = new Label("Welcome :)");
        h1.getStyleClass().add("h1");
        Label h2 = new Label("We need your location\nto continue...");
        h2.getStyleClass().add("h2");

        Button btnPickLocation = new Button("Pick location");
        btnPickLocation.setOnAction(event -> {
            app.openLocationPickingDialog();
        });
        Button btnAutoDetect = new Button("Detect automatically");
        btnAutoDetect.setOnAction(event -> {
            AutomaticLocationDetector locationDetector = new AutomaticLocationDetectorImpl();
            app.acceptLocation(locationDetector.detectLocation());
        });

        HBox hb = new HBox();
        hb.setPadding(new Insets(0, 10, 10, 10));
        hb.setSpacing(12);
        hb.getChildren().addAll(btnPickLocation, btnAutoDetect);
        hb.setAlignment(Pos.CENTER);

        this.getChildren().addAll(h1, h2, hb);
        setTopAnchor(h1, 96.0);
        fullWidth(h1);
        setTopAnchor(h2, 240.0);
        fullWidth(h2);
        setBottomAnchor(hb, 96.0);
        fullWidth(hb);
    }

    private void fullWidth(Node node) {
        setLeftAnchor(node, 0.0);
        setRightAnchor(node, 0.0);
    }

    @Override
    public Node getRootNode() {
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
    }

}
