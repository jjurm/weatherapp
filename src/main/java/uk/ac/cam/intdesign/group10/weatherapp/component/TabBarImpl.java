package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.content.DayContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.content.OverviewContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class TabBarImpl extends HBox implements TabBar
{
	private int activeBar = 0;
	private List<TabBarComponent> tabs;
	private HomeScreen screen;
	private WeatherApp app;
	
    public TabBarImpl(WeatherApp wap, HomeScreen scr)
    {
        screen = scr;
        app = wap;

        getStyleClass().add("tabbar");
        
    	LocalDate date = LocalDate.now().plusDays(2);
    	String DoW = getStringDayOf(date);
    	
    	tabs = new LinkedList<>();
    	
    	tabs.add(new TabBarComponent("Overview", this, 0));
    	tabs.add(new TabBarComponent("Today", this, 1));
    	tabs.add(new TabBarComponent("Tomorrow", this, 2));
    	tabs.add(new TabBarComponent(DoW, this, 3));
    	date = date.plusDays(1);
        DoW = getStringDayOf(date);
    	tabs.add(new TabBarComponent(DoW, this, 4));
    	
    	for (TabBarComponent tab : tabs)
    	{
    		// resize tabs to fill the width of the tab bar, with equal priority
			HBox.setHgrow(tab, Priority.ALWAYS);
			tab.setMaxWidth(Double.MAX_VALUE);
			tab.setPadding(new Insets(12, 0, 12, 0));
    		getChildren().add(tab);
    	}
    	
    	tabs.get(0).activate();
    }

	private String getStringDayOf(LocalDate date) {
		String dow = date.getDayOfWeek().toString();
		return  dow.substring(0, 1) + dow.substring(1, 3).toLowerCase();
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
	public Node getRootNode() {
		return this;
	}

	@Override
    public void acceptWeatherData(WeatherData data) {
        // TODO update weather icons
    }
}
