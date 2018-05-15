package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        SUNNY, RAINY, PARTLY_CLOUDY, CLOUDY, SNOWY, LIGHTNING;
    }

}
