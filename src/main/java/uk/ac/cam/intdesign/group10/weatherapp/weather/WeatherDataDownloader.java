package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.io.IOException;
import java.util.function.Consumer;

public interface WeatherDataDownloader {

    public void fetchData();

    public void subscribe(Observer observer);
    public void unsubscribe(Observer observer);

    public static interface Observer extends Consumer<WeatherData> {

        public void handleError(IOException exception);

    }

}
