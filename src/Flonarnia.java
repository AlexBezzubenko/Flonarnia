import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Alexander on 24.03.2016.
 */
public class Flonarnia {
    private Stage primaryStage;
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private Player player = new Player();
    int a = 2;

    public Flonarnia(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void run(){
        String musicFile = "23.mp3";
        Thread t = new Thread(() -> {
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        });
        //t.start();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //player.moveCircle(1);
                update();
            }
        };
        timer.start();


        Pane root = new Pane();
        Scene mainScene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(mainScene);
        root.getChildren().add(player);
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
