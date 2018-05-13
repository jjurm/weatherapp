package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.util.Date;
import java.util.List;

public class WeatherData {

    public double actualTemperature;
    public double minTemperature;
    public double maxTemperature;
    public double feelsLikeTemperature;
    public double windSpeed;
    public double humidity;
    public double precipitation;
    public Date sunrise;
    public Date sunset;

    public List<DayInfo> days;


    public static class DayInfo {
        public Date day;
        public String decription;
        public double temperature;
    }

    public static class HourInfo {
        public Date date;
        public double temperature;
        public WeatherType type;
    }

    public static enum WeatherType {
        SUNNY, RAINY, PARTLY_CLOUDY, CLOUDY, SNOWY, LIGHTNING;
    }

}
