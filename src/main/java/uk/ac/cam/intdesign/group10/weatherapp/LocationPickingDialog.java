package uk.ac.cam.intdesign.group10.weatherapp;

import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;

public interface LocationPickingDialog {

    public void openDialog();

    public void subscribe(LocationConsumer observer);
    public void unsubscribe(LocationConsumer observer);

}
