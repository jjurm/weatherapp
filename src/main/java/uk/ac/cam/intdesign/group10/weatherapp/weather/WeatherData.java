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
        SUNNY("Sunny", "016-sun.png"),
        RAINY("Rainy", "011-raining.png"),
        PARTLY_CLOUDY("Partly cloudy", "014-cloudy.png"),
        CLOUDY("Cloudy", "013-cloud.png"),
        SNOWY("Snowy", "011-raining.png"),
        LIGHTNING("Lightning", "010-storm.png");

        private String description;

        private BufferedImage image;

        WeatherType(String description, String filename) {
            this.description = description;

            File file = new File("assets/icons/" + filename);
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                System.out.println("Can't load " + file.toString());
                image = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
            }
        }

        public String getDescription() {
            return description;
        }

        public BufferedImage getImage() {
            return image;
        }
    }

}
