package Panels;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 * Created by Alexander on 23.04.2016.
 */
public class Panel extends Pane {
    protected Label label = new Label("Flonarnia");
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public Panel(double translateX, double translateY){
        this.setOnMousePressed(circleOnMousePressedEventHandler);
        this.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
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
    public void Show(){
        this.setVisible(true);
    }
    public void Hide(){
        this.setVisible(false);
    }
}
