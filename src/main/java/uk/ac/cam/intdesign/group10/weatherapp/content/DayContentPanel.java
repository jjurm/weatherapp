package uk.ac.cam.intdesign.group10.weatherapp.content;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uk.ac.cam.intdesign.group10.weatherapp.component.HourRowImpl;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.DayInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.WeatherType;

public class DayContentPanel extends VBox implements ContentPanel {

    public List<HourRowImpl> rows;
    public Map<HourRowImpl, Double> estimations = new HashMap<>();
    private int dayIndex;

    public DayContentPanel(int dayIndex) {
        this.dayIndex = dayIndex;

        getStyleClass().add("panel-day");
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptWeatherData(WeatherData data) {
        putContentInPanel(data);

    }

    public void putContentInPanel(WeatherData data) {
        FlowPane suggestedTime = new FlowPane();
        Label st = new Label("Suggested time:");
        suggestedTime.getChildren().add(st);
        getChildren().add(suggestedTime);

        initialiseHourRows(data);
        colourHourRows(data);

        for (int i = 0; i < 24; i++) {

            this.getChildren().add(rows.get(i));
        }
        for (HourRowImpl hr : rows) {
            if (estimations.get(hr) == 1.0) {
                int h1 = hr.fromHour;
                int h2 = hr.toHour;
                Label suggestedHours = new Label();
                if (h1 < 10) {
                    if (h2 < 10) {
                        suggestedHours.setText("0" + String.valueOf(h1) + " - " + "0" + String.valueOf(h2));
                    } else {

                        suggestedHours.setText("0" + String.valueOf(h1) + " - " + String.valueOf(h2));

                    }
                } else {
                    suggestedHours.setText(String.valueOf(h1) + " - " + String.valueOf(h2));
                }
                suggestedTime.getChildren().add(suggestedHours);
                break;

            }
        }

    }

    public HourInfo getHourInfo(HourRowImpl hr, WeatherData weatherData) {
        DayInfo dayInfo = weatherData.days.get(dayIndex);
        return dayInfo.hours.get(hr.fromHour);

    }

    public void initialiseHourRows(WeatherData data) {

        rows = new ArrayList<HourRowImpl>();

        for (int i = 0; i < 24; i++) {
            HourRowImpl hr = new HourRowImpl(i, i + 1);

            Label jHours = hr.hoursLabel();
            Label jTemperature = hr.temperatureLabel();
            HourInfo hourInfo = getHourInfo(hr, data);
            if (getHourInfo(hr, data) == null) {

                estimations.put(hr, Double.NEGATIVE_INFINITY);
                hr.getChildren().add(jHours);
                rows.add(hr);

            } else {
                double hourTemperature = hourInfo.temperature;
                estimations.put(hr, hourTemperature);
                jTemperature.setText(String.format("%.1f", hourTemperature) + " \u00b0" + "C");
                hr.getChildren().add(jHours);
                hr.getChildren().add(jTemperature);

                BufferedImage img = hourInfo.type.getImage();
                ImageView imgView = new ImageView(SwingFXUtils.toFXImage(img, null));
                imgView.setFitWidth(32);
                imgView.setFitHeight(32);
                hr.getChildren().add(imgView);
                rows.add(hr);
            }

        }
        estimationFunction(data);

    }

    /* transfers all the temperatures that are not Double.NEGATIVE_INFINITY (corresponding to null) to an auxiliary hashmap
     * to scale them in range 0-1 so that color coding may be done
     */
    public void estimationFunction(WeatherData data) {

        Map<HourRowImpl, Double> auxiliary = new HashMap<HourRowImpl, Double>();
        for (HourRowImpl hr : estimations.keySet()) {
            if (estimations.get(hr) != Double.NEGATIVE_INFINITY) {
                auxiliary.put(hr, estimations.get(hr));

            }
        }


        double mean = 0.0;
        double variance = 0.0;
        for (HourRowImpl hr : auxiliary.keySet()) {
            // Google said it's the best temperature in which to run =)
            auxiliary.put(hr, Math.abs(15 - auxiliary.get(hr)));
            mean += auxiliary.get(hr);

        }

        mean /= auxiliary.size();
        for (HourRowImpl hr : auxiliary.keySet()) {

            variance += Math.pow(auxiliary.get(hr) - mean, 2);

        }
        variance /= auxiliary.size();
        for (HourRowImpl hr : auxiliary.keySet()) {
            auxiliary.put(hr, auxiliary.get(hr) - mean / variance);
        }

        double minimum = Collections.min(auxiliary.values());
        double maximum = Collections.max(auxiliary.values());
        for (HourRowImpl hr : auxiliary.keySet()) {
            auxiliary.put(hr, (auxiliary.get(hr) - minimum) / (maximum - minimum));
            if (getHourInfo(hr, data).type.equals(WeatherType.RAINY)) {
                auxiliary.put(hr, auxiliary.get(hr) - 0.2);
            }

        }
        minimum = Collections.min(auxiliary.values());
        maximum = Collections.max(auxiliary.values());
        for (HourRowImpl hr : auxiliary.keySet()) {
            auxiliary.put(hr, 1 - (auxiliary.get(hr) - minimum) / (maximum - minimum));
            estimations.put(hr, auxiliary.get(hr));

        }

    }

    public void colourHourRows(WeatherData data) {

        for (HourRowImpl hr : rows) {
            if (getHourInfo(hr, data) == null) {
                hr.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                hr.setBackground(new Background(new BackgroundFill(setColor(estimations.get(hr)), CornerRadii.EMPTY, Insets.EMPTY)));
            }

        }
    }


    public Color setColor(double est) {
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
