package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.util.Calendar;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataConsumer;

public class HourRowImpl extends FlowPane implements HourRow, WeatherDataConsumer {

	public WeatherData weatherData;
	public int fromHour;
	public int toHour;
	public int day = Calendar.DAY_OF_MONTH;
	public HourInfo hour;

	public HourRowImpl(int startHourOfROw, int endHourOfRow) {
		fromHour = startHourOfROw;
		toHour = endHourOfRow;

		getStyleClass().add("hourrow");

		setHgap(40);
	}

	public Label hoursLabel() {
		Label jHour = new Label();
		if (fromHour < 10) {
			if (toHour < 10) {
				jHour.setText("0" + String.valueOf(fromHour) + " - " + "0" + String.valueOf(toHour));
			} else {

				jHour.setText("0" + String.valueOf(fromHour) + " - " + String.valueOf(toHour));

			}
		} else {
			jHour.setText(String.valueOf(fromHour) + " - " + String.valueOf(toHour));
		}
		return jHour;

	}

	public Label temperatureLabel() {
		Label jTemperature = new Label();

		return jTemperature;
	}

	@Override
	public Node getRootNode() {
		return this;
	}

	@Override
	public void acceptWeatherData(WeatherData data) {

		weatherData = data;

	}

}
