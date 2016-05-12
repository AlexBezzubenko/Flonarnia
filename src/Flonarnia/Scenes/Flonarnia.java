package Flonarnia.Scenes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.House;
import Flonarnia.Flobjects.Tree;
import Flonarnia.Flobjects.TypedHouse;
import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.NPC;
import Flonarnia.Heroes.Player;
import Flonarnia.Heroes.Strategy.StrategyAttack;
import Flonarnia.Panels.HeroPanel;
import Flonarnia.Panels.SkillPanel;
import Flonarnia.Panels.TargetPanel;
import Flonarnia.Panels.TradePanel;
import Flonarnia.tools.Sort;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
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
import java.util.TreeSet;

/**
 * Created by Alexander on 24.03.2016.
 */
public class Flonarnia {
    private final double APP_W;
    private final double APP_H;
    private Stage primaryStage;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    public static Player player;

    private int velocity = 4;
    private boolean shift = false;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    public static Pane backgroundRoot = new Pane();
    public static Pane foregroundRoot = new Pane();
    public static TargetPanel targetPanel;
    public static TradePanel tradePanel;
    public static SkillPanel skillPanel;

    public static boolean bool = false;

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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                enemyActivate();
                update();

                flobjects.sort(new Sort());

                for (Flobject f: flobjects){
                    f.toFront();
            }
                if (now % 100 == 0 && !bool){
                    /*for (Flobject tf: tflobjects){
                        System.out.println(tf.getClass().getSimpleName());
                    }*/

                    //    System.out.println(keys);
                }

            }
        };
        timer.start();
    }

    private void createContext(){
        NPC guider = new NPC(2000, 514, "Guider");
        NPC warrior = new NPC(2200, 513, "Warrior");
        NPC shaman = new NPC(2400, 510, "Shaman");
        NPC trader = new NPC(2600, 511, "Trader");
        NPC gatekeeper = new NPC(1100, 902, "GateKeeper");
        NPC blacksmith = new NPC(1100, 1001, "Blacksmith");
        Enemy buffalo = new Enemy(1700, 705, "Buffalo");
        Enemy dark_soul = new Enemy(1100, 704, "Dark Soul");
        dark_soul.setContext(new StrategyAttack(dark_soul));
        Enemy dragon = new Enemy(1200, 703, "Dragon");
        dragon.setContext(new StrategyAttack(dragon));
        Enemy ogre = new Enemy(1400, 702, "Ogre");
        ogre.setContext(new StrategyAttack(ogre));
        Enemy undead = new Enemy(1300, 701, "Undead");
        undead.setContext(new StrategyAttack(undead));

        Flobject[] houses = new Flobject[]{
                new TypedHouse(1900, 201, "house_type_1"),
                new TypedHouse(2100, 202, "house_type_2"),
                new TypedHouse(2300, 203, "house_type_3"),
                new TypedHouse(2500, 204, "house_type_4")
        };

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

        for (Flobject f: houses){
            flobjects.add(f);
        }
        flobjects.add(player);
        flobjects.add(new Tree(500, 401));
        flobjects.add(new Tree(500, 600));
        flobjects.add(new Tree(800, 402));
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

        for (int i = 0; i < 5; i++){
            Enemy buffalo2 = new Enemy(2000 + i * 300, 600 + i, "Buffalo");
            if (i > 2) {
                buffalo2.setContext(new StrategyAttack(buffalo2));
            }
            flobjects.add(buffalo2);
        }

        //gameRoot.getChildren().addAll(gv);

        backgroundRoot.getChildren().addAll(gv);
        foregroundRoot.getChildren().addAll(flobjects);

        player.inventoryPanel.setTranslateX(APP_W / 3);
        player.inventoryPanel.setTranslateY(APP_H / 4);

        gameRoot.getChildren().addAll(backgroundRoot,foregroundRoot);
        appRoot.getChildren().addAll(gameRoot);


        HeroPanel heroPanel = new HeroPanel(10, 10, appRoot);
        targetPanel = new TargetPanel(APP_W / 2, 0, appRoot);
        tradePanel = new TradePanel(APP_W / 3, APP_H / 4, appRoot);
        skillPanel = new SkillPanel(APP_W / 3, APP_H - APP_H / 6, appRoot);

        Scene mainScene = new Scene(appRoot, APP_W, APP_W);
        primaryStage.setScene(mainScene);
        mainScene.setOnKeyPressed(event->keys.put(event.getCode(),true));
        mainScene.setOnKeyReleased(event->keys.put(event.getCode(), false));

        player.Bind();
    }
    private void enemyActivate(){
        for (Flobject f:flobjects){
            if (f.getClass() == Enemy.class){
                int activateRadius = 300;
                Enemy enemy = (Enemy)f;
                            /*if (Math.abs(player.getTranslateX() - enemy.getTranslateX()) <
                                    activateRadius && Math.abs(player.getTranslateY() - enemy.getTranslateY())
                                    < activateRadius){
                                //if (i == 1)
                                //    enemy.setContext(new StrategyRunAway(enemy));
                                //enemy.setContext(new StrategyAttack(enemy));
                            }
                            else {
                                if (enemy.getContext() != null) {
                                    if (enemy.getContext().getClass() != StrategyChaotic.class)
                                        enemy.setContext(new StrategyChaotic(enemy));
                                }
                            }*/
                if (enemy.getContext() != null)
                    enemy.getContext().move();

                //((Enemy)f).moveCircle(1);
            }
            if (f.getClass() == NPC.class){
                try {
                    ((NPC) f).moveCircle(1);
                } catch (InterruptedException e){

                }
            }
        }
    }
    private void update() {
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
