package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.util.function.Consumer;

import javax.swing.JComponent;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public abstract class HourRow extends JComponent implements Consumer<WeatherData> {
}
