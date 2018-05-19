package uk.ac.cam.intdesign.group10.weatherapp.screen;

import uk.ac.cam.intdesign.group10.weatherapp.component.test.FxComponent;
import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataConsumer;

/**
 * Screens are: WelcomeScreen, HomeScreen.
 */
public interface Screen extends FxComponent, WeatherDataConsumer, LocationConsumer {
}
