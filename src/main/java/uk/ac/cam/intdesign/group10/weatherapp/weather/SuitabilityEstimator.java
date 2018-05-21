package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.util.Map;

/**
 * Calculates how suitable a weather condition is for running.
 */
public interface SuitabilityEstimator {

    /**
     * Returns double in the range 0-1.
     */
    public Map<WeatherData.HourInfo, Double> estimate(WeatherData.DayInfo dayInfo);

}
