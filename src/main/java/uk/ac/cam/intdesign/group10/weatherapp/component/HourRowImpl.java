package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.util.Calendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.DayInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataConsumer;

public class HourRowImpl extends JPanel implements HourRow, WeatherDataConsumer{
	
	
	public WeatherData weatherData;
	public int fromHour;
	public int toHour;
	public int day = Calendar.DAY_OF_MONTH;
	public HourInfo hour;
	
	public HourRowImpl(int startHourOfROw, int endHourOfRow) {
		fromHour = startHourOfROw;
		toHour = endHourOfRow;
		
		
	}

	
	public JLabel hoursLabel() {
		JLabel jHour = new JLabel();
		if(fromHour < 10) {
			if(toHour < 10) {
			jHour.setText("0" + String.valueOf(fromHour) + " - " + "0"+ String.valueOf(toHour));
			} else {
				
				jHour.setText("0" + String.valueOf(fromHour) + " - " + String.valueOf(toHour));
				
			}
		} else {
			jHour.setText(String.valueOf(fromHour) + " - " + String.valueOf(toHour));
		}
		return jHour;
		
	}
	

	public JLabel temperatureLabel() {
		JLabel jTemperature = new JLabel();
		
		return jTemperature;
	}
	
	
	
	@Override
	public JComponent getRootComponent() {
		return this ;
	}

	@Override
	public void acceptWeatherData(WeatherData data) {
		
		weatherData = data ;
		
		
	}
	
	

}
