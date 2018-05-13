package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.io.IOException;

public interface WeatherDataDownloader {

    public void fetchData();

    public void subscribe(Observer observer);
    public void unsubscribe(Observer observer);

    public static interface Observer extends WeatherDataConsumer {

        public void handleError(IOException exception);

    }

}
