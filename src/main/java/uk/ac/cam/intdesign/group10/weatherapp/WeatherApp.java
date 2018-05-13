package uk.ac.cam.intdesign.group10.weatherapp;

import java.awt.Container;

import javax.swing.JFrame;

import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.screen.Screen;

public class WeatherApp extends JFrame {

    Screen currentScreen;
    private WeatherAppPanel contentPanel;

    public void changeScreen(Screen screen) {
        contentPanel.screenHolder.removeAll();
        currentScreen = screen;
        contentPanel.screenHolder.add(screen);
    }

    public WeatherApp() {
        super("Weather app");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);

        createComponents();
        changeScreen(new HomeScreen());

        //pack();
    }

    private void createComponents() {
        Container pane = getContentPane();

        contentPanel = new WeatherAppPanel();
        pane.add(contentPanel.$$$getRootComponent$$$());
    }

    public void run() {
        setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WeatherApp app = new WeatherApp();
                app.run();
            }
        });
    }

}
