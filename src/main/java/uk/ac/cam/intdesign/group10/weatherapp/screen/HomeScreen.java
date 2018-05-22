package uk.ac.cam.intdesign.group10.weatherapp.screen;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.component.Header;
import uk.ac.cam.intdesign.group10.weatherapp.content.ContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.content.DayContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.content.OverviewContentPanel;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class HomeScreen extends BorderPane implements Screen {

    private WeatherApp app;
    // we store the most recent WeatherData, so that it can be used instantly when we switch ContentPanel
    private WeatherData lastWeatherData = null;

    private final Header header;

    private int lastContentPanelIndex = 0;
    private final ScrollPane contentHolderScrollPane;
    private final HBox contentHolder;
    private final List<ScrollPane> contentScrollPanels = new ArrayList<>();
    private final List<ContentPanel> contentPanels = new ArrayList<>();

    /**
     * Call this to change the HomeScreen content (e.g. from overview to specific day)
     */
    public void changeContentPanel(int index) {
        ContentPanel contentPanel = contentPanels.get(index);

        // push last weather data to new content panel
        if (lastWeatherData != null) {
            contentPanel.acceptWeatherData(lastWeatherData);
            contentPanel.acceptWeatherData(lastWeatherData);
        }

        //scrollPane.setContent(contentPanel.getRootNode());

        // scroll all other ContentPanels to top
        for (int i = 0; i < contentScrollPanels.size(); i++) {
            if (i == lastContentPanelIndex) continue;
            contentScrollPanels.get(i).vvalueProperty().set(0);
        }

        // scroll horizontally to the correct position
        final Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(contentHolderScrollPane.hvalueProperty(), 1.0 / (contentPanels.size() - 1) * index);
        final KeyFrame kf = new KeyFrame(Duration.millis(220), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        lastContentPanelIndex = index;
    }

    private void createContentPanels() {
        contentPanels.add(new OverviewContentPanel(app, this));
        for (int i = 0; i < 4; i++) {
            contentPanels.add(new DayContentPanel(i));
        }
    }

    public HomeScreen(WeatherApp app) {
        this.app = app;

        header = new Header(app, this);
        setTop(header.getRootNode());

        contentHolder = new HBox();


        contentHolderScrollPane = new ScrollPane();
        contentHolderScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contentHolderScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // consume the horizontal scroll event (to prevent manual scrolling between screens)
        contentHolderScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaX() != 0) {
                event.consume();
            }
        });
        contentHolderScrollPane.prefHeightProperty().bind(heightProperty());
        contentHolderScrollPane.setFitToHeight(true);
        contentHolderScrollPane.setContent(contentHolder);

        setCenter(contentHolderScrollPane);

        createContentPanels();
        contentPanels.forEach(contentPanel -> {
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setContent(contentPanel.getRootNode());
            scrollPane.prefWidthProperty().bind(contentHolderScrollPane.widthProperty());
            scrollPane.getStyleClass().add("content");
            contentScrollPanels.add(scrollPane);
            contentHolder.getChildren().add(scrollPane);
        });



        //changeContentPanel(new OverviewContentPanel(app, this));
        changeContentPanel(0);
    }

    @Override
    public Pane getRootNode() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        lastWeatherData = data;

        // must notify both the header and the current ContentPanel
        header.acceptWeatherData(data);
        contentPanels.forEach(panel -> panel.acceptWeatherData(lastWeatherData));
    }

    @Override
    public void acceptLocation(Location location) {
        // notify the Header
        header.acceptLocation(location);
    }
}
