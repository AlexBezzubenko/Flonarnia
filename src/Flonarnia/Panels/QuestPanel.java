package Flonarnia.Panels;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Created by Alexander on 22.05.2016.
 */
public class QuestPanel extends Panel {
    public QuestPanel(Pane pane) {
        super(pane);
        label.setText("Quest");
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold;");
        label.setFont(new Font("italic", 13));
        label.setTranslateX(85);


        Button hideButton = new Button("X");
        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");
        hideButton.setOnAction(event -> this.setVisible(false));
        hideButton.setTranslateX(220 - 30);
        hideButton.setPrefSize(5, 5);
        this.getChildren().addAll(label, hideButton);

        Text text = new Text("Hi, dear. ");

        Hyperlink hyperlink = new Hyperlink("help");
        hyperlink.setTranslateX(90);
        hyperlink.setTranslateY(150);
        hyperlink.setBorder(null);
        hyperlink.setOnAction(e -> {
            hyperlink.setVisited(false);
        });

        this.getChildren().addAll(text, hyperlink);

        pane.widthProperty().addListener((obs, old, newValue) -> {
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
        pane.heightProperty().addListener((obs, old, newValue) -> {
            this.setTranslateY(newValue.doubleValue() / 2 - this.getHeight() / 2);
        });
        this.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        this.setPrefSize(220, 180);
        this.setVisible(false);
    }
}
