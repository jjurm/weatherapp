package uk.ac.cam.intdesign.group10.weatherapp.weather;

public interface SuitabilityEstimator {

    /**
     * Returns double in the range 0-1.
     */
    public double estimate(WeatherData.HourInfo hourInfo);

}
