package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.imageio.ImageIO;

public class WeatherData {

    public Double actualTemperature;
    public WeatherType type;
    public Double minTemperature;
    public Double maxTemperature;
    public Double feelsLikeTemperature;
    public Double windSpeed;
    public Double humidity; //In percentage
    public Double precipitation;
    public LocalTime sunrise;
    public LocalTime sunset;

    public List<DayInfo> days;


    public static class DayInfo {
        public LocalDate day;
        public WeatherType type;
        public Double temperature; //Max temperature for that day
        public List<HourInfo> hours;
    }

    public static class HourInfo {
        public LocalTime time;
        public Double temperature;
        public WeatherType type;
    }

    public static enum WeatherType {
        SUNNY("Sunny", "014-sun.png"),
        RAINY("Rainy", "001-rain-3.png"),
        PARTLY_CLOUDY("Partly cloudy", "015-cloudy.png"),
        CLOUDY("Cloudy", "018-cloud.png"),
        SNOWY("Snowy", "004-snowing-1.png"),
        LIGHTNING("Lightning", "013-storm.png");

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
