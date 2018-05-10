package uk.ac.cam.intdesign.group10.weatherapp.location;

public class AutomaticLocationDetectorImpl implements AutomaticLocationDetector {
    @Override
    public Location detectLocation() {
        return new Location("Cambridge, UK");
    }
}
