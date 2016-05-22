package Flonarnia.Panels;

import Flonarnia.Scenes.Flonarnia;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;

/**
 * Created by Alexander on 21.05.2016.
 */
public class ShamanPanel extends Panel {
    public ShamanPanel(Pane pane){
        super(pane);
        label.setText("Buffer");
        label.setFont(new Font("italic", 13));
        label.setTranslateX(85);


        Button hideButton = new Button("X");
        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");
        hideButton.setOnAction(event -> this.setVisible(false));
        hideButton.setTranslateX(220 - 30);
        hideButton.setPrefSize(5, 5);

        Button recoverHpButton = new Button("Recover HP");
        Button recoverMpButton = new Button("Recover MP");
        Button recoverEdButton = new Button("Recover ED");

        recoverHpButton.setMaxWidth(Double.MAX_VALUE);
        recoverMpButton.setMaxWidth(Double.MAX_VALUE);
        recoverEdButton.setMaxWidth(Double.MAX_VALUE);

        recoverHpButton.setOnAction(e->{
            if(Flonarnia.player.shekelAmount.get() - 1000 >= 0) {
                Flonarnia.player.healthCapacity.set(Flonarnia.player.healthMaxCapacity.get());
                Flonarnia.player.shekelAmount.set(Flonarnia.player.shekelAmount.get() - 1000);
            }
        });
        recoverMpButton.setOnAction(e->{
            if(Flonarnia.player.shekelAmount.get() - 1000 >= 0) {
                Flonarnia.player.manaCapacity.set(Flonarnia.player.manaMaxCapacity.get());
                Flonarnia.player.shekelAmount.set(Flonarnia.player.shekelAmount.get() - 1000);
            }
        });
        recoverEdButton.setOnAction(e->{
            if(Flonarnia.player.shekelAmount.get() - 1000 >= 0) {
                Flonarnia.player.enduranceCapacity.set(Flonarnia.player.enduranceMaxCapacity.get());
                Flonarnia.player.shekelAmount.set(Flonarnia.player.shekelAmount.get() - 1000);
            }
        });

        File f = new File("src/Flonarnia/Panels/shaman.css");
        this.getStylesheets().clear();
        this.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(recoverHpButton, recoverEdButton, recoverMpButton);
        vBox.setSpacing(7);
        vBox.setTranslateX(40);
        vBox.setTranslateY(25);
        vBox.setPrefWidth(140);

        Label costLabel = new Label("Cost: 1000 shekels");
        costLabel.setTranslateX(50);
        costLabel.setTranslateY(160);

        this.getChildren().addAll(label, hideButton, vBox, costLabel);

        pane.widthProperty().addListener((obs,old,newValue)->{
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
        pane.heightProperty().addListener((obs,old,newValue)->{
            this.setTranslateY(newValue.doubleValue() / 2 - this.getHeight() / 2);
        });
        this.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        this.setPrefSize(220, 180);
        this.setVisible(false);
    }
}
