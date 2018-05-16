package uk.ac.cam.intdesign.group10.weatherapp.weather;

import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.DayInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.WeatherType;


import java.util.List;

public class SuitabilityEstimatorImpl implements SuitabilityEstimator{

	
	@Override
	public double estimate(WeatherData.HourInfo hourInfo, double minTemperature, double maxTemperature) {
		
	   double hourTemperature = hourInfo.temperature;
	   WeatherType hourWeather = hourInfo.type;
	   double estimatedValue = (hourTemperature - minTemperature)/(maxTemperature - minTemperature);
	   return estimatedValue;
	}
	
	
	
	




	
	



	
	
	

}
