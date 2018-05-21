package uk.ac.cam.intdesign.group10.weatherapp;

import com.google.gson.*;
import javafx.scene.control.ComboBox;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class LocationPickingDialogImpl implements LocationPickingDialog{
    private List<LocationConsumer> observers = new ArrayList<>();
    private JsonObject cities;
    private List<String> autocomplete;
    final String apiCall = "http://autocomplete.wunderground.com/aq?h=0&query=";
    @Override
    public void openDialog() {
        JTextField nameField = new JTextField();
        JComboBox autocompleteBox = new JComboBox();
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fill();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fill();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fill();
            }

            private void fill(){
                String currentText = nameField.getText();
                try{
                    getAutocompleteSuggestions(currentText);
                } catch(IOException ex){

                }
                autocompleteBox.removeAllItems();
                for(String sugg : autocomplete)
                    autocompleteBox.addItem(sugg);
            }
        });
        final JComponent[] inputs = new JComponent[]{new JLabel("Choose your city:"), nameField, autocompleteBox};
        int result = JOptionPane.showConfirmDialog(null, inputs, "Pick a location", JOptionPane.PLAIN_MESSAGE);
        if(result == JOptionPane.OK_OPTION){
            Location location = getLocation(autocompleteBox.getSelectedItem().toString());
            notifyEveryone(location);
        }
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

    private Location getLocation(String text) {
        JsonArray jsonList = cities.get("RESULTS").getAsJsonArray();
        for(JsonElement entry : jsonList){
            if(entry.getAsJsonObject().get("name").getAsString().equals(text)) return parseLocation(entry.getAsJsonObject());
        }
        return null;
    }

    private JsonElement readJsonFromURL(String stringUrl) throws JsonIOException, JsonSyntaxException, IOException {
        URL url = new URL(stringUrl);
        URLConnection request = url.openConnection();
        request.connect();
        JsonParser jp = new JsonParser();
        return jp.parse(new InputStreamReader((InputStream) request.getContent()));
    }

    private void getAutocompleteSuggestions(String prefix) throws JsonIOException, java.io.IOException{
        cities = readJsonFromURL(getURL(prefix)).getAsJsonObject();
        //System.out.println(cities.toString());
        JsonArray jsonList = cities.get("RESULTS").getAsJsonArray();
        autocomplete = new ArrayList<>();
        for(JsonElement entry : jsonList){
            if(entry.getAsJsonObject().get("type").getAsString().equals("city"))
                autocomplete.add(entry.getAsJsonObject().get("name").getAsString());
        }
        for(String s : autocomplete) System.out.println(s);
    }

    private String getURL(String prefix) {
        prefix = prefix.toLowerCase();
        return apiCall + prefix;
    }

    private void notifyEveryone(Location location){
        //location.debug();
        for(LocationConsumer obs : observers){
            obs.acceptLocation(location);
        }
    }

    @Override
    public void subscribe(LocationConsumer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(LocationConsumer observer) {
        observers.remove(observer);
    }
}
