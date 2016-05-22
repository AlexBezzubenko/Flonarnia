package Flonarnia.Flobjects;

import Flonarnia.tools.SpriteAnimation;
import javafx.animation.Animation;
import javafx.util.Duration;

/**
 * Created by Alexander on 16.05.2016.
 */
public class Fire extends Flobject {
    protected SpriteAnimation animation;

    public Fire(double translateX, double translateY){
        super(translateX, translateY, "Flobjects", "fire");

        animation = new SpriteAnimation(this.imageView, Duration.millis(500),columns,offsetX,offsetY,WIDTH,HEIGHT);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        bounds.setWidth(WIDTH / 2);
        bounds.setHeight(HEIGHT / 3);
        bounds.setTranslateY(translateY + 2 * HEIGHT / 3);
        bounds.setTranslateX(translateX);

        this.getChildren().removeAll(visual);
    }
}
