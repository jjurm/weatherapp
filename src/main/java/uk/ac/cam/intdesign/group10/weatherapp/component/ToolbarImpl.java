package uk.ac.cam.intdesign.group10.weatherapp.component;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestFxComponent;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class ToolbarImpl extends ToolBar implements Toolbar {

    private Location location;
    private Label locationName;
    private Button changeLocation;
    public ToolbarImpl(WeatherApp weatherApp) {
        //setCenter(new TestFxComponent("Toolbar", Color.LIGHTBLUE));
        locationName = new Label();
        changeLocation = new Button();
        changeLocation.setText("Change");
        changeLocation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                weatherApp.openLocationPickingDialog();
            }
        });
        this.getItems().add(locationName);
        this.getItems().add(new Separator());
        this.getItems().add(changeLocation);
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptLocation(Location location) {
        this.location = location;
        refresh();
    }

    private void refresh() {
        locationName.setText(location.getName());
    }
}
