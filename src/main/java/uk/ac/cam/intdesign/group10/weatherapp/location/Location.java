package uk.ac.cam.intdesign.group10.weatherapp.location;

import java.io.*;
import java.util.*;
import java.net.*;

public class Location {

    // example: "Cambridge, UK"
    static ArrayList<String> cities;
    
    static{
        cities = new ArrayList<>();
        String response = executePost("http://autocomplete.wunderground.com/aq?query=A", "");
        System.out.println(response);
    }

    public static String executePost(String targetURL, String urlParameters) {
      HttpURLConnection connection = null;

      try {
        //Create connection
        URL url = new URL(targetURL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", 
            "application/x-www-form-urlencoded");

        connection.setRequestProperty("Content-Length", 
            Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");  

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.close();

        //Get Response  
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
          response.append(line);
          response.append('\r');
        }
        rd.close();
        return response.toString();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      } finally {
        if (connection != null) {
          connection.disconnect();
        }
      }
    }
    private String name;
    private String country, zmw, tz, tzs, path;
    private double latitude, longitude;

    public Location(String location) {
        this.name = location;
        latitude = longitude = 0.0;
    }
    
    public Location(String name, String country, String zmw, String tz, String tzs, String path, double latitude, double longitude){
        this.name = name;
        this.country = country;
        this.zmw = zmw;
        this.tz = tz;
        this.tzs = tzs;
        this.path = path;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getName(){
        return name;
    }
}
