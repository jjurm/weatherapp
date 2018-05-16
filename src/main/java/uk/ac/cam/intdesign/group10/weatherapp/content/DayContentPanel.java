package uk.ac.cam.intdesign.group10.weatherapp.content;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.component.*;
import uk.ac.cam.intdesign.group10.weatherapp.weather.*;

public class DayContentPanel extends JPanel implements ContentPanel {
	WeatherData weatherData;
    List<HourRowImpl> rows;
    SuitabilityEstimatorImpl estimator;
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
    
    
    
    public static List<HourRowImpl> initialiseHourRows() {
    	List<HourRowImpl> hourRows = new ArrayList<HourRowImpl>();
    	
    	for( int i = 0 ; i < 24 ; i ++) {
    		HourRowImpl hr = new HourRowImpl(i,i+1);
  
    		hr.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
    		JLabel jHours = hr.hoursLabel();
    		JLabel jTemperature = hr.temperatureLabel();
    		hr.add(jHours);
    		hr.add(jTemperature);
    		hourRows.add(hr);
    		hr.setBackground(Color.GREEN);
    	}
    	return hourRows;
    }
    
    public static JPanel suggestedTimeCreator() {
    	JPanel jp = new JPanel();
    	jp.setLayout(new FlowLayout(FlowLayout.LEFT,40,0));
    	JLabel st = new JLabel("Suggested time:");
    	jp.add(st);
    	return jp;
    }
    
    
}
