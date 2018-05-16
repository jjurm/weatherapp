package uk.ac.cam.intdesign.group10.weatherapp.location;

public class Location {

    // example: "Cambridge, UK"

    String location;
    
    public String getLocation()
    {
    	return location;
    }

    public Location(String location) {
        this.location = location;
    }
}
