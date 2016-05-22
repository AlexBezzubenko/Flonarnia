package Flonarnia.Panels;

import Flonarnia.Scenes.Flonarnia;
import Flonarnia.Scenes.Location.Location;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Created by Alexander on 20.05.2016.
 */
public class ToVillagePanel extends Panel{
    public ToVillagePanel(Pane pane, ArrayList<Location> locations){
        super(pane);
        Button acceptButton = new Button("To Village");
        acceptButton.setStyle("-fx-background-color: grey;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");
        acceptButton.setOnAction(event -> {
            this.setVisible(false);
            Flonarnia.player.toVillage(locations, true);
            if(!Flonarnia.player.isAlive())
                Flonarnia.player.resurrect();
        });
        acceptButton.setTranslateX(30);
        acceptButton.setTranslateY(30);
        acceptButton.setPrefSize(90, 20);
        this.setOnMouseClicked(e->{
            if (Flonarnia.player.isAlive())
                this.setVisible(false);
        });

        this.getChildren().addAll(acceptButton);


        pane.widthProperty().addListener((obs,old,newValue)->{
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
        pane.heightProperty().addListener((obs,old,newValue)->{
            this.setTranslateY(newValue.doubleValue() * 0.4 - this.getHeight() / 2);
        });
        this.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        this.setPrefSize(150, 90);
        this.setVisible(false);
    }
}
