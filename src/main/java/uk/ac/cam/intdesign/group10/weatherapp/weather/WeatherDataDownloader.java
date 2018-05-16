package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.io.IOException;

import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

public interface WeatherDataDownloader {

    public void fetchData(Location loc);

    public void subscribe(Observer observer);
    public void unsubscribe(Observer observer);

    public static interface Observer extends WeatherDataConsumer {

        public void handleError(IOException exception);

    }

}
