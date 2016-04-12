import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by Alexander on 22.03.2016.
 */

public class Player  extends Flobject {
    private final double WIDTH = 32;
    private final double HEIGHT = 60;

    int columns = 6;
    int offsetX = 0;
    double offsetY = 0;
    double width = 32;
    double height = 60;

    private boolean right = true;
    private boolean top = false;
    private boolean left = false;
    private boolean bottom = false;

    boolean isRunning = false;

    private SpriteAnimation animation;

    public Point2D playerVelocity = new Point2D(0,0);

    public Player(double translateX, double translateY){
        super(translateX, translateY, 32, 60);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(this.imageView, Duration.millis(1000),columns,offsetX,offsetY,width,height);
        animation.setCycleCount(1);
        rect.setWidth(WIDTH / 2);
        rect.setHeight(HEIGHT / 2);
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX + WIDTH / 4);
        //getChildren().addAll(this.imageView);
    }

    public void moveX(int value, boolean run){
        changeSize(WIDTH, HEIGHT);
        this.setTranslateX(this.getTranslateX() + value);
        rect.setTranslateX(this.getTranslateX() + WIDTH / 4);

        for (Tree tree:Flonarnia.trees){
            if(tree.rect.getBoundsInParent().intersects(this.rect.getBoundsInParent())){
                this.setTranslateX(this.getTranslateX() - value);
                rect.setTranslateX(this.getTranslateX() + WIDTH / 4);
                return;
            }
        }

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
        changeSize(WIDTH, HEIGHT);
        animation.setWidthHeight(width,height);
        this.setTranslateY(this.getTranslateY() + value);
        rect.setTranslateY(this.getTranslateY() + HEIGHT / 2);

        for (Tree tree:Flonarnia.trees){
            if(tree.rect.getBoundsInParent().intersects(this.rect.getBoundsInParent())){
                this.setTranslateY(this.getTranslateY() - value);
                rect.setTranslateY(this.getTranslateY() + HEIGHT / 2);
                return;
            }
        }

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
        changeSize(60, 70);
        animation.setColumns(5);
        animation.setOffsetX(2);
        animation.setOffsetY(513);

        animation.play();
        animation.setOnFinished(event -> {
            changeSize(WIDTH, HEIGHT);
            animation.interpolate(0);
        });
    }
    private void changeSize(double width, double height){
        this.width = width;
        this.height = height;
        this.setPrefSize(width,height);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        animation.setWidthHeight(width,height);
    }
}
