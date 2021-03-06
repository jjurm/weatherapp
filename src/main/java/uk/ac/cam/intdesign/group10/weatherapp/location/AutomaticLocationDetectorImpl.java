package uk.ac.cam.intdesign.group10.weatherapp.location;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AutomaticLocationDetectorImpl implements AutomaticLocationDetector {
    final static String apiCall = "http://autocomplete.wunderground.com/aq?h=0&query=";
    private static JsonArray cities;
    private double latitude, longitude;
    private String apiName;
    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lon1, double lat2,
                                  double lon2) {

        //final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return c;
    }

    @Override
    public Location detectLocation() {
        getLocation();
        try{
            getAutocompleteSuggestions();
        } catch(IOException ex){

        }
        Location loc = closestLocation();
        return loc;
    }

    private Location closestLocation() {
            double closestDist = Double.MAX_VALUE;
        Location best = null;
        for(JsonElement city : cities){
            double dist = distance(
                    city.getAsJsonObject().get("lat").getAsDouble(),
                    city.getAsJsonObject().get("lon").getAsDouble(),
                    latitude,
                    longitude
            );
            if(dist < closestDist){
                closestDist = dist;
                best = parseLocation(city.getAsJsonObject());
            }
        }
        return best;
    }

    private Location parseLocation(JsonObject jsonObject){
        return new Location(
                jsonObject.get("name").getAsString(),
                jsonObject.get("c").getAsString(),
                jsonObject.get("zmw").getAsString(),
                jsonObject.get("tz").getAsString(),
                jsonObject.get("tzs").getAsString(),
                jsonObject.get("l").getAsString(),
                jsonObject.get("lat").getAsDouble(),
                jsonObject.get("lon").getAsDouble()
        );
    }

    private void getLocation() {
        JsonObject locJSON;
        try{
            locJSON = readJsonFromURL("http://ip-api.com/json").getAsJsonObject();
        } catch(Exception ex){
            return;
        }
        latitude = locJSON.get("lat").getAsDouble();
        longitude = locJSON.get("lon").getAsDouble();
        apiName = locJSON.get("city").getAsString();
    }


    private static JsonElement readJsonFromURL(String stringUrl) throws JsonIOException, JsonSyntaxException, IOException {
        URL url = new URL(stringUrl);
        URLConnection request = url.openConnection();
        request.connect();
        JsonParser jp = new JsonParser();
        return jp.parse(new InputStreamReader((InputStream) request.getContent()));
    }

    private void getAutocompleteSuggestions() throws JsonIOException, java.io.IOException{
        cities = readJsonFromURL(apiCall + apiName).getAsJsonObject().get("RESULTS").getAsJsonArray();
    }
}
