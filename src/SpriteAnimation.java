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
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int columns,
            int offsetX, int offsetY,
            int width, int height
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
    public void setOffsetX(int x){
        this.offsetX = x;
    }
    public void setOffsetY(int y){
        this.offsetY = y;
    }
    public void setColumns(int columns){
        this.columns = columns;
    }
    public void setWidthHeight(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void interpolate(double frac) { // frac = [0..1]
        final int index = Math.min((int) Math.floor(columns * frac), columns - 1);
        final int x = (index % columns) * width + offsetX;
        final int y = (index / columns) * height + offsetY;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}
