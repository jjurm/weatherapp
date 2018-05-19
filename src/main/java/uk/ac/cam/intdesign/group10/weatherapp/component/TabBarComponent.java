package uk.ac.cam.intdesign.group10.weatherapp.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TabBarComponent extends BorderPane {

    private String title;
    private TabBarImpl parent;
    private int tabID;

    public void deactivate() {
        getStyleClass().remove("active");
    }

    public void activate() {
        getStyleClass().add("active");
    }

    TabBarComponent(String text, TabBarImpl p, int id) {
        Label label = new Label(text);
        BorderPane.setMargin(label, new Insets(12, 0, 12, 0));
        setCenter(label);
        getStyleClass().add("tab");

        title = text;

        parent = p;
        tabID = id;

        this.setOnMouseClicked(event -> parent.tabBarChange(tabID));
    }

}
