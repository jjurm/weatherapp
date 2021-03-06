package uk.ac.cam.intdesign.group10.weatherapp.content;

import uk.ac.cam.intdesign.group10.weatherapp.component.test.FxComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataConsumer;

/**
 * ContentPanel is abstraction of different contents that can be put into HomeScreen.
 * Implementations are OverviewContentPanel and DayContentPanel
 */
public interface ContentPanel extends FxComponent, WeatherDataConsumer {
}
