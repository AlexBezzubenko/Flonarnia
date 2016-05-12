package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.tools.Collision;
import Flonarnia.tools.SpriteAnimation;
import javafx.util.Duration;

/**
 * Created by Alexander on 15.04.2016.
 */
public class Character extends Flobject {
    protected double width;
    protected double height;

    protected boolean right = true;
    protected boolean top = false;
    protected boolean left = false;
    protected boolean bottom = false;
    protected boolean isRunning = false;
    private static int moveCircleRadius = 100;

    protected SpriteAnimation animation;

    public Character(double translateX, double translateY, String name) {
        super(translateX, translateY, "NPC", name);
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

    protected void moveX(double value, boolean run){
        this.setTranslateX(this.getTranslateX() + value);
        //rect.setTranslateX(this.getTranslateX() + WIDTH / 4);

        if (Collision.checkTranslateX(this, Flonarnia.flobjects, value) != null){
            return;
        }

        double x = offsetX;
        int c = columns;
        if (run){
            x += 212;
        }
        if (isRunning){
            x += width * 2 ;
            c -= 2;
        }

        if (value > 0) {
            animation.setOffsetY(offsetY + 192);
        } else {
            animation.setOffsetY(offsetY + 128);
        }

        animation.setOffsetX(x);
        animation.setColumns(c);
        animation.play();
        animation.onFinishedProperty().set(actionEvent -> isRunning = true);
    }

    protected void moveY(double value, boolean run) {
        animation.setWidthHeight(width,height);
        this.setTranslateY(this.getTranslateY() + value);
        //rect.setTranslateY(this.getTranslateY() + HEIGHT / 2);
        if (Collision.checkTranslateY(this, Flonarnia.flobjects, value) != null){
            return;
        }

        double x = offsetX;
        int c = columns;
        if (run){
            x += 212;
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
    public void moveCircle(double velocity) throws InterruptedException {
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
            moveX(velocity, false);
            animation.play();
        }
        else if (moveCircleRadius < 0 && moveCircleRadius > -800){
            moveX(-velocity, false);
            animation.play();
        }
        else if (moveCircleRadius < -800 || moveCircleRadius > 800){
            animation.stop();
        }
        moveCircleRadius -= velocity;

        if (moveCircleRadius < -800){
            moveCircleRadius = 1200;
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