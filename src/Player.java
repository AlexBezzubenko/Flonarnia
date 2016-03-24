import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by Alexander on 22.03.2016.
 */

public class Player  extends Pane {
    private final int WIDTH = 32;
    private final int HEIGHT = 60;

    int columns = 6;
    int offsetX = 0;
    int offsetY = 0;
    int width = 32;
    int height = 60;

    private boolean right = true;
    private boolean top = false;
    private boolean left = false;
    private boolean bottom = false;

    boolean isRunning = false;

    Image image = ImageManager.getInstance().getImage("PLAYER");
    ImageView imageView = new ImageView(image);

    private SpriteAnimation animation;
    public Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;

    public Player(){
        this.setTranslateX(150);
        this.setTranslateY(150);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(this.imageView, Duration.millis(1000),columns,offsetX,offsetY,width,height);
        animation.setCycleCount(1);
        getChildren().addAll(this.imageView);
    }

    public void moveX(int value, boolean run){
        animation.setWidthHeight(width,height);
        this.setTranslateX(this.getTranslateX() + value);
        int x = 0;
        int c = 6;
        if (run){
            x = 212;
        }
        if (isRunning){
            x += width * 2 ;
            c -= 2;
        }

        if (value > 0) {
            animation.setOffsetY(192);
        } else {
            animation.setOffsetY(128);
        }

        animation.setOffsetX(x);
        animation.setColumns(c);
        animation.play();
        animation.onFinishedProperty().set(actionEvent -> isRunning = true);
    }
    public void moveY(int value, boolean run){
        animation.setWidthHeight(width,height);
        this.setTranslateY(this.getTranslateY() + value);
        int x = 0;
        int c = 6;
        if (run){
            x = 212;
        }
        if (isRunning){
            x += width * 2 ;
            c -= 2;
        }
        if (value > 0) {
            animation.setOffsetY(0);
        } else {
            animation.setOffsetY(63);
        }

        animation.setOffsetX(x);
        animation.setColumns(c);
        animation.play();
        animation.onFinishedProperty().set(actionEvent -> isRunning = true);
    }
    public void moveCircle(int velocity){
        if(getTranslateX() > 300 - width) {
                    right = false;
                    bottom = true;
                }
                if(getTranslateX() < 0) {
                    left = false;
                    top = true;
                }
                if(getTranslateY() > 300 - height) {
                    bottom = false;
                    left = true;
                }
                if(getTranslateY() < 0) {
                    top = false;
                    right = true;
                }

                if (right){
                    moveX(velocity, false);

                }
                if(left){
                     moveX(-velocity, false);

                }
                 if (top){
                    moveY(-velocity, false);

                 }
                if(bottom){
                    moveY(velocity, false);
                }
                animation.play();
    }
    public void stop(){
        if (isRunning) {
            isRunning = false;
            animation.setColumns(6);
            animation.setOffsetX(212);
        }
        animation.interpolate(0);
        animation.stop();
    }
    public void attack(){
        animation.setColumns(5);
        animation.setOffsetX(2);
        animation.setOffsetY(513);
        animation.setWidthHeight(60,70);
        animation.play();
        animation.setOnFinished(event -> {
            animation.interpolate(0);
        });
    }
    /*public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-28);
            canJump = false;
        }
    }*/
}
