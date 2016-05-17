package Flonarnia.Scenes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.Portal;
import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.MovableFlobject;
import Flonarnia.Heroes.NPC;
import Flonarnia.Heroes.Player;
import Flonarnia.Panels.*;
import Flonarnia.Scenes.Location.Dion;
import Flonarnia.Scenes.Location.KetraOrcOutpost;
import Flonarnia.Scenes.Location.Location;
import Flonarnia.tools.Sort;
import Flonarnia.tools.SpriteAnimation;
import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Alexander on 24.03.2016.
 */
public class Flonarnia {
    private double APP_W;
    private double APP_H;
    private Stage primaryStage;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    public static Player player;

    private int velocity = 4;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    public static Pane backgroundRoot = new Pane();
    public static Pane foregroundRoot = new Pane();
    public static TargetPanel targetPanel;
    public static TradePanel tradePanel;
    public static TeleportPanel teleportPanel;

    public static LogPanel logPanel;
    public static SkillPanel skillPanel;

    public static ArrayList<Flobject> flobjects = new ArrayList<>();
    private static PerformanceTracker tracker;
    private ArrayList<ImageView> gv = new ArrayList<>();
    private Scene mainScene;
    private Label frames = new Label();
    private Portal portal;
    private Location currentLocation;
    private ArrayList<Location> locations = new ArrayList<>();

