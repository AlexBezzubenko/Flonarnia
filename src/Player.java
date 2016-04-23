import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by Alexander on 22.03.2016.
 */

public class Player  extends Character {
    public Player(double translateX, double translateY){
        super(translateX, translateY, "player");
    }

    @Override
    public void moveX(int value, boolean run){
        changeSize(WIDTH, HEIGHT);
        super.moveX(value, run);
    }
    @Override
    public void moveY(int value, boolean run){
        changeSize(WIDTH, HEIGHT);
        super.moveY(value, run);

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
    public void attacked(double value, boolean X){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), this);
        double sign = value / Math.abs(value);
        double throwLen = 20;
        if (X) {
            this.setTranslateX(this.getTranslateX() + sign * throwLen);
            translateTransition.setFromX(this.getTranslateX());
            translateTransition.setToX(this.getTranslateX() + value);
            translateTransition.setCycleCount(1);
            translateTransition.play();

            if (Collision.checkTranslteX(this, Flonarnia.flobjects, 0) != null) {
                translateTransition.stop();
                return;
            }
        }else{
            if (sign > 0){
                throwLen *= 3;
            }
            this.setTranslateY(this.getTranslateY() + sign * throwLen);
            translateTransition.setFromY(this.getTranslateY());
            translateTransition.setToY(this.getTranslateY() + value);
            translateTransition.setCycleCount(1);
            translateTransition.play();

            if (Collision.checkTranslteY(this, Flonarnia.flobjects, 0) != null) {
                translateTransition.stop();
                return;
            }
        }

        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);

        Blend blush;
        blush = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(0, 0, WIDTH, HEIGHT, Color.RED)
        );
        imageView.setEffect(blush);

        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
        translateTransition.setOnFinished(event -> {imageView.setEffect(null);});
    }
}
