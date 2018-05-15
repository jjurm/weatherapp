package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MockWeatherDataProvider implements WeatherDataDownloader {

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    private List<Observer> observers = new LinkedList<>();

    @Override
    public void fetchData() {
        executor.submit(this::mockWeather);
    }

    private void mockWeather() {
        WeatherData data = new WeatherData();
        data.actualTemperature = 27;
        data.type = WeatherData.WeatherType.SUNNY;
        data.minTemperature = 21;
        data.maxTemperature = 28;
        data.feelsLikeTemperature = 29;
        data.windSpeed = 10;
        data.humidity = 0.75;
        data.precipitation = 0.12;
        data.sunrise = LocalTime.of(7, 12);
        data.sunset = LocalTime.of(20, 53);

        data.days = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            WeatherData.DayInfo day = new WeatherData.DayInfo();
            day.day = LocalDate.now().plusDays(i);
            day.type = WeatherData.WeatherType.CLOUDY;
            day.temperature = 28 + 2.5 * i;
            day.hours = IntStream.range(0, 24)
                    .mapToObj(h -> {
                        WeatherData.HourInfo hour = new WeatherData.HourInfo();
                        hour.time = LocalTime.of(h, 0);
                        hour.temperature = 18 + h * 0.4;
                        hour.type = WeatherData.WeatherType.RAINY;
                        return hour;
                    })
                    .collect(Collectors.toList());
        }

        observers.forEach(o -> o.acceptWeatherData(data));
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }
}
