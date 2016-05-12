package Flonarnia.Panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by Alexander on 23.04.2016.
 */
public class TargetPanel extends Panel {
    private Label groupLabel = new Label();
    public TargetPanel(double translateX, double translateY, Pane pane){
        super(translateX, translateY, pane);
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 30 0;");
        groupLabel.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 10 0;");
        StackPane glass = new StackPane();
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(groupLabel, Pos.BOTTOM_CENTER);

        Button hideButton = new Button("X");
        StackPane.setAlignment(hideButton, Pos.TOP_RIGHT);

        //hideButton.setStyle("-fx-background-color: transparent;");
        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4; -fx-padding: -2 0 -2 0;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");

        hideButton.setOnAction(event -> this.setVisible(false));

        glass.getChildren().addAll(label, groupLabel, hideButton);
        glass.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        glass.setPrefSize(100, 30);
        this.getChildren().addAll(glass);
    }
    public void changeTarget(String species, String group){
        this.setVisible(true);
        this.label.setText(species);
        this.groupLabel.setText(group);
    }
}
