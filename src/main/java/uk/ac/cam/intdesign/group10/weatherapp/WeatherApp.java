package uk.ac.cam.intdesign.group10.weatherapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.screen.Screen;
import uk.ac.cam.intdesign.group10.weatherapp.screen.WelcomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.MockWeatherDataProvider;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataDownloader;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class WeatherApp extends JFrame implements LocationConsumer, WeatherDataDownloader.Observer {

    public static final Dimension screenDimension = new Dimension(360, 600);

    private WeatherDataDownloader weatherDataDownloader = null;

    // we store the most recent WeatherData and Location,
    // so that it can be used instantly when we switch Screen
    private WeatherData lastWeatherData = null;
    private Location lastLocation = null;

    private Screen currentScreen;
    private JPanel screenHolder;

    public void changeScreen(Screen screen) {
        screenHolder.removeAll();
        currentScreen = screen;
        screenHolder.add(screen.getRootComponent());

        if (lastWeatherData != null) {
            currentScreen.acceptWeatherData(lastWeatherData);
        }
        if (lastLocation != null && !(currentScreen instanceof WelcomeScreen)) {
            currentScreen.acceptLocation(lastLocation);
        }

        // repaint panel and recalculate layout of components
        screenHolder.repaint();
        screenHolder.revalidate();
    }

    public void openLocationPickingDialog() {
        // TODO
    }

    private WeatherApp() {
        super("Weather app");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(600, 240);
        //setSize(300, 400);

        // TODO
        weatherDataDownloader = new MockWeatherDataProvider();
        weatherDataDownloader.subscribe(this);

        createComponents();
        changeScreen(new HomeScreen(this));

        // exit on ESC
        getRootPane().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EXIT");
        getRootPane().getActionMap().put("EXIT", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setVisible(true);
        
        lastLocation = new Location("UK/London");
        weatherDataDownloader.fetchData(lastLocation);
    }

    private void createComponents() {
        Container pane = getContentPane();

        screenHolder = new JPanel();
        screenHolder.setLayout(new BorderLayout(0, 0));
        screenHolder.setPreferredSize(screenDimension);
        pane.add(screenHolder, BorderLayout.CENTER);

    }

    @Override
    public void acceptLocation(Location location) {
        lastLocation = location;

        // notify the screen
        currentScreen.acceptLocation(location);

        // and asynchronously query weather update
        if (weatherDataDownloader != null) {
            weatherDataDownloader.fetchData(location);
        } else {
            System.out.println("Downloading weather data not implemented yet");
        }
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        // means we've got fresh weather data from the provider
        lastWeatherData = data;

        // notify the screen
        currentScreen.acceptWeatherData(data);
    }

    @Override
    public void handleError(IOException exception) {
        System.out.println("Error while downloading weather data:");
        exception.printStackTrace();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WeatherApp();
            }
        });
    }

}
