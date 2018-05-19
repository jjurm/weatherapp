package uk.ac.cam.intdesign.group10.weatherapp.component;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestFxComponent;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

public class ToolbarImpl extends BorderPane implements Toolbar {

    public ToolbarImpl() {
        setCenter(new TestFxComponent("Toolbar", Color.LIGHTBLUE));
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptLocation(Location location) {
        // TODO
    }
}
