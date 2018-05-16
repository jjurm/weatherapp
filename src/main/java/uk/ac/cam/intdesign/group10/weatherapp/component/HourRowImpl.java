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
	public double hourTemperature;
	public int day = Calendar.DAY_OF_MONTH;
	
	
	public HourRowImpl(int startHourOfROw, int endHourOfRow) {
		fromHour = startHourOfROw;
		toHour = endHourOfRow;
		hourTemperature = getHourTemperature();
		
	}
	
	public double getHourTemperature() {
		
		double temp = 0.0;
		List<DayInfo> days = weatherData.days;
		for(DayInfo d : days) {
			if(d.day.getDayOfMonth() == day){
				
				List<HourInfo> hours = d.hours;
				for(HourInfo h : hours) {
					if(fromHour == h.time.getHour()) {
						
						temp = h.temperature;
						
					}
				}
			}
			
			break;
		}
		return temp;
		
		
		
	}
	
	public JLabel hoursLabel() {
		JLabel jFromHour = new JLabel();
		if(fromHour < 10) {
			if(toHour < 10) {
			jFromHour.setText("0" + String.valueOf(fromHour) + " - " + "0"+ String.valueOf(toHour));
			} else {
				
				jFromHour.setText("0" + String.valueOf(fromHour) + " - " + String.valueOf(toHour));
				
			}
		} else {
			jFromHour.setText(String.valueOf(fromHour) + " - " + String.valueOf(toHour));
		}
		return jFromHour;
		
	}
	

	public JLabel temperatureLabel() {
		JLabel jTemperature = new JLabel();
		jTemperature.setText(String.valueOf(hourTemperature) + " \u00b0" + "C");
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
