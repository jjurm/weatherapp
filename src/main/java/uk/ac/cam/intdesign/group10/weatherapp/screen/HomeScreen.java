package uk.ac.cam.intdesign.group10.weatherapp.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.Header;
import uk.ac.cam.intdesign.group10.weatherapp.content.ContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.content.OverviewContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class HomeScreen extends JPanel implements Screen {

    private WeatherApp app;
    // we store the most recent WeatherData, so that it can be used instantly when we switch ContentPanel
    private WeatherData lastWeatherData = null;

    private final Header header;
    private final JPanel contentPanelHolder;
    private ContentPanel currentContentPanel;

    /**
     * Call this to change the HomeScreen content (e.g. from overview to specific day)
     */
    public void changeContentPanel(ContentPanel contentPanel) {
        contentPanelHolder.removeAll();
        currentContentPanel = contentPanel;

        // push last weather data to new content panel
        if (lastWeatherData != null) {
            currentContentPanel.acceptWeatherData(lastWeatherData);
        }
        contentPanelHolder.add(contentPanel.getRootComponent());

        // repaint panel and recalculate layout of components
        contentPanelHolder.repaint();
        contentPanelHolder.revalidate();
    }

    public HomeScreen(WeatherApp app) {
        this.app = app;

        setLayout(new BorderLayout());
        header = new Header(app, this);
        add(header, BorderLayout.NORTH);

        contentPanelHolder = new JPanel();
        contentPanelHolder.setLayout(new BorderLayout());

        // set preferred size to minimum so that it's not wider than the screen
        // it will be resized by the scrollPane to fill the screen size
        contentPanelHolder.setPreferredSize(new Dimension(0, 0));

        JScrollPane scrollPane = new JScrollPane(contentPanelHolder,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(contentPanelHolder);

        changeContentPanel(new OverviewContentPanel(app, this));
    }

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        lastWeatherData = data;

        // must notify both the header and the current ContentPanel
        header.acceptWeatherData(data);
        currentContentPanel.acceptWeatherData(lastWeatherData);
    }

    @Override
    public void acceptLocation(Location location) {
        // notify the Header
        header.acceptLocation(location);
    }
}
