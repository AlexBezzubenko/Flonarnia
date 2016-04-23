package Panels;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Alexander on 23.04.2016.
 */
public class Panel extends Pane {
    protected Label label = new Label("Flonarnia");
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public Panel(double translateX, double translateY){
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 20 0;");
        StackPane glass = new StackPane();
        StackPane.setAlignment(label, Pos.TOP_CENTER);

        Button hideButton = new Button("X");
        StackPane.setAlignment(hideButton, Pos.TOP_RIGHT);

        //hideButton.setStyle("-fx-background-color: transparent;");
        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4; -fx-padding: -2 0 -2 0;" +
                            " -fx-font-size: 14px; -fx-text-fill: yellow;");

        hideButton.setOnAction(event -> this.setVisible(false));

        glass.getChildren().addAll(label, hideButton);
        glass.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        glass.setPrefSize(100, 30);

        this.setOnMousePressed(circleOnMousePressedEventHandler);
        this.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.getChildren().addAll(glass);


    }
    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Panel)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Panel)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Panel)(t.getSource())).setTranslateX(newTranslateX);
                    ((Panel)(t.getSource())).setTranslateY(newTranslateY);
                }
            };
}
