package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import uk.ac.cam.intdesign.group10.weatherapp.WeatherApp;
import uk.ac.cam.intdesign.group10.weatherapp.location.Location;

public class ToolbarImpl extends ToolBar implements Toolbar {

    private Location location;
    private Label locationName;
    private Button changeLocation;
    public ToolbarImpl(WeatherApp weatherApp) {
        //setCenter(new TestFxComponent("Toolbar", Color.LIGHTBLUE));
        locationName = new Label();
        changeLocation = new Button();
        BufferedImage img = null;
        try{
            img = ImageIO.read(getClass().getResourceAsStream("/icons/021-edit.png"));
        }
        catch(Exception ex){
            System.out.println("Can't load 021-edit.png");
        }
        HBox p = new HBox();
        HBox.setHgrow(p, Priority.ALWAYS);
        img = resizeImage(img, BufferedImage.TYPE_INT_ARGB, 50, 50);
        ImageView imgv = new ImageView();
        imgv.setImage(SwingFXUtils.toFXImage(img, null));
        changeLocation.setGraphic(imgv);
        changeLocation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                weatherApp.openLocationPickingDialog();
            }
        });
        this.getItems().add(locationName);
        this.getItems().add(p);
        this.getItems().add(changeLocation);
    }

    @Override
    public Node getRootNode() {
        return this;
    }

    @Override
    public void acceptLocation(Location location) {
        this.location = location;
        refresh();
    }

    private void refresh() {
        locationName.setText(location.getName());
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type,
                                             Integer img_width, Integer img_height)
    {
        BufferedImage resizedImage = new BufferedImage(img_width, img_height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, img_width, img_height, null);
        g.dispose();

        return resizedImage;
    }
}
