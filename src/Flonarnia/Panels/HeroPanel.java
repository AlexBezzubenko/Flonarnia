package Flonarnia.Panels;

import Flonarnia.Scenes.Flonarnia;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * Created by Alexander on 09.05.2016.
 */
public class HeroPanel extends Panel {
    private Pane pane = new Pane();

    public HeroPanel(double translateX, double translateY, Pane root){
        super(translateX, translateY, root);
        label.setText("Hero");
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold;");
        label.setTranslateX(25);

        Label levelLabel = new Label();
        levelLabel.textProperty().bind(Flonarnia.player.level.asString());
        levelLabel.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold;");
        levelLabel.setTranslateX(10);

        Rectangle endurance = new Rectangle(50, 20);
        Rectangle hp = new Rectangle(70, 20);
        Rectangle mana = new Rectangle(30, 20);

        Rectangle enduranceBack = new Rectangle(100, 20);
        Rectangle hpBack = new Rectangle(100, 20);
        Rectangle manaBack = new Rectangle(100, 20);

        LinearGradient greenGrad = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.1f, Color.rgb(25, 200, 0)),
                new Stop(1.0f, Color.rgb(0, 0, 0, .1)));
        LinearGradient redGrad = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.1f, Color.rgb(255, 4, 38)),
                new Stop(1.0f, Color.rgb(0, 0, 0, .1)));
        LinearGradient blueGrad = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.1f, Color.rgb(21, 4, 255)),
                new Stop(1.0f, Color.rgb(0, 0, 0, .1)));

        endurance.setFill(greenGrad);
        endurance.setTranslateX(5);
        endurance.setTranslateY(20);

        hp.setTranslateX(5);
        hp.setTranslateY(40);
        hp.setFill(redGrad);

        mana.setTranslateX(5);
        mana.setTranslateY(60);
        mana.setFill(blueGrad);

        enduranceBack.setFill(greenGrad);
        enduranceBack.setTranslateX(5);
        enduranceBack.setTranslateY(20);
        enduranceBack.setOpacity(0.5);

        hpBack.setTranslateX(5);
        hpBack.setTranslateY(40);
        hpBack.setFill(redGrad);
        hpBack.setOpacity(0.5);

        manaBack.setTranslateX(5);
        manaBack.setTranslateY(60);
        manaBack.setFill(blueGrad);
        manaBack.setOpacity(0.5);

        Label enduranceCapacity = new Label("EP   1212/4523");
        enduranceCapacity.setStyle("-fx-text-fill: white; -fx-font-style: italic; -fx-font-weight: bold;");
        enduranceCapacity.setTranslateX(5);
        enduranceCapacity.setTranslateY(20);

        Label healthCapacity = new Label("HP   1212/4523");
        healthCapacity.setStyle("-fx-text-fill: white; -fx-font-style: italic; -fx-font-weight: bold;");
        healthCapacity.setTranslateX(5);
        healthCapacity.setTranslateY(40);

        Label manaCapacity = new Label("MP   1212/4523");
        manaCapacity.setStyle("-fx-text-fill: white; -fx-font-style: italic; -fx-font-weight: bold;");
        manaCapacity.setTranslateX(5);
        manaCapacity.setTranslateY(60);


        enduranceCapacity.textProperty().bind(new StringBinding() {
            {
                bind(Flonarnia.player.enduranceCapacity, Flonarnia.player.enduranceMaxCapacity);
            }
            protected String computeValue() {
                endurance.setWidth(100 * Flonarnia.player.enduranceCapacity.get()
                        / Flonarnia.player.enduranceMaxCapacity.get());
                return "ED   " + Flonarnia.player.enduranceCapacity.get() +
                        "/"+ Flonarnia.player.enduranceMaxCapacity.get();
            }
        });
        healthCapacity.textProperty().bind(new StringBinding() {
            {
                bind(Flonarnia.player.healthCapacity, Flonarnia.player.healthMaxCapacity);
            }
            protected String computeValue() {
                hp.setWidth(100 * Flonarnia.player.healthCapacity.get()
                        / Flonarnia.player.healthMaxCapacity.get());
                return "HP   " + Flonarnia.player.healthCapacity.get() +
                        "/"+ Flonarnia.player.healthMaxCapacity.get();
            }
        });
        manaCapacity.textProperty().bind(new StringBinding() {
            {
                bind(Flonarnia.player.manaCapacity, Flonarnia.player.manaMaxCapacity);
            }
            protected String computeValue() {
                mana.setWidth(100 * Flonarnia.player.manaCapacity.get()
                        / Flonarnia.player.manaMaxCapacity.get());
                return "MP   " + Flonarnia.player.manaCapacity.get() +
                        "/"+ Flonarnia.player.manaMaxCapacity.get();
            }
        });


        Rectangle levelRect = new Rectangle(45, 12, Color.GRAY);
        levelRect.setTranslateX(10);
        levelRect.setTranslateY(3);
        levelRect.setOpacity(0.5);
        pane.setPrefSize(110, 80);
        pane.getChildren().addAll(enduranceBack, hpBack, manaBack);
        pane.getChildren().addAll(endurance, hp, mana);
        pane.getChildren().addAll(enduranceCapacity, healthCapacity, manaCapacity);
        pane.getChildren().addAll(levelRect, levelLabel, label);
        //pane.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        pane.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10 10 0 0;");

        this.getChildren().addAll(pane);
    }
}
