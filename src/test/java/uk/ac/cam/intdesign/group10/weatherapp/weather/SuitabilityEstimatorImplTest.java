package uk.ac.cam.intdesign.group10.weatherapp.weather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class SuitabilityEstimatorImplTest {

    private static final double EPSILON = 0.001;

    private SuitabilityEstimator estimator;
    private WeatherData.DayInfo dayInfo;

    @Before
    public void setup() {
        estimator = new SuitabilityEstimatorImpl();

        dayInfo = new WeatherData.DayInfo();
        dayInfo.hours = new ArrayList<>();
    }

    @Test
    public void estimate_isLowerWhenRainy() {
        // First hour with sunny weather
        WeatherData.HourInfo h1 = new WeatherData.HourInfo();
        h1.time = LocalTime.of(0, 0);
        h1.type = WeatherData.WeatherType.SUNNY;
        h1.temperature = 20.0;

        // Second hour with rainy weather
        WeatherData.HourInfo h2 = new WeatherData.HourInfo();
        h2.time = LocalTime.of(0, 0);
        h2.type = WeatherData.WeatherType.RAINY;
        h2.temperature = 20.0;

        dayInfo.hours.add(h1);
        dayInfo.hours.add(h2);


        // Run the estimator
        Map<WeatherData.HourInfo, Double> estimate = estimator.estimate(dayInfo);


        // Check that sunny weather results in higher suitability value
        Assert.assertThat("Rainy weather not taken into account",
                estimate.get(h1) - estimate.get(h2), is(greaterThan(EPSILON)));
    }

    @Test
    public void estimate_isLowerWhenFreezing() {
        // First hour with sunny weather
        WeatherData.HourInfo h1 = new WeatherData.HourInfo();
        h1.time = LocalTime.of(0, 0);
        h1.type = WeatherData.WeatherType.SUNNY;
        h1.temperature = 20.0;

        // Second hour with rainy weather
        WeatherData.HourInfo h2 = new WeatherData.HourInfo();
        h2.time = LocalTime.of(0, 0);
        h2.type = WeatherData.WeatherType.SUNNY;
        h2.temperature = 0.0;


        dayInfo.hours.add(h1);
        dayInfo.hours.add(h2);


        // Run the estimator
        Map<WeatherData.HourInfo, Double> estimate = estimator.estimate(dayInfo);


        // Check that sunny weather results in higher suitability value
        Assert.assertThat("Freezing weather not taken into account",
                estimate.get(h1) - estimate.get(h2), is(greaterThan(EPSILON)));
    }

}
