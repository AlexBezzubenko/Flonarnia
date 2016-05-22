package Flonarnia.Flobjects;

import Flonarnia.Scenes.Flonarnia;
import Flonarnia.tools.SpriteAnimation;
import javafx.animation.Animation;
import javafx.util.Duration;

/**
 * Created by Alexander on 16.05.2016.
 */
public class Portal extends Flobject {
    protected SpriteAnimation animation;

    public Portal(double translateX, double translateY){
        super(translateX, translateY, "Flobjects", "portal");
        animation = new SpriteAnimation(this.imageView, Duration.millis(500),columns,offsetX,offsetY,WIDTH,HEIGHT);
        bounds.setWidth(WIDTH);
        bounds.setHeight(HEIGHT);

        this.setOnMouseClicked(e->Flonarnia.toVillagePanel.setVisible(true));
    }

    public void closePortal(){
        animation.stop();
        animation.setColumns(columns);
        animation.setOffsetX(offsetX);
        animation.interpolate(0);
    }

    public void openPortal(){
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
        animation.setColumns(4);
        animation.setOffsetX(offsetX + WIDTH);
    }
}
