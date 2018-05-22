package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.time.LocalDate;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;

public class DaySummaryImpl extends GridPane implements DaySummary {
    private Label weatherTypeLabel = new Label();
    private Label temperatureLabel = new Label();
    private ImageView image = new ImageView();
    private Label dateLabel = new Label();
    final String DEGREE  = "\u00b0" + "C";
    private int dayIndex;
    public DaySummaryImpl(int dayIndex){
        setHgap(20);
        this.dayIndex = dayIndex;
        image.setFitHeight(72);
        image.setFitWidth(72);
        getStyleClass().add("day-summary");
        GridPane.setConstraints(dateLabel, 0, 0);
        GridPane.setConstraints(image, 1, 0);
        GridPane.setConstraints(weatherTypeLabel, 0, 1);
        GridPane.setConstraints(temperatureLabel, 1, 1);
        getChildren().addAll(weatherTypeLabel, temperatureLabel, image, dateLabel);

    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        weatherTypeLabel.setText(data.type.getDescription());
        temperatureLabel.setText(data.maxTemperature.toString() + DEGREE);
        LocalDate day = data.days.get(dayIndex).day;
        dateLabel.setText(day.getDayOfWeek().toString() + ", " + day.getDayOfMonth() + " " + day.getMonth().toString());
        image.setImage(SwingFXUtils.toFXImage(data.type.getImage(), null));
    }

    @Override
    public Node getRootNode() {
        return this;
    }
}
