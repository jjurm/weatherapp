package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.imageio.ImageIO;

public class WeatherData {

    public double actualTemperature;
    public WeatherType type;
    public double minTemperature;
    public double maxTemperature;
    public double feelsLikeTemperature;
    public double windSpeed;
    public double humidity;
    public double precipitation;
    public LocalTime sunrise;
    public LocalTime sunset;

    public List<DayInfo> days;


    public static class DayInfo {
        public LocalDate day;
        public WeatherType type;
        public double temperature;
        public List<HourInfo> hours;
    }

    public static class HourInfo {
        public LocalTime time;
        public double temperature;
        public WeatherType type;
    }

    public static enum WeatherType {
        SUNNY("Sunny", ""),
        RAINY("Rainy", ""),
        PARTLY_CLOUDY("Partly cloudy", ""),
        CLOUDY("Cloudy", ""),
        SNOWY("Snowy", ""),
        LIGHTNING("Lightning", "");

        private String description;
        private String filename;

        private BufferedImage image;

        WeatherType(String description, String filename) {
            this.description = description;
            this.filename = filename;

            try {
                image = ImageIO.read(new File(filename));
            } catch (IOException e) {
                image = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
            }
        }
    }

}
