package uk.ac.cam.intdesign.group10.weatherapp;

import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

public interface LocationPickingDialog {

    public void openDialog();

    public void subscribe(Observer observer);
    public void unsubscribe(Observer observer);

    public static interface Observer {

        public void locationUpdated(Location location);

    }

}
