package Flonarnia.Scenes;

import Flonarnia.Flobjects.*;
import Flonarnia.Heroes.*;
import Flonarnia.Panels.*;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexander on 24.03.2016.
 */
public class Flonarnia {
    private final double APP_W;
    private final double APP_H;
    private Stage primaryStage;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    public static Player player;

    private int velocity = 5;
    private boolean shift = false;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    public static Pane backgroundRoot = new Pane();
    public static Pane foregroundRoot = new Pane();
    public static TargetPanel targetPanel;
    public static SkillPanel skillPanel;

    private NPC guider = new NPC(2000, 510, "guider");
    private NPC warrior = new NPC(2200, 510, "warrior");
    private NPC shaman = new NPC(2400, 510, "shaman");
    private NPC trader = new NPC(2600, 510, "trader");
    private NPC gatekeeper = new NPC(1100, 900, "gatekeeper");
    private NPC blacksmith = new NPC(1100, 1000, "blacksmith");
    private Enemy buffalo = new Enemy(1700, 700, "buffalo");
    private Enemy dark_soul = new Enemy(1100, 700, "dark_soul2");
    private Enemy dragon = new Enemy(1200, 700, "dragon");
    private Enemy ogre = new Enemy(1400, 700, "ogre");
    private Enemy undead = new Enemy(1300, 700, "undead");

    private Flobject[] houses = new Flobject[]{
            new TypedHouse(1900, 200, "house_type_1"),
            new TypedHouse(2100, 200, "house_type_2"),
            new TypedHouse(2300, 200, "house_type_3"),
            new TypedHouse(2500, 200, "house_type_4")
    };


    public static ArrayList<Flobject> flobjects = new ArrayList<>();


    private ArrayList<ImageView> gv = new ArrayList<>();

    public Flonarnia(Stage primaryStage){
        this.primaryStage = primaryStage;
        APP_W = primaryStage.getWidth();
        APP_H = primaryStage.getHeight();
        double x = APP_W / 4 + 600 + 1000;
        double y = APP_H / 4 + 600;
        player = new Player(x, y);
        gameRoot.setLayoutX( -(x - APP_W / 2));
        gameRoot.setLayoutY( -(y - APP_H / 2));
    }

    public void run(){
        String musicFile = "23.mp3";
        Thread t = new Thread(() -> {
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        });
       // t.start();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //player.moveCircle(1);
                for (Flobject f:flobjects){
                    if (f.getClass() == Enemy.class){
                        ((Enemy)f).moveCircle(1);
                    }
                    if (f.getClass() == NPC.class){
                        try {
                            ((NPC) f).moveCircle(1);
                        } catch (InterruptedException e){

                        }
                    }
                }
                update();
                if (now % 100 == 0)
                    System.out.println(keys);

            }
        };
        timer.start();

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

        player.translateXProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset > APP_W / 2 || offset <= APP_W / 2 /*&& offset < APP_W * 5 - 640*/){
                gameRoot.setLayoutX( -(offset - APP_W / 2));
            }
        });
        player.translateYProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset > APP_H / 2 || offset <= APP_H / 2 /*&& offset < APP_W * 5 - 640*/){
                gameRoot.setLayoutY( -(offset - APP_H / 2));
                //grassView.setLayoutY( -(offset - APP_H /2 ));
            }
        });
        for (Flobject f: houses){
            flobjects.add(f);
        }
        flobjects.add(player);
        flobjects.add(new Tree(500, 400));
        flobjects.add(new Tree(500, 600));
        flobjects.add(new Tree(800, 400));
        flobjects.add(new Tree(1000, 500));
        flobjects.add(new House(-100, -100));
        flobjects.add(trader);
        flobjects.add(guider);
        flobjects.add(warrior);
        flobjects.add(shaman);
        flobjects.add(gatekeeper);
        flobjects.add(blacksmith);
        flobjects.add(dragon);
        flobjects.add(dark_soul);
        flobjects.add(buffalo);
        flobjects.add(ogre);
        flobjects.add(undead);


        gameRoot.getChildren().addAll(gv);

        //gameRoot.getChildren().addAll( player);
        backgroundRoot.getChildren().addAll(gv);//reislor
        foregroundRoot.getChildren().addAll(flobjects);

        //inventoryPanel = new InventoryPanel(APP_W / 3, APP_H / 4);
        player.inventoryPanel.setTranslateX(APP_W / 3);
        player.inventoryPanel.setTranslateY(APP_H / 4);

        gameRoot.getChildren().addAll(backgroundRoot,foregroundRoot);
        appRoot.getChildren().addAll(gameRoot);

        targetPanel = new TargetPanel(APP_W / 2, 0, appRoot);
        skillPanel = new SkillPanel(APP_W / 3, APP_H - APP_H / 6, appRoot);

        Scene mainScene = new Scene(appRoot, APP_W, APP_W);
        primaryStage.setScene(mainScene);
        mainScene.setOnKeyPressed(event->keys.put(event.getCode(),true));

        mainScene.setOnKeyReleased(event->keys.put(event.getCode(), false));
        for (Node n: appRoot.getChildren())
            System.out.println(n.getClass().getSimpleName());
    }

    public void update() {
        if (isPressed(KeyCode.UP) || isPressed(KeyCode.W)) {
            player.moveY(-velocity, shift);
            //enemy.moveY(-a, shift);
            if (player.getTranslateY() < gv.get(4).getTranslateY()){
                for (ImageView imageView: gv){
                    imageView.setTranslateY(imageView.getTranslateY() - APP_H);
                }
            }
        } else if (isPressed(KeyCode.DOWN) || isPressed(KeyCode.S)) {
            player.moveY(velocity, shift);
            //enemy.moveY(a, shift);
            if (player.getTranslateY() > gv.get(4).getTranslateY() + APP_H){
                for (ImageView imageView: gv){
                    imageView.setTranslateY(imageView.getTranslateY() + APP_H);
                }
            }
        } else if (isPressed(KeyCode.RIGHT) || isPressed(KeyCode.D)) {
            player.moveX(velocity, shift);
            //enemy.moveX(a, shift);

            if (player.getTranslateX() > gv.get(4).getTranslateX() + APP_W){
                for (ImageView imageView: gv){
                    imageView.setTranslateX(imageView.getTranslateX() + APP_W);
                }
            }
        } else if (isPressed(KeyCode.LEFT) || isPressed(KeyCode.A)) {
            player.moveX(-velocity, shift);
            //enemy.moveX(-a, shift);
            if (player.getTranslateX() < gv.get(4).getTranslateX()){
                for (ImageView imageView: gv){
                    imageView.setTranslateX(imageView.getTranslateX() - APP_W);
                }
            }
        }
        else if (isPressed(KeyCode.F)){
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
            player.shekelAmount.set(player.shekelAmount.getValue() + 1);
        }
        else {
            player.stop();
        }

        if (isReleased(KeyCode.SHIFT)){
            velocity = shift? velocity / 2 : velocity * 2;
            shift = !shift;
            setKeyIsPressed(KeyCode.SHIFT);
        }
        if (isReleased(KeyCode.I)){
            boolean isVisibleInventory = player.inventoryPanel.isVisible();
            player.inventoryPanel.setVisible(!isVisibleInventory);
            player.inventoryPanel.toFront();
            setKeyIsPressed(KeyCode.I);
        }


    }
    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    public boolean isReleased(KeyCode key) {
        return !keys.getOrDefault(key, true);
    }
    public void setKeyIsPressed(KeyCode key){
        keys.put(key, true);
    }

}
