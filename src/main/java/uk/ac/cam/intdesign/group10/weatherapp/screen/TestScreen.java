package uk.ac.cam.intdesign.group10.weatherapp.screen;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestFxComponent;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class TestScreen implements Screen{

    private final TestFxComponent root;

    public TestScreen() {
        root = new TestFxComponent("Test screen", Color.LIGHTGREEN);
    }

    @Override
    public Node getRootNode() {
        return root;
    }

    @Override
    public void acceptLocation(Location location) {

    }

    @Override
    public void acceptWeatherData(WeatherData data) {

    }
}
