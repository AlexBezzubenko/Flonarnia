package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Heroes.Strategy.Strategy;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.tools.Collision;
import Flonarnia.tools.SpriteAnimation;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

/**
 * Created by Alexander on 21.04.2016.
 */

//        name = "buffalo";
//        name = "dark_soul";
//        name = "orge";
//        name = "undead";
//        name = "dragon";

public class Enemy extends MovableFlobject {
    public SimpleDoubleProperty health = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty maxHealth = new SimpleDoubleProperty(1000);

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

        //this.getChildren().removeAll(this.visual);
    }

    public void moveX(double value){
        this.setTranslateX(this.getTranslateX() + value);
        //rect.setTranslateX(this.getTranslateX() + WIDTH / 4);

        Flobject collisionFlobject = Collision.checkTranslateX(this, Flonarnia.flobjects, value);
        if (collisionFlobject != null){
            if (collisionFlobject.getClass() == Player.class){
                ((Player)collisionFlobject).attacked(value / Math.abs(value) * 200, true);
                //this.setTranslateX(this.getTranslateX() - 5);
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
        //rect.setTranslateY(this.getTranslateY() + HEIGHT / 2);
        Flobject collisionFlobject = Collision.checkTranslateY(this, Flonarnia.flobjects, value);
        if (collisionFlobject != null){
            if (collisionFlobject.getClass() == Player.class){
                ((Player)collisionFlobject).attacked(value / Math.abs(value) * 200, false);
                //this.setTranslateX(this.getTranslateX() - 5);
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
    public void moveCircle(int velocity){
        if (moveCircleRadius >= 0 && moveCircleRadius < 800){
            moveY(velocity);
            animation.play();
        }
        else if (moveCircleRadius < 0 && moveCircleRadius > -800){
            moveY(-velocity);
            animation.play();
        }
        moveCircleRadius -= velocity;

        if (moveCircleRadius < -800){
            moveCircleRadius = 800;
            animation.stop();
        }
    }

    public void stop(){
        if (isRunning) {
            isRunning = false;
            animation.setColumns(6);
            animation.setOffsetX(offsetX); // +212 for player not influences now
        }
        animation.interpolate(0);
        animation.stop();
    }
}
