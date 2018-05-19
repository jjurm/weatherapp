package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SuitabilityEstimatorImpl implements SuitabilityEstimator {
    @Override
    public Map<WeatherData.HourInfo, Double> estimate(WeatherData.DayInfo dayInfo) {
        Map<WeatherData.HourInfo, Double> auxiliary = new HashMap<>();
        for (WeatherData.HourInfo info : dayInfo.hours) {
            if (info != null) {
                auxiliary.put(info, info.temperature);
            }
        }

        double mean = 0.0;
        double variance = 0.0;
        for (WeatherData.HourInfo hr : auxiliary.keySet()) {
            // Google said it's the best temperature in which to run =)
            auxiliary.put(hr, Math.abs(15 - auxiliary.get(hr)));
            mean += auxiliary.get(hr);
        }

        mean /= auxiliary.size();
        for (WeatherData.HourInfo hr : auxiliary.keySet()) {

            variance += Math.pow(auxiliary.get(hr) - mean, 2);

        }
        variance /= auxiliary.size();
        for (WeatherData.HourInfo hr : auxiliary.keySet()) {
            auxiliary.put(hr, auxiliary.get(hr) - mean / variance);
        }

        double minimum = Collections.min(auxiliary.values());
        double maximum = Collections.max(auxiliary.values());
        for (WeatherData.HourInfo hr : auxiliary.keySet()) {
            auxiliary.put(hr, (auxiliary.get(hr) - minimum) / (maximum - minimum));
            if (hr.type.equals(WeatherData.WeatherType.RAINY)) {
                auxiliary.put(hr, auxiliary.get(hr) - 0.2);
            }

        }
        minimum = Collections.min(auxiliary.values());
        maximum = Collections.max(auxiliary.values());
        for (WeatherData.HourInfo hr : auxiliary.keySet()) {
            auxiliary.put(hr, 1 - (auxiliary.get(hr) - minimum) / (maximum - minimum));
        }

        return auxiliary;
    }
}
