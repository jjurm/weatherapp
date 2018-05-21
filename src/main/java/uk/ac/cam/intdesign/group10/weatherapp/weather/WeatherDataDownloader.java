package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.io.IOException;

import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

/**
 * Fetches weather data (e.g. from an online API). Uses the Observer pattern, as getting weather
 * data is an asynchronous process.
 */
public interface WeatherDataDownloader {

    public void fetchData(Location loc);

    public void subscribe(Observer observer);
    public void unsubscribe(Observer observer);

    public static interface Observer extends WeatherDataConsumer {

        public void handleError(IOException exception);

    }

}
