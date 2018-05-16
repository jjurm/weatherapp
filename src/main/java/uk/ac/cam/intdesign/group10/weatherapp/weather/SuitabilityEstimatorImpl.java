package uk.ac.cam.intdesign.group10.weatherapp.weather;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.DayInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;

public class SuitabilityEstimatorImpl implements SuitabilityEstimator, WeatherDataConsumer {

	public WeatherData weatherData;
	public int day = Calendar.DAY_OF_MONTH;
	
	public  double estimate(HourInfo hourInfo) {
		
		return 0;
	}
	
	public HourInfo getHour() {
		
		List<DayInfo> days = weatherData.days;
		return hour;
				
	}
	@Override
	public void acceptWeatherData(WeatherData data) {
		
		weatherData = data; 
		
		
	}
	
	public Color estimatedColour() {
		 
		return Color.GREEN;
		
	}
	
	

}
