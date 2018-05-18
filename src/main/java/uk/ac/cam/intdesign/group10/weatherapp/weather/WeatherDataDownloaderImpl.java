package uk.ac.cam.intdesign.group10.weatherapp.weather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.weather.WeatherDataDownloader.Observer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class WeatherDataDownloaderImpl implements WeatherDataDownloader
{
	private ExecutorService executor = Executors.newSingleThreadExecutor();

    private List<Observer> observers = new LinkedList<>();
    
    final String signedKeyUrl = "http://api.wunderground.com/api/071e683a7cda37e3/";
    
    static Location cityLocation;
    
    @Override
    public void fetchData(Location loc)
    {
    	cityLocation = loc;
        executor.submit(this::downloadWeatherData);
    }
    
    private JsonElement readJsonFromURL(String sURL) throws JsonIOException, JsonSyntaxException, IOException
    {
        // Connect to the URL
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        
        return root;
    }
    
    Double stringToDouble(String num)
    {
    	Double res;
    	
    	try
    	{
        	res = Double.parseDouble(num);
    	}
    	catch(NumberFormatException e)
    	{
    		res = null;
    	}
    	
    	return res;
    }
    
    Integer stringToInt(String num)
    {
    	Integer res;
    	
    	try
    	{
    		res = Integer.parseInt(num);
    	}
    	catch(NumberFormatException e)
    	{
    		res = null;
    	}
    	
    	return res;
    }
    
    WeatherData.WeatherType mapWeatherType(String weatherIcon)
    {
    	if ( weatherIcon.substring(0, 2) == "nt" ) //No special icons for nighttime
    		weatherIcon = weatherIcon.substring(3, weatherIcon.length());
    	
    	switch(weatherIcon)
    	{
    		case "chanceflurries":
    			return WeatherData.WeatherType.SNOWY;
    		case "chancerain":
    			return WeatherData.WeatherType.RAINY;
    		case "chancesleet":
    			return WeatherData.WeatherType.SNOWY;
    		case "chancesnow":
    			return WeatherData.WeatherType.SNOWY;
    		case "chancetstorms":
    			return WeatherData.WeatherType.LIGHTNING;
    		case "cloudy":
    			return WeatherData.WeatherType.CLOUDY;
    		case "flurries":
    			return WeatherData.WeatherType.SNOWY;
    		case "fog":
    			return WeatherData.WeatherType.CLOUDY;
    		case "hazy":
    			return WeatherData.WeatherType.CLOUDY;
    		case "mostlycloudy":
    			return WeatherData.WeatherType.CLOUDY;
    		case "mostlysunny":
    			return WeatherData.WeatherType.PARTLY_CLOUDY;
    		case "partlycloudy":
    			return WeatherData.WeatherType.PARTLY_CLOUDY;
    		case "partlysunny":
    			return WeatherData.WeatherType.CLOUDY;
    		case "sleet":
    			return WeatherData.WeatherType.SNOWY;
    		case "rain":
    			return WeatherData.WeatherType.RAINY;
    		case "snow":
    			return WeatherData.WeatherType.SNOWY;
    		case "tstorms":
    			return WeatherData.WeatherType.LIGHTNING;
    		default:
    			return WeatherData.WeatherType.SUNNY;
    	}
    }

    private void downloadWeatherData() throws JsonIOException, JsonSyntaxException
    {
    	WeatherData data = new WeatherData();
    	JsonObject root, currentInfo;
    	JsonArray weatherList;
    	int curDay, firstHour = -1;
    	Integer yr, mth, dy, hr, mn;
    	String locationCode;
    	
    	locationCode = "zmw:" + cityLocation.getLocation();
    	
		try
		{
			//Conditions for the current day
			root = readJsonFromURL(signedKeyUrl + "conditions/q/" + locationCode + ".json").getAsJsonObject();
			currentInfo = root.get("current_observation").getAsJsonObject();
			
			data.actualTemperature = stringToDouble( currentInfo.get("temp_c").getAsString() );
			data.feelsLikeTemperature = stringToDouble( currentInfo.get("feelslike_c").getAsString() );
			data.windSpeed = stringToDouble( currentInfo.get("wind_kph").getAsString() );
			data.humidity = stringToDouble( currentInfo.get("relative_humidity").getAsString().replaceAll("%", "") );
			data.precipitation = stringToDouble( currentInfo.get("precip_today_metric").getAsString() );
			
			//Sunrise + sunset
			root = readJsonFromURL(signedKeyUrl + "astronomy/q/" + locationCode + ".json").getAsJsonObject();
			
			currentInfo = root.get("sun_phase").getAsJsonObject().get("sunrise").getAsJsonObject();
			hr = stringToInt( currentInfo.get("hour").getAsString() );
			mn = stringToInt( currentInfo.get("minute").getAsString() );
			if ( hr == null || mn == null)
				data.sunrise = null;
			else
				data.sunrise = LocalTime.of(hr, mn);
			
			currentInfo = root.get("sun_phase").getAsJsonObject().get("sunset").getAsJsonObject();
			hr = stringToInt( currentInfo.get("hour").getAsString() );
			mn = stringToInt( currentInfo.get("minute").getAsString() );
			if ( hr == null || mn == null)
				data.sunset = null;
			else
				data.sunset = LocalTime.of(hr, mn);
			
			//Next days forecast
			root = readJsonFromURL(signedKeyUrl + "forecast/q/" + locationCode + ".json").getAsJsonObject();
			weatherList = root.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray();
			
			data.minTemperature = stringToDouble( weatherList.get(0).getAsJsonObject().get("low").getAsJsonObject().get("celsius").getAsString() );
			data.maxTemperature = stringToDouble( weatherList.get(0).getAsJsonObject().get("high").getAsJsonObject().get("celsius").getAsString() );
			
			data.type = mapWeatherType( weatherList.get(0).getAsJsonObject().get("icon").getAsString() );
			
			data.days = new LinkedList<>();
			
			for (int i = 0; i < 4; i++)
			{
				data.days.add(new WeatherData.DayInfo());
				
				yr = stringToInt( weatherList.get(i).getAsJsonObject().get("date").getAsJsonObject().get("year").getAsString() );
				mth = stringToInt( weatherList.get(i).getAsJsonObject().get("date").getAsJsonObject().get("month").getAsString() );
				dy = stringToInt( weatherList.get(i).getAsJsonObject().get("date").getAsJsonObject().get("day").getAsString() );
				
				data.days.get(i).day = LocalDate.of(yr, mth, dy);
				data.days.get(i).type = mapWeatherType( weatherList.get(i).getAsJsonObject().get("icon").getAsString() );
				data.days.get(i).temperature = stringToDouble( weatherList.get(i).getAsJsonObject().get("high").getAsJsonObject().get("celsius").getAsString() );
				
				data.days.get(i).hours = new LinkedList<>();
			}
			
			//Hourly weather
			root = readJsonFromURL(signedKeyUrl + "hourly10day/q/" + locationCode + ".json").getAsJsonObject();
			weatherList = root.get("hourly_forecast").getAsJsonArray();
			
			curDay = 0;
			for (JsonElement h : weatherList)
			{
				WeatherData.HourInfo thisHour = new WeatherData.HourInfo();
				
				hr = stringToInt( h.getAsJsonObject().get("FCTTIME").getAsJsonObject().get("hour").getAsString() );
				mn = stringToInt( h.getAsJsonObject().get("FCTTIME").getAsJsonObject().get("min_unpadded").getAsString() );
				
				if ( hr == null || mn == null )
					thisHour.time = null;
				else
					thisHour.time = LocalTime.of(hr, mn);
				
				if (thisHour.time.getHour() == 0)
				{
					curDay++;
					
					if (curDay == 4)
						break;
				}
				
				if (firstHour == -1)
					firstHour = thisHour.time.getHour();
				
				thisHour.type = mapWeatherType( h.getAsJsonObject().get("icon").getAsString() );
				thisHour.temperature = stringToDouble( h.getAsJsonObject().get("temp").getAsJsonObject().get("metric").getAsString() );
				
				data.days.get(curDay).hours.add(thisHour);
			}
			
			// Pad with nulled hours for past hours in the current day
			for (int i = firstHour - 1; i >= 0; i--)
			{
				data.days.get(0).hours.add(null);
			}
			
			observers.forEach(o -> o.acceptWeatherData(data));
		}
		catch (IOException e)
		{
			observers.forEach(o -> o.handleError(e));
		}
    }

	@Override
	public void subscribe(Observer observer)
	{
		observers.add(observer);
	}

	@Override
	public void unsubscribe(Observer observer) 
	{
		observers.remove(observer);
	}
}
