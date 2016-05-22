package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.tools.Collision;
import Flonarnia.tools.SpriteAnimation;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

/**
 * Created by Alexander on 21.04.2016.
 */

//        name = "buffalo";
//        name = "dark_soul";
//        name = "ogre";
//        name = "undead";
//        name = "dragon";

public class Enemy extends MovableFlobject {
    public SimpleDoubleProperty health = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty maxHealth = new SimpleDoubleProperty(1000);
    public SimpleIntegerProperty power = new SimpleIntegerProperty(50);
    public SimpleIntegerProperty cost = new SimpleIntegerProperty(1);

    public Enemy(double translateX, double translateY, String species) {
        super(translateX, translateY, "Enemy", species);
        width = WIDTH;
        height = HEIGHT;

        animation = new SpriteAnimation(this.imageView, Duration.millis(1000),columns,offsetX,offsetY,WIDTH,HEIGHT);
        animation.setCycleCount(1);
        bounds.setWidth(WIDTH / 2);
        bounds.setHeight(HEIGHT / 2);
        bounds.setTranslateY(translateY + HEIGHT / 2);
        bounds.setTranslateX(translateX + WIDTH /4);
    }
    public Enemy(double translateX, double translateY, String species, int level) {
        this(translateX, translateY, species);
        health.set(level * 100);
        maxHealth.set(level * 100);
        power.set(level * 15);
        cost.set(level * 100);
    }

    public void moveX(double value){
        this.setTranslateX(this.getTranslateX() + value);

        Flobject collisionFlobject = Collision.checkTranslateX(this, Flonarnia.flobjects, value);
        if (collisionFlobject != null){
            if (collisionFlobject.getClass() == Player.class){
                ((Player)collisionFlobject).attacked(value / Math.abs(value) * 150, power.get(), true);
            }
            return;
        }

        if (value > 0) {
            animation.setOffsetY(offsetY + HEIGHT * 2);
        } else {
            animation.setOffsetY(offsetY + HEIGHT);
        }

        animation.play();
    }

    public void moveY(double value) {
        animation.setWidthHeight(width,height);
        this.setTranslateY(this.getTranslateY() + value);
        Flobject collisionFlobject = Collision.checkTranslateY(this, Flonarnia.flobjects, value);
        if (collisionFlobject != null){
            if (collisionFlobject.getClass() == Player.class){
                ((Player)collisionFlobject).attacked(value / Math.abs(value) * 150, power.get(), false);
            }
            return;
        }


        if (value > 0) {
            animation.setOffsetY(offsetY);
        } else {
            animation.setOffsetY(offsetY + HEIGHT * 3);
        }

        animation.play();
    }
}
