package uk.ac.cam.intdesign.group10.weatherapp.location;

import java.io.*;
import java.util.*;
import java.net.*;

public class Location {
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
    public String getLocation(){
        return zmw;
    }
    public void debug(){
        System.out.println(name + " " + country + " " + zmw + " " + tz + " " + tzs + " " + path + " " + latitude + " " + longitude);
    }
}
