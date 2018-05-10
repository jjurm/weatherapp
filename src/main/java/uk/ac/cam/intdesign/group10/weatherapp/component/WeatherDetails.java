package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.util.function.Consumer;

import javax.swing.JComponent;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public abstract class WeatherDetails extends JComponent implements Consumer<WeatherData> {
}