    public Flonarnia(Stage primaryStage){
        this.primaryStage = primaryStage;
        APP_W = primaryStage.getWidth();
        APP_H = primaryStage.getHeight();
        double x = APP_W / 4 + 600 + 1000;
        double y = APP_H / 4 + 600;
        player = new Player(x, y);
        gameRoot.setLayoutX( -(x - APP_W / 2));
        gameRoot.setLayoutY( -(y - APP_H / 2));

        player.translateXProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            gameRoot.setLayoutX( -(offset - APP_W / 2));

            while (offset > gv.get(4).getTranslateX() + APP_W){
                for (ImageView imageView: gv){
                    imageView.setTranslateX(imageView.getTranslateX() + APP_W);
                }
            }

            while (offset < gv.get(4).getTranslateX()){
                for (ImageView imageView: gv){
                    imageView.setTranslateX(imageView.getTranslateX() - APP_W);
                }
            }
        });
        player.translateYProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            gameRoot.setLayoutY( -(offset - APP_H / 2));

            while (offset < gv.get(4).getTranslateY()){
                for (ImageView imageView: gv){
                    imageView.setTranslateY(imageView.getTranslateY() - APP_H);
                }
            }
            while (offset > gv.get(4).getTranslateY() + APP_H){
                for (ImageView imageView: gv){
                    imageView.setTranslateY(imageView.getTranslateY() + APP_H);
                }
            }
        });
        appRoot.widthProperty().addListener((obs,old,newValue)->{
            APP_W = newValue.doubleValue();
            double offset = player.getTranslateX();
            gameRoot.setLayoutX( -(offset - APP_W / 2));
            frames.setTranslateX(APP_W - frames.getWidth() - 10);
        });
        appRoot.heightProperty().addListener((obs,old,newValue)->{
            APP_H = newValue.doubleValue();
            double offset = player.getTranslateY();
            gameRoot.setLayoutY( -(offset - APP_H / 2));
        });

        locations.add(new Dion());
        locations.add(new KetraOrcOutpost());
    }

    public void run(){
        String musicFile = "/Flonarnia/src/Flonarnia/tools/res/main_theme.mp3";
        Thread t = new Thread(() -> {
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(0.05);
            mediaPlayer.play();
        });
        //t.start();
        createContext();
        logPanel.addLine("Welcome to Flonarnia!");
        tracker = PerformanceTracker.getSceneTracker(mainScene);
        frames.setFont(Font.font ("Verdana", 20));
        frames.setTranslateX(700);
        frames.setTextFill(Color.YELLOW);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                enemyActivate();
                update();

                flobjects.sort(new Sort());

                for (Flobject f: flobjects){
                    f.toFront();
                }
                if (now % 500 == 0){
                    float fps = tracker.getAverageFPS();
                    tracker.resetAverageFPS();
                    frames.setText(String.valueOf((int)fps));
                    System.out.println(player.getTranslateX() + " " + player.getTranslateY());
                    //player.regenerate();
                }
            }
        };
        timer.start();
    }

    private void createContext(){
        Image grassImage = new Image(getClass().getResourceAsStream("/Flonarnia/tools/res/map.png"));
        ImageView grassView = new ImageView(grassImage);
        grassView.setViewport(new Rectangle2D(0, 0, APP_W, APP_H));

        double gvTranslateX = -APP_W;
        double gvTranslateY = -APP_H * 2;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0){
                gvTranslateX = -APP_W;
                gvTranslateY += APP_H;
            }
            ImageView gvItem = new ImageView(grassImage);
            gvItem.setTranslateX(gvTranslateX);
            gvItem.setTranslateY(gvTranslateY);
            gv.add(gvItem);
            gvTranslateX += APP_W;
        }

        flobjects.add(player);

        currentLocation = locations.get(0);
        flobjects.addAll(currentLocation.createContext());

        backgroundRoot.getChildren().addAll(gv);
        foregroundRoot.getChildren().addAll(flobjects);

        player.inventoryPanel.setTranslateX(APP_W / 3);
        player.inventoryPanel.setTranslateY(APP_H / 4);

        gameRoot.getChildren().addAll(backgroundRoot,foregroundRoot);
        appRoot.getChildren().addAll(gameRoot, frames);

        logPanel = new LogPanel(10, APP_H - 200, appRoot);
        HeroPanel heroPanel = new HeroPanel(10, 10, appRoot);
        targetPanel = new TargetPanel(APP_W / 2, 0, appRoot);
        tradePanel = new TradePanel(APP_W / 3, APP_H / 4, appRoot);
        teleportPanel = new TeleportPanel(appRoot);
        teleportPanel.setLinks(locations, foregroundRoot);


        //Scene mainScene = new Scene(appRoot, APP_W, APP_W);
        mainScene = new Scene(appRoot, APP_W, APP_W);
        primaryStage.setScene(mainScene);
        mainScene.setOnKeyPressed(event->keys.put(event.getCode(),true));
        mainScene.setOnKeyReleased(event->keys.put(event.getCode(), false));



        portal = currentLocation.getPortal();
        foregroundRoot.getChildren().add(portal);
        skillPanel = new SkillPanel(appRoot, player, portal);
        player.Bind(skillPanel);
    }

    private void enemyActivate(){
        flobjects.stream().filter(f -> f instanceof MovableFlobject).forEach(f -> {
            MovableFlobject m = (MovableFlobject) f;
            if (m.getContext() != null) {
                m.getContext().move();
            }
        });
    }
    private void update(){
        int n = 40000;
        if (isPressed(KeyCode.UP) || isPressed(KeyCode.W)) {
            player.moveY(-velocity);
        } else if (isPressed(KeyCode.DOWN) || isPressed(KeyCode.S)) {
            player.moveY(velocity);
        } else if (isPressed(KeyCode.RIGHT) || isPressed(KeyCode.D)) {
            player.moveX(velocity);
        } else if (isPressed(KeyCode.LEFT) || isPressed(KeyCode.A)) {
            player.moveX(-velocity);
        }
        else if (isPressed(KeyCode.F) || isPressed(KeyCode.F1)){
            player.attack();
        }
        else if (isPressed(KeyCode.Q)){
            primaryStage.close();
        }
        else if (isPressed(KeyCode.R)){
            //skillPanel.cells.get(4).incItemAmount(1);
            //inventoryPanel.shekelAmount.setValue(inventoryPanel.shekelAmount.getValue() + 100);
            //player.addToInventory(new InventoryItem("mana", "poison", 3));
            //System.out.println("try to add");
            player.shekelAmount.set(player.shekelAmount.getValue() + 2000);
        }

        else if (isPressed(KeyCode.NUMPAD8)){
            player.setTranslateY(player.getTranslateY() - n);
        }
        else if (isPressed(KeyCode.NUMPAD2)){
            player.setTranslateY(player.getTranslateY() + n);
        }
        else if (isPressed(KeyCode.NUMPAD4)){
            player.setTranslateX(player.getTranslateX() - n);
        }
        else if (isPressed(KeyCode.NUMPAD6)){
            player.setTranslateX(player.getTranslateX() + n);
        }
        else {
            player.stop();
        }


        if (isReleased(KeyCode.SHIFT)){
            player.setRun(!player.getRun());
            setKeyIsPressed(KeyCode.SHIFT);
        }
        if (isReleased(KeyCode.I)){
            boolean isVisibleInventory = player.inventoryPanel.isVisible();
            player.inventoryPanel.setVisible(!isVisibleInventory);
            player.inventoryPanel.toFront();
            setKeyIsPressed(KeyCode.I);
        }
        if (isReleased(KeyCode.C)){
            player.saveState();
            setKeyIsPressed(KeyCode.C);
        }
        if (isReleased(KeyCode.V)){
            player.loadState();
            setKeyIsPressed(KeyCode.V);
        }

        if (isReleased(KeyCode.F2)){
            Flonarnia.player.toVillage(portal);
            setKeyIsPressed(KeyCode.F2);
        }
        if (isReleased(KeyCode.F3)){
            Flonarnia.player.usePoison("health", Color.RED);
            setKeyIsPressed(KeyCode.F3);
        }
        if (isReleased(KeyCode.F4)){
            Flonarnia.player.usePoison("endurance", Color.GREEN);
            setKeyIsPressed(KeyCode.F4);
        }
        if (isReleased(KeyCode.F5)){
            Flonarnia.player.usePoison("mana", Color.BLUE);
            setKeyIsPressed(KeyCode.F5);
        }
        if (isReleased(KeyCode.F6)){
            Flonarnia.player.spend();
            setKeyIsPressed(KeyCode.F6);
        }
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    private boolean isReleased(KeyCode key) {
        return !keys.getOrDefault(key, true);
    }
    private void setKeyIsPressed(KeyCode key){
        keys.put(key, true);
    }

}
