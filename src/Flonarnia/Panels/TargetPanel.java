package Flonarnia.Panels;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Heroes.Enemy;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

/**
 * Created by Alexander on 23.04.2016.
 */
public class TargetPanel extends Panel {
    private Label groupLabel = new Label();
    private Rectangle hp = new Rectangle(100, 10, Color.RED);
    private StackPane glass = new StackPane();

    public TargetPanel(double translateX, double translateY, Pane pane){
        super(translateX, translateY, pane);
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 30 0;");
        groupLabel.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold;  -fx-padding: 0 0 15 0;");

        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(groupLabel, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(hp, Pos.BOTTOM_LEFT);

        Button hideButton = new Button("X");
        StackPane.setAlignment(hideButton, Pos.TOP_RIGHT);

        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4; -fx-padding: -2 0 -2 0;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");

        hideButton.setOnAction(event -> this.setVisible(false));

        hp.setVisible(false);
        glass.getChildren().addAll(label, groupLabel, hp, hideButton);
        glass.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        glass.setPrefSize(100, 30);
        this.getChildren().addAll(glass);

        pane.widthProperty().addListener((obs,old,newValue)->{
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
    }

    public void changeTarget(Flobject flobject, String species, String group){
        if (flobject instanceof Enemy){
            hp.setVisible(true);
            Enemy enemy = (Enemy)flobject;
            hp.widthProperty().bind(new DoubleBinding() {
                {
                    bind(enemy.health , enemy.maxHealth);
                }
                protected double computeValue() {

                    System.out.println(enemy.health.get() +  "   " + enemy.maxHealth.get()  + "  " +
                            enemy.health.get() / enemy.maxHealth.get() * 100);

                    return enemy.health.get() / enemy.maxHealth.get() * 100;
                }
            });
        }
        else {
            hp.setVisible(false);
        }

        this.setVisible(true);
        this.label.setText(species);
        this.groupLabel.setText(group);
    }
}
