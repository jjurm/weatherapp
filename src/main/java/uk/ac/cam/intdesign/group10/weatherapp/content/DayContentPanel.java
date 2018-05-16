package uk.ac.cam.intdesign.group10.weatherapp.content;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.DayInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.WeatherType;
import uk.ac.cam.intdesign.group10.weatherapp.component.*;
import uk.ac.cam.intdesign.group10.weatherapp.weather.*;

public class DayContentPanel extends JPanel implements ContentPanel {
	public WeatherData weatherData;
	public WeatherData.DayInfo day;
    public List<HourRowImpl> rows;
    public Map<HourRowImpl, Double> estimations;
    public SuitabilityEstimatorImpl estimator;
    public DayContentPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // just for test, remove it
        add(new TestComponent("Day overview", Color.pink));
        JPanel suggestedTime = suggestedTimeCreator();
        this.add(suggestedTime);
        rows = initialiseHourRows();
        
        for(int i = 0 ; i < 24 ; i ++) {
        	
        	this.add(rows.get(i));
        }
        for(HourRowImpl hr : rows) {
        	if(hr.getBackground().equals(Color.GREEN)) {
        		int h1 = hr.fromHour;
        		int h2 = hr.toHour;
        		JLabel suggestedHours = new JLabel();
        		if(h1 < 10) {
        			if(h2 < 10) {
        			suggestedHours.setText("0" + String.valueOf(h1) + " - " + "0"+ String.valueOf(h2));
        			} else {
        				
        				suggestedHours.setText("0" + String.valueOf(h1) + " - " + String.valueOf(h2));
        				
        			}
        		} else {
        			suggestedHours.setText(String.valueOf(h1) + " - " + String.valueOf(h2));
        		}
        		suggestedTime.add(suggestedHours);
        		break;
        		
        	}
        }
        
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        
    	weatherData = data;
    }
    
	
	public HourInfo getHourInfo(HourRowImpl hr) {
		HourInfo hourInfo = new HourInfo();
		List<DayInfo> days = weatherData.days;
		for(DayInfo d : days) {
			if(d.day.getDayOfMonth() == hr.day){
				
				List<HourInfo> hours = d.hours;
				for(HourInfo h : hours) {
					if(hr.fromHour == h.time.getHour()) {
						
						hourInfo = h;
						
					}
				}
			}
			
			break;
		}
		return hourInfo;
		
		
		
	}
    
    
    
    public List<HourRowImpl> initialiseHourRows() {
    	
    	List<HourRowImpl> hourRows = new ArrayList<HourRowImpl>();
    	Map<HourRowImpl,Double> estimations = new HashMap<HourRowImpl,Double>();
    	
    	for( int i = 0 ; i < 24 ; i ++) {
    		HourRowImpl hr = new HourRowImpl(i,i+1);
    		hr.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
    		JLabel jHours = hr.hoursLabel();
    		JLabel jTemperature = hr.temperatureLabel();
    		HourInfo hourInfo = getHourInfo(hr);
    		double hourTemperature = hourInfo.temperature;
    		double estimation = Math.abs(20 - hourTemperature);
    		estimations.put(hr,estimation);
    		jTemperature.setText(String.valueOf(hourTemperature) + " \u00b0" + "C");
    		hr.add(jHours);
    		hr.add(jTemperature);
    		hourRows.add(hr);
    		
    	}
    	normalise();
    	colourHourRows();
    	return hourRows;
    }
    
    public void normalise() {
    	
    
    	double maximum = java.util.Collections.max(estimations.values());
    	double minimum = java.util.Collections.min(estimations.values());
    	for(HourRowImpl hr : estimations.keySet()) {
    		double val = estimations.get(hr);
    		double normVal = (val - minimum)/(maximum - minimum);
    		estimations.put(hr, normVal);
    	}
    }
    
    public void colourHourRows() {
    	
    	for(HourRowImpl hr : rows) {
    		if(getHourInfo(hr).type.equals(WeatherType.RAINY)) {
        		hr.setBackground(setColor(0.0));
        		} else {
                hr.setBackground(setColor(estimations.get(hr)));
        		}
    	}
    	
    }
   
    public Color setColor(double est) {
    	//Hue
    	double h = est*0.4;
    	//Saturation
    	double s = 0.9;
    	//Brightness
    	double b = 0.9;
    	return Color.getHSBColor((float)h, (float)s, (float)b) ;
    }
    
 
    
    public JPanel suggestedTimeCreator() {
    	JPanel jp = new JPanel();
    	jp.setLayout(new FlowLayout(FlowLayout.LEFT,40,0));
    	JLabel st = new JLabel("Suggested time:");
    	jp.add(st);
    	double from = 0.0;
    	double to = 0.0;
    	for(HourRowImpl hr : estimations.keySet()) {
    		if(estimations.get(hr) == 1.0) {
    			 from = hr.fromHour;
    			 to = hr.toHour;
    			 break;
    		}
    		
    		
    	}
    	JLabel jTime = new JLabel();
    	jTime.setText(String.valueOf(from) + " - " + String.valueOf(to));
    	
    	return jp;
    }
    
    
}
