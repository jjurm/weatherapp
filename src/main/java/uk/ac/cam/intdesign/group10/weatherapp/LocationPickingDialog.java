package uk.ac.cam.intdesign.group10.weatherapp;

import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;

/**
 * Dialog window for picking and changing the location. Implements the Observer pattern.
 */
public interface LocationPickingDialog {

    public void openDialog();

    public void subscribe(LocationConsumer observer);
    public void unsubscribe(LocationConsumer observer);

}
