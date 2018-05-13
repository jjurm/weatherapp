package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestComponent extends JPanel {

    public TestComponent(String title, Color bg) {
        add(new JLabel(title));
        setBackground(bg);
    }
}
