package uk.ac.cam.intdesign.group10.weatherapp.component;

import javax.swing.JComponent;

import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

public abstract class Toolbar extends JComponent {

    public abstract void displayLocation(Location location);

}
