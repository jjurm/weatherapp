package uk.ac.cam.intdesign.group10.weatherapp.component.test;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.AppComponent;
import uk.ac.cam.intdesign.group10.weatherapp.content.DayContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.screen.WelcomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataConsumer;

/**
 * This class is just for test purposes, to demonstrate switching between ContentPanels within the
 * HomeScreen and switching between Screens.
 */
public class TestRowWithButtons extends JPanel implements AppComponent, WeatherDataConsumer {

    private WeatherApp app;
    private HomeScreen homeScreen;

    private JLabel lblTemperature;

    public TestRowWithButtons(WeatherApp app, HomeScreen homeScreen) {
        this.app = app;
        this.homeScreen = homeScreen;

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.red);

        lblTemperature = new JLabel("Temperature: ??");
        add(lblTemperature);

        JButton btn1 = new JButton("Go to welcome screen");
        btn1.addActionListener(e -> {
            app.changeScreen(new WelcomeScreen(app));
        });
        add(btn1);

        JButton btn2 = new JButton("Switch to DayContent");
        btn2.addActionListener(e -> {
            homeScreen.changeContentPanel(new DayContentPanel(0));
        });
        add(btn2);
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        lblTemperature.setText("Temperature: " + data.actualTemperature);
    }
}
