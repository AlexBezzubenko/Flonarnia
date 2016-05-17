package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Heroes.Strategy.Strategy;
import Flonarnia.tools.SpriteAnimation;
import javafx.util.Duration;

/**
 * Created by Alexander on 14.05.2016.
 */
public abstract class MovableFlobject extends Flobject {
    protected double width;
    protected double height;

    protected boolean isRunning = false;
    protected int moveCircleRadius = 100;
    protected double startX;
    protected double startY;

    protected SpriteAnimation animation;

    protected Strategy context;
    public MovableFlobject(double translateX, double translateY, String group, String species){
        super(translateX,translateY, group, species);
        width = WIDTH;
        height = HEIGHT;
        startX = translateX;
        startY = translateY;

        animation = new SpriteAnimation(this.imageView, Duration.millis(1000),columns,offsetX,offsetY,WIDTH,HEIGHT);
        animation.setCycleCount(1);
        bounds.setWidth(WIDTH / 2);
        bounds.setHeight(HEIGHT / 2);
        bounds.setTranslateY(translateY + HEIGHT / 2);
        bounds.setTranslateX(translateX + WIDTH /4);
    }

    public void moveX(double value){}
    public void moveY(double value){}

    public double getStartTranslteX(){
        return startX;
    }
    public double getStartTranslteY(){
        return startY;
    }

    public void setContext(Strategy context){
        this.context = context;
    }
    public Strategy getContext(){
        return this.context;
    }
}
