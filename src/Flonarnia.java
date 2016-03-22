import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Alexander on 14.03.2016.
 */
public class Flonarnia extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private Player player = new Player();


    int a = 2;

    @Override
    public void start(Stage primaryStage) {


        String musicFile = "23.mp3";     // For example
        Thread t = new Thread(() -> {
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            while(true)
            mediaPlayer.play();
        });
       // t.start();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //player.moveCircle(1);
                update();
            }
        };
        timer.start();

        Scene s = new Scene(new Group(player),300,300,Color.RED);
        s.setOnKeyPressed(event->keys.put(event.getCode(),true));
        s.setOnKeyReleased(event-> {
            keys.put(event.getCode(), false);
        });
        primaryStage.setScene(s);
        primaryStage.show();
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
    public static void main(String[] args) {
        launch(args);
    }


    /*static long startNanoTime;
    @Override
    public void init() throws Exception {
        //init resources;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Flonarnia");
        Pane root = new Pane();
        Canvas canvas = new Canvas(512,512);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image image = new Image("sun.png");
        ProgressBar progressBar = new ProgressBar(0);

        ProgressIndicator progressIndicator = new ProgressIndicator(0);

        progressIndicator.setTranslateX(200);

        root.getChildren().addAll(progressBar, progressIndicator);

        startNanoTime  = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gc.clearRect(0,0,512,512);
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x =  250 + 100 * Math.cos(t-3.14/2);
                double y =  250 + 100 * Math.sin(t-3.14/2);

                    progressIndicator.setProgress(t / 6.28);
                    progressBar.setProgress(t / 6.28);
                if (x < 250 && y < 151){
                    //progressIndicator.setProgress(0);
                    //progressBar.setProgress(0);
                    startNanoTime = currentNanoTime;
                }
                gc.drawImage( image, x, y );

                //user input;
                //update;
                //render;
                //sync
            }
        }.start();
        Scene scene = new Scene(root,512,512, Color.ANTIQUEWHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        //release resources;
    }*/
}
