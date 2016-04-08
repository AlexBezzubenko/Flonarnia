import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private Player player = new Player();
    int a = 5;
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    public static Rectangle rect;

    public static ArrayList<Tree> trees = new ArrayList<>();

    public Flonarnia(Stage primaryStage){
        this.primaryStage = primaryStage;
        APP_W = primaryStage.getWidth();
        APP_H = primaryStage.getHeight();
    }

    public void run(){
        String musicFile = "23.mp3";
        Thread t = new Thread(() -> {
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        });
        t.start();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //player.moveCircle(1);
                update();
            }
        };
        timer.start();

        Image grassImage = new Image(getClass().getResourceAsStream("/res/map.png"));
        ImageView grassView = new ImageView(grassImage);
        grassView.setViewport(new Rectangle2D(0, 0, APP_W, APP_H));

        ArrayList<ImageView> gv = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            gv.add(new ImageView(grassImage));
        }
        gv.get(0).setTranslateX(-APP_W);
        gv.get(2).setTranslateX(APP_W);
        gv.get(3).setTranslateY(-APP_H);
        gv.get(4).setTranslateY(APP_H);

        player.setTranslateX(APP_W / 2);
        player.setTranslateY(APP_H / 2);
        player.translateXProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset > APP_W / 2 || offset <= APP_W / 2 /*&& offset < APP_W * 5 - 640*/){
                gameRoot.setLayoutX( -(offset - APP_W / 2));
                //grassView.setLayoutX( -(offset - APP_W /2 ));
            }
        });
        player.translateYProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset > APP_H / 2 || offset <= APP_H / 2 /*&& offset < APP_W * 5 - 640*/){
                gameRoot.setLayoutY( -(offset - APP_H / 2));
                //grassView.setLayoutY( -(offset - APP_H /2 ));
            }
        });

        rect = new Rectangle(300,300,100,100);
        rect.setFill(Color.BLUE);
        rect.setEffect(new Bloom(100));

        trees.add(new Tree(500, 400));
        trees.add(new Tree(500, 600));
        trees.add(new Tree(800, 400));
        trees.add(new Tree(1000, 500));

        gameRoot.getChildren().addAll(gv);
        gameRoot.getChildren().addAll( player, rect);
        gameRoot.getChildren().addAll(trees);
        appRoot.getChildren().addAll(gameRoot);

        Scene mainScene = new Scene(appRoot, APP_W, APP_W);
        primaryStage.setScene(mainScene);
        mainScene.setOnKeyPressed(event->keys.put(event.getCode(),true));

        mainScene.setOnKeyReleased(event-> {
            keys.put(event.getCode(), false);
            player.stop();
        });



    }


    private boolean shift = false;
    public void update() {
        if (isPressed(KeyCode.UP) || isPressed(KeyCode.W)) {
            player.moveY(-a, shift);
        } else if (isPressed(KeyCode.DOWN) || isPressed(KeyCode.S)) {
            player.moveY(a, shift);
        } else if (isPressed(KeyCode.RIGHT) || isPressed(KeyCode.D)) {
            player.moveX(a, shift);
        } else if (isPressed(KeyCode.LEFT) || isPressed(KeyCode.A)) {
            player.moveX(-a, shift);
        }
        else if (isPressed(KeyCode.SHIFT)){
            shift = !shift;
        }
        else if (isPressed(KeyCode.F)){
            player.attack();
        } else if (isPressed(KeyCode.Q)){
            player.stop();
        }
    }
    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

}
