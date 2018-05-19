package uk.ac.cam.intdesign.group10.weatherapp.content;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import uk.ac.cam.intdesign.group10.weatherapp.component.AppComponent;
import uk.ac.cam.intdesign.group10.weatherapp.component.HourRowImpl;
import uk.ac.cam.intdesign.group10.weatherapp.component.test.TestComponent;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.DayInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.HourInfo;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherData.WeatherType;

public class DayContentPanel extends JPanel implements ContentPanel, AppComponent {

	public List<HourRowImpl> rows;
	public Map<HourRowImpl, Double> estimations = new HashMap<>();
	private int dayIndex;

	public DayContentPanel(int dayIndex) {
		this.dayIndex = dayIndex;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// just for test, remove it
		add(new TestComponent("Day overview", Color.pink));

	}

    @Override
    public JComponent getRootComponent() {
        return this;
    }

    @Override
	public void acceptWeatherData(WeatherData data) {
		putContentInPanel(data);

	}

	public void putContentInPanel(WeatherData data) {
		JPanel suggestedTime = new JPanel();
		suggestedTime.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
		JLabel st = new JLabel("Suggested time:");
		suggestedTime.add(st);
		this.add(suggestedTime);
		initialiseHourRows(data);
		colourHourRows(data);

		for (int i = 0; i < 24; i++) {

			this.add(rows.get(i));
		}
		for (HourRowImpl hr : rows) {
			if (estimations.get(hr) == 1.0) {
				int h1 = hr.fromHour;
				int h2 = hr.toHour;
				JLabel suggestedHours = new JLabel();
				if (h1 < 10) {
					if (h2 < 10) {
						suggestedHours.setText("0" + String.valueOf(h1) + " - " + "0" + String.valueOf(h2));
					} else {

						suggestedHours.setText("0" + String.valueOf(h1) + " - " + String.valueOf(h2));

					}
				} else {
					suggestedHours.setText(String.valueOf(h1) + " - " + String.valueOf(h2));
				}
				suggestedTime.add(suggestedHours);
				break;

			}
		}

	}

	public HourInfo getHourInfo(HourRowImpl hr, WeatherData weatherData) {
		DayInfo dayInfo = weatherData.days.get(dayIndex);
		return dayInfo.hours.get(hr.fromHour);

	}

	public void initialiseHourRows(WeatherData data) {

		rows = new ArrayList<HourRowImpl>();

		for (int i = 0; i < 24; i++) {
			HourRowImpl hr = new HourRowImpl(i, i + 1);
			hr.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
			JLabel jHours = hr.hoursLabel();
			JLabel jTemperature = hr.temperatureLabel();
			HourInfo hourInfo = getHourInfo(hr, data);
			if (getHourInfo(hr, data) == null) {

				estimations.put(hr, Double.NEGATIVE_INFINITY);
				hr.add(jHours);
				rows.add(hr);

			} else {
				double hourTemperature = hourInfo.temperature;
				estimations.put(hr, hourTemperature);
				jTemperature.setText(String.format("%.1f", hourTemperature) + " \u00b0" + "C");
				hr.add(jHours);
				hr.add(jTemperature);
				hr.setBorder(new BevelBorder(BevelBorder.LOWERED));
				JLabel jImg = new JLabel();
				BufferedImage img = hourInfo.type.getImage();
				ImageIcon icon = new ImageIcon(img);
				jImg.setIcon(icon);
				hr.add(jImg);
				rows.add(hr);
			}

		}
		estimationFunction(data);

	}
    /* transfers all the temperatures that are not Double.NEGATIVE_INFINITY (corresponding to null) to an auxiliary hashmap
     * to scale them in range 0-1 so that color coding may be done
	*/
	public void estimationFunction(WeatherData data) {

		Map<HourRowImpl, Double> auxiliary = new HashMap<HourRowImpl, Double>();
        for(HourRowImpl hr : estimations.keySet()) {
        	if(estimations.get(hr) != Double.NEGATIVE_INFINITY) {
        		auxiliary.put(hr, estimations.get(hr));

        	}
        }


		double mean = 0.0;
		double variance = 0.0;
		for (HourRowImpl hr : auxiliary.keySet()) {
			// Google said it's the best temperature in which to run =)
			auxiliary.put(hr, Math.abs(15 - auxiliary.get(hr)));
			mean += auxiliary.get(hr);

		}

		mean /= auxiliary.size() ;
		for (HourRowImpl hr : auxiliary.keySet()) {

			variance += Math.pow(auxiliary.get(hr) - mean, 2);

		}
		variance /= auxiliary.size() ;
		for (HourRowImpl hr : auxiliary.keySet()) {
			auxiliary.put(hr, auxiliary.get(hr) - mean / variance);
		}

		double minimum = Collections.min(auxiliary.values());
		double maximum = Collections.max(auxiliary.values());
		for (HourRowImpl hr : auxiliary.keySet()) {
			auxiliary.put(hr, (auxiliary.get(hr) - minimum) / (maximum - minimum));
			if(getHourInfo(hr,data).type.equals(WeatherType.RAINY)) {
				auxiliary.put(hr, auxiliary.get(hr) - 0.2);
			}

		}
		minimum = Collections.min(auxiliary.values());
		maximum = Collections.max(auxiliary.values());
		for (HourRowImpl hr : auxiliary.keySet()) {
			auxiliary.put(hr, 1 - (auxiliary.get(hr) - minimum) / (maximum - minimum));
			estimations.put(hr, auxiliary.get(hr));

		}

	}

	public void colourHourRows(WeatherData data) {

		for (HourRowImpl hr : rows) {
			if (getHourInfo(hr, data) == null) {
				hr.setBackground(Color.GRAY);
			} else {

					hr.setBackground(setColor(estimations.get(hr)));
				}

			}
		}



	public Color setColor(double est) {
		// Hue
		double h = est * 0.3;
		// Saturation
		double s = 0.9;
		// Brightness
		double b = 0.9;
		return Color.getHSBColor((float) h, (float) s, (float) b);
	}

}
