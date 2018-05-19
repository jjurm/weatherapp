package uk.ac.cam.intdesign.group10.weatherapp.component.test;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TestFxComponent extends Pane implements FxComponent {

    public TestFxComponent(String label, Color background) {
        getChildren().add(new Label(label));
        setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public Node getRootNode() {
        return null;
    }

}
