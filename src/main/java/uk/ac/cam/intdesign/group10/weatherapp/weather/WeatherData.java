package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.util.Date;
import java.util.List;

public class WeatherData {

    double actualTemperature;
    double minTemperature;
    double maxTemperature;
    double feelsLikeTemperature;
    double windSpeed;
    double humidity;
    double precipitation;
    Date sunrise;
    Date sunset;

    List<DayInfo> days;


    public static class DayInfo {
        Date day;
        String decription;
        double temperature;
    }

    public static class HourInfo {
        Date date;
        double temperature;
        WeatherType type;
    }

    public static enum WeatherType {
        SUNNY, RAINY, PARTLY_CLOUDY, CLOUDY, SNOWY, LIGHTNING;
    }

}
