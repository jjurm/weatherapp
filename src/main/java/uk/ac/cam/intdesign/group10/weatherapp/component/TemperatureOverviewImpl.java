package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class TemperatureOverviewImpl extends VBox implements TemperatureOverview {
    final String DEGREE  = "\u00b0" + "C";
    private RightNowLabel temperatureLabel = new RightNowLabel(0);
    private WeatherDetails weatherDetails = new WeatherDetailsImpl();

    public TemperatureOverviewImpl(){
        getChildren().add(temperatureLabel);
        getChildren().add(weatherDetails.getRootNode());
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        temperatureLabel.update(data.actualTemperature, data.type.getImage());
        weatherDetails.acceptWeatherData(data);
    }
    private class RightNowLabel extends GridPane {
        private Label rightNowLabel = new Label("Right Now:");
        private Label tempLabel = new Label();
        private Label imageLabel = new Label();
        public RightNowLabel(double temp) {
            tempLabel.getStyleClass().add("temp-rightnow");
            GridPane.setConstraints(rightNowLabel, 0, 0);
            GridPane.setConstraints(tempLabel, 0, 1);
            GridPane.setConstraints(imageLabel, 1, 1);
            getChildren().addAll(rightNowLabel, tempLabel, imageLabel);
        }
        public void update(double temp, BufferedImage weatherType){
            tempLabel.setText(temp + DEGREE);
            ImageView imgView = new ImageView(SwingFXUtils.toFXImage(weatherType, null));
            imageLabel.setGraphic(imgView);
        }
    }
}
