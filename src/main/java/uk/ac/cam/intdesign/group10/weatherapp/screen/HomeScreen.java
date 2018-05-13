package uk.ac.cam.intdesign.group10.weatherapp.screen;

import java.awt.BorderLayout;
import java.awt.Color;

import uk.ac.cam.intdesign.group10.weatherapp.component.TestComponent;

public class HomeScreen extends Screen {

    public HomeScreen() {
        setLayout(new BorderLayout());
        add(new TestComponent("Home screen", Color.green));
        setBackground(Color.black);
    }
}
