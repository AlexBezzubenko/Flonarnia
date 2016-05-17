package Flonarnia.Panels;

import Flonarnia.Flobjects.Portal;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.Scenes.Location.KetraOrcOutpost;
import Flonarnia.Scenes.Location.Location;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.ArrayList;


/**
 * Created by Alexander on 17.05.2016.
 */
public class TeleportPanel extends Panel{
    private ArrayList<Hyperlink> links = new ArrayList<>();

    public TeleportPanel(Pane pane){
        super(pane);
        label.setText("Teleport");
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

        for (int i = 0; i < 5; i++){
            Hyperlink hyperlink = new Hyperlink();
            hyperlink.setTranslateX(20);
            hyperlink.setTranslateY(i * 20 + 20);
            hyperlink.setBorder(null);
            links.add(hyperlink);
            this.getChildren().add(hyperlink);
        }

        pane.widthProperty().addListener((obs,old,newValue)->{
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
        pane.heightProperty().addListener((obs,old,newValue)->{
            this.setTranslateY(newValue.doubleValue() / 2 - this.getHeight() / 2);
        });
        this.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        this.setPrefSize(220, 150);
        //this.setVisible(false);

    }

    public void setLinks(ArrayList<Location> locations, Pane root){
        for(int i = 0; i < locations.size(); i++){
            Location l = locations.get(i);
            Hyperlink h = links.get(i);
            h.setText(l.getName() + " - " + l.getCost() + " Shekels");
            h.setOnAction(e->{
                root.getChildren().clear();
                Flonarnia.flobjects.clear();

                Flonarnia.flobjects.addAll(l.createContext());
                Flonarnia.flobjects.add(Flonarnia.player);
                root.getChildren().addAll(Flonarnia.flobjects);
                Portal portal = l.getPortal();
                root.getChildren().add(portal);

                Flonarnia.player.toLocation(portal, l.getName());
                h.setVisited(false);
            });
        }

    }
}
