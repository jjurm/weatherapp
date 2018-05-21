package uk.ac.cam.intdesign.group10.weatherapp.location;

/**
 * Can be used to implement handler of the event of updated weather data.
 */
public interface LocationConsumer {

    public void acceptLocation(Location location);

}
