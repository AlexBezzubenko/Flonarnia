package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.tools.Collision;
import Flonarnia.tools.SpriteAnimation;
import javafx.util.Duration;

/**
 * Created by Alexander on 21.04.2016.
 */

//        name = "buffalo";
//        name = "dark_soul";
//        name = "orge";
//        name = "undead";
//        name = "dragon";

public class Enemy extends Flobject {
    protected double width;
    protected double height;

    protected boolean right = true;
    protected boolean top = false;
    protected boolean left = false;
    protected boolean bottom = false;
    protected boolean isRunning = false;
    private static int moveCircleRadius = 100;

    protected SpriteAnimation animation;

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

    protected void moveX(int value, boolean run){
        this.setTranslateX(this.getTranslateX() + value);
        //rect.setTranslateX(this.getTranslateX() + WIDTH / 4);

        Flobject collisionFlobject = Collision.checkTranslteX(this, Flonarnia.flobjects, value);
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

    protected void moveY(int value, boolean run) {
        animation.setWidthHeight(width,height);
        this.setTranslateY(this.getTranslateY() + value);
        //rect.setTranslateY(this.getTranslateY() + HEIGHT / 2);
        Flobject collisionFlobject = Collision.checkTranslteY(this, Flonarnia.flobjects, value);
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
        /*if(getTranslateX() > 300 - width) {
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
        */
        if (moveCircleRadius >= 0 && moveCircleRadius < 800){
            moveY(velocity, false);
            animation.play();
        }
        else if (moveCircleRadius < 0 && moveCircleRadius > -800){
            moveY(-velocity, false);
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
