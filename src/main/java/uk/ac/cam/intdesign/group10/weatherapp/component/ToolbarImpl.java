package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

public class ToolbarImpl extends JPanel implements Toolbar {

    public ToolbarImpl() {
        setLayout(new BorderLayout());
        add(new TestComponent("Toolbar", Color.BLUE));
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void displayLocation(Location location) {
        // TODO
    }
}
