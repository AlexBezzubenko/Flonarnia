package Flonarnia.Scenes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.Portal;
import Flonarnia.Heroes.MovableFlobject;
import Flonarnia.Heroes.Player;
import Flonarnia.Panels.*;
import Flonarnia.Scenes.Location.*;
import Flonarnia.tools.Sort;
import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
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
    public static ToVillagePanel toVillagePanel;
    public static ShamanPanel shamanPanel;

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
    public static String login;
    private String parentPath;

    public Flonarnia(Stage primaryStage, String login){
        this.primaryStage = primaryStage;
        this.login = login;

        String pattern= "^[a-zA-Z0-9]+$";
        if(!login.matches(pattern)){
            login = "hero";
        }

        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        String jarPath = null;
        try {
            jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        parentPath = new File(jarPath).getParentFile().getPath();
        parentPath = parentPath + File.separator;

        APP_W = primaryStage.getWidth();
        APP_H = primaryStage.getHeight();
        double x = 800;
        double y = 600;
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
            frames.setTranslateX(APP_W - frames.getWidth() - 40);
        });
        appRoot.heightProperty().addListener((obs,old,newValue)->{
            APP_H = newValue.doubleValue();
            double offset = player.getTranslateY();
            gameRoot.setLayoutY( -(offset - APP_H / 2));
        });

        locations.add(new Dion());
        locations.get(0).setCreated(true);
        locations.add(new BeastFarm());
        locations.add(new DevilPass());
        locations.add(new DragonValley());
        locations.add(new ForbiddenPass());
        locations.add(new KetraOrcOutpost());
        locations.add(new ValleyOfSilence());
    }

    public void run(){
        createContent();

        player.loadState(login, parentPath);
        logPanel.addLine("Welcome to Flonarnia!", Color.GREEN);
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
                if (now % 30 == 0){
                    float fps = tracker.getAverageFPS();
                    tracker.resetAverageFPS();
                    frames.setText(String.valueOf((int)fps));
                    player.regenerate();
                }
            }
        };
        timer.start();
    }

    private void createContent(){
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
        toVillagePanel = new ToVillagePanel(appRoot, locations);
        targetPanel = new TargetPanel(APP_W / 2, 0, appRoot);
        tradePanel = new TradePanel(APP_W / 3, APP_H / 4, appRoot);
        teleportPanel = new TeleportPanel(appRoot);
        teleportPanel.setLinks(locations, foregroundRoot);
        shamanPanel = new ShamanPanel(appRoot);

        mainScene = new Scene(appRoot, APP_W, APP_W);
        primaryStage.setScene(mainScene);
        mainScene.setOnKeyPressed(event->keys.put(event.getCode(),true));
        mainScene.setOnKeyReleased(event->keys.put(event.getCode(), false));

        portal = currentLocation.getPortal();
        foregroundRoot.getChildren().add(portal);
        skillPanel = new SkillPanel(appRoot, player, locations);
        player.Bind(skillPanel);

        primaryStage.setOnCloseRequest(e->{
            player.saveState(login, parentPath);
        });
        primaryStage.setFullScreen(true);
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
        else if (isPressed(KeyCode.M) && isPressed(KeyCode.O) && isPressed(KeyCode.N) && isPressed(KeyCode.E)
                 && isPressed(KeyCode.Y)){
            player.shekelAmount.set(player.shekelAmount.getValue() + 2000);
        }
        else {
            player.stop();
        }

        if (isReleased(KeyCode.ALT) && isReleased(KeyCode.ENTER)){
            primaryStage.setFullScreen(!primaryStage.fullScreenProperty().get());
            setKeyIsPressed(KeyCode.ALT);
            setKeyIsPressed(KeyCode.ENTER);
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

        if (isReleased(KeyCode.F2)){
            Flonarnia.player.toVillage(locations, true);
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
