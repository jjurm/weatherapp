package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.content.DayContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.content.OverviewContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class TabBarImpl extends JPanel implements TabBar
{
	private int activeBar = 0;
	private List<TabBarComponent> tabs;
	private HomeScreen screen;
	private WeatherApp app;
	
    public TabBarImpl(WeatherApp wap, HomeScreen scr)
    {
        setLayout(new GridLayout());
        
        screen = scr;
        app = wap;
        
    	LocalDate date = LocalDate.now().plusDays(2);
    	String DoW = date.getDayOfWeek().toString().substring(0, 1) + date.getDayOfWeek().toString().substring(1, date.getDayOfWeek().toString().length()).toLowerCase();
    	
    	tabs = new LinkedList<>();
    	
    	tabs.add(new TabBarComponent("Overview", Color.LIGHT_GRAY, Color.WHITE, this, 0));
    	tabs.add(new TabBarComponent("Today", Color.LIGHT_GRAY, Color.WHITE, this, 1));
    	tabs.add(new TabBarComponent("Tomorrow", Color.LIGHT_GRAY, Color.WHITE, this, 2));
    	tabs.add(new TabBarComponent(DoW, Color.LIGHT_GRAY, Color.WHITE, this, 3));
    	date = date.plusDays(1);
        DoW = date.getDayOfWeek().toString().substring(0, 1) + date.getDayOfWeek().toString().substring(1, date.getDayOfWeek().toString().length()).toLowerCase();
    	tabs.add(new TabBarComponent(DoW, Color.LIGHT_GRAY, Color.WHITE, this, 4));
    	
    	for (TabBarComponent tab : tabs)
    	{
    		add(tab);
    	}
    	
    	tabs.get(0).activate();
    }
    
    public void tabBarChange(int id)
    {
    	if (id == activeBar)
    		return;
    	
    	if (id == 0)
    		screen.changeContentPanel( new OverviewContentPanel(app, screen) );
    	else
    		screen.changeContentPanel( new DayContentPanel(id-1) );
    	
    	tabs.get(activeBar).deactivate();
    	tabs.get(id).activate();
    	
    	activeBar = id;
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // TODO update weather icons
    }
}
