package Flonarnia.tools;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by Alexander on 21.03.2016.
 */
public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private int columns;
    private double offsetX;
    private double offsetY;
    private double width;
    private double height;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int columns,
            double offsetX, double offsetY,
            double width, double height
    ){
        this.imageView = imageView;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }
    public void setOffsetX(double x){
        this.offsetX = x;
    }
    public void setOffsetY(double y){
        this.offsetY = y;
    }
    public void setColumns(int columns){
        this.columns = columns;
    }
    public void setWidthHeight(double width, double height){
        this.width = width;
        this.height = height;
    }

    public void interpolate(double frac) { // frac = [0..1]
        final int index = Math.min((int) Math.floor(columns * frac), columns - 1);
        final double x = (index % columns) * width + offsetX;
        final double y = (index / columns) * height + offsetY;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}
