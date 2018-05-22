package uk.ac.cam.intdesign.group10.weatherapp;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;
import uk.ac.cam.intdesign.group10.weatherapp.location.LocationConsumer;

public class LocationPickingDialogImpl implements LocationPickingDialog{
    private List<LocationConsumer> observers = new ArrayList<>();
    private JsonObject cities;
    private List<String> autocomplete;
    final String apiCall = "http://autocomplete.wunderground.com/aq?h=0&query=";
    OkHttpClient client = new OkHttpClient();
    private TextField nameField;
    private ComboBox autocompleteBox;

    @Override
    public void openDialog() {
        nameField = new TextField();
        autocompleteBox = new ComboBox();
        autocompleteBox.getItems().add("Cambridge, United Kingdom");
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String currentText = nameField.getText();
                try{
                    getAutocompleteSuggestions(currentText);
                } catch(IOException ex){

                }
            }
        });
        autocompleteBox.getSelectionModel().selectFirst();
        final Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Location chooser");
        dialog.setHeaderText("Please, choose your location");
        GridPane grid = new GridPane();
        grid.add(nameField, 1, 1);
        grid.add(autocompleteBox, 1, 2);
        dialog.getDialogPane().getStylesheets().add("stylesheet.css");
        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType param) {
                if(param == buttonTypeOk) {
                    Object value = autocompleteBox.getValue();
                    if (value == null) return null;
                    return value.toString();
                } else return null;
            }
        });
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            Location location = getLocation(result.get());
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

    private JsonElement readJsonFromData(String data) throws JsonIOException, JsonSyntaxException, IOException {
        JsonParser jp = new JsonParser();
        return jp.parse(data);
    }

    private void getAutocompleteSuggestions(String prefix) throws JsonIOException, java.io.IOException{
        Request request = new Request.Builder().url(getURL(prefix)).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Connection error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String r = response.body().string();
                cities = readJsonFromData(r).getAsJsonObject();
                JsonArray jsonList = cities.get("RESULTS").getAsJsonArray();

                autocomplete = new ArrayList<>();
                for(JsonElement entry : jsonList){
                    if(entry.getAsJsonObject().get("type").getAsString().equals("city"))
                        autocomplete.add(entry.getAsJsonObject().get("name").getAsString());
                }

                Platform.runLater(() -> {
                    autocompleteBox.getItems().clear();
                    autocompleteBox.getItems().addAll(autocomplete);
                    autocompleteBox.getSelectionModel().selectFirst();
                });
            }
        });
    }

    private String getURL(String prefix) {
        prefix = prefix.toLowerCase();
        try{
            return apiCall + URLEncoder.encode(prefix, "UTF-8");
        } catch(Exception e){
            return null;
        }

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
