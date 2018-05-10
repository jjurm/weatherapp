package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.util.function.Consumer;

import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public abstract class TabBar extends JPanel implements Consumer<WeatherData> {

}
