package uk.ac.cam.intdesign.group10.weatherapp.weather;

/**
 * To be implemented by all components that can accept weather data (e.g. the HomeScreen -
 * needs to be updated from the received data)
 */
public interface WeatherDataConsumer {

    public void acceptWeatherData(WeatherData data);

}
