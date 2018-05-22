package uk.ac.cam.intdesign.group10.weatherapp.component;

import javafx.scene.control.Button;

public class TabBarComponent extends Button {

    private String title;
    private TabBarImpl parent;
    private int tabID;

    public void deactivate() {
        getStyleClass().remove("active");
    }

    public void activate() {
        getStyleClass().add("active");
    }

    TabBarComponent(String text, TabBarImpl p, int id)
    {
    	super(text);
    	    
        getStyleClass().add("tab");

        title = text;

        parent = p;
        tabID = id;

        this.setOnMouseClicked(event -> parent.tabBarChange(tabID));
    }

}
