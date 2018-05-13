package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Header extends JPanel {

    public Toolbar toolbar;
    public TabBar tabbar;

    public Header() {
        // create toolbar, tabbar
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

        toolbar = new ToolbarImpl();
        add(toolbar.getRootComponent());

        tabbar = new TabBarImpl();
        add(tabbar.getRootComponent());
    }

}
