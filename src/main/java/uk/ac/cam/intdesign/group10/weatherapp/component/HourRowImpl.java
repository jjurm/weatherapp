package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.util.Map;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;

public class HourRowImpl extends HBox implements HourRow {

    private int fromHour;
    private int toHour;
    private Label jTemperature;
    private ImageView imgView;

    public HourRowImpl(int startHourOfROw, int endHourOfRow) {
        fromHour = startHourOfROw;
        toHour = endHourOfRow;

        getStyleClass().add("hourrow");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(40);

        createComponents();
    }

    private void createComponents() {
        Label jHours = new Label();
        jHours.setText(String.format("%02d - %02d", fromHour, toHour));
        jTemperature = new Label();
        imgView = new ImageView();
        getChildren().add(jHours);
        getChildren().add(jTemperature);
        imgView.setFitWidth(32);
        imgView.setFitHeight(32);
        getChildren().add(imgView);
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    public void acceptWeatherData(WeatherData.DayInfo data, Map<HourInfo, Double> estimations) {
        HourInfo hourInfo = data.hours.get(fromHour);
        Double estimatedValue = estimations.get(hourInfo);

        if (hourInfo != null) {
            double hourTemperature = hourInfo.temperature;
            jTemperature.setText(String.format("%.1f", hourTemperature) + " \u00b0" + "C");
            imgView.setImage(SwingFXUtils.toFXImage(hourInfo.type.getImage(), null));
        }

        colourRow(estimatedValue);
    }

    private void colourRow(Double estimatedValue) {
        if (estimatedValue == null) {
            setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            setBackground(new Background(new BackgroundFill(setColor(estimatedValue), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    private Color setColor(double est) {
        // Hue
        double h = est * 0.3;
        // Saturation
        double s = 0.9;
        // Brightness
        double b = 0.9;
        // hue is in degrees
        return Color.hsb(h * 360, s, b);
    }

}
