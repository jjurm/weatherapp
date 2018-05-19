package uk.ac.cam.intdesign.group10.weatherapp;

import java.awt.Dimension;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;
import uk.ac.cam.intdesign.group10.weatherapp.screen.Screen;
import uk.ac.cam.intdesign.group10.weatherapp.screen.WelcomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.MockWeatherDataProvider;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataDownloader;

public class WeatherApp extends Application implements LocationConsumer, WeatherDataDownloader.Observer {

    public static final Dimension screenDimension = new Dimension(360, 600);

    private WeatherDataDownloader weatherDataDownloader = null;

    // we store the most recent WeatherData and Location,
    // so that it can be used instantly when we switch Screen
    private WeatherData lastWeatherData = null;
    private Location lastLocation = null;

    private Screen currentScreen;
    private StackPane screenHolder;

    /**
     * Changes screen and pushes last received WeatherData and Location to the screen
     */
    public void changeScreen(Screen screen) {
        screenHolder.getChildren().clear();
        currentScreen = screen;
        screenHolder.getChildren().add(screen.getRootNode());

        if (lastWeatherData != null) {
            currentScreen.acceptWeatherData(lastWeatherData);
        }
        if (lastLocation != null && !(currentScreen instanceof WelcomeScreen)) {
            currentScreen.acceptLocation(lastLocation);
        }
    }

    public void openLocationPickingDialog() {
        LocationPickingDialogImpl dialog = new LocationPickingDialogImpl();
        dialog.subscribe(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Weather app");

        createUiComponents(primaryStage);
        changeScreen(new WelcomeScreen(this));
        //changeScreen(new HomeScreen(this));

        weatherDataDownloader = new MockWeatherDataProvider();
        weatherDataDownloader.subscribe(this);

        lastLocation = new Location("UK/London");
        weatherDataDownloader.fetchData(lastLocation);
    }

    private void createUiComponents(Stage primaryStage) {
        StackPane root = new StackPane();

        screenHolder = new StackPane();
        root.getChildren().add(screenHolder);

        Scene scene = new Scene(root, screenDimension.width, screenDimension.height);
        scene.getStylesheets().add("stylesheet.css");

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
        primaryStage.show();
    }

    @Override
    public void acceptLocation(Location location) {

        lastLocation = location;

        // notify the screen
        currentScreen.acceptLocation(location);

        // and asynchronously query weather update
        if (weatherDataDownloader != null) {
            weatherDataDownloader.fetchData(location);
        } else {
            System.out.println("Downloading weather data not implemented yet");
        }
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // means we've got fresh weather data from the provider
        lastWeatherData = data;

        // notify the screen
        Platform.runLater(() -> currentScreen.acceptWeatherData(data));
    }

    @Override
    public void handleError(IOException exception) {
        System.out.println("Error while downloading weather data:");
        exception.printStackTrace();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
