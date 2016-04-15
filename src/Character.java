import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

/**
 * Created by Alexander on 15.04.2016.
 */
public class Character extends Flobject {
    protected final double WIDTH;
    protected final double HEIGHT;
    protected final double offsetX;
    protected final double offsetY;

    protected int columns = 6;
    protected double width = 32;
    protected double height = 60;

    protected boolean right = true;
    protected boolean top = false;
    protected boolean left = false;
    protected boolean bottom = false;
    protected boolean isRunning = false;
    private static int moveCircleRadius = 100;

    protected SpriteAnimation animation;

    public Character(double translateX, double translateY, String name) {
        super(translateX, translateY, 32, 60);
        double[] params = ImageManager.getInstance().getNPCImageParams(name);
        offsetX = params[0];
        offsetY = params[1];
        WIDTH = params[2];
        HEIGHT = params[3];

        imageView.setViewport(new Rectangle2D(offsetX,offsetY,WIDTH,HEIGHT));
        animation = new SpriteAnimation(this.imageView, Duration.millis(1000),columns,offsetX,offsetY,WIDTH,HEIGHT);
        animation.setCycleCount(1);
        rect.setWidth(WIDTH / 2);
        rect.setHeight(HEIGHT / 2);
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX + WIDTH /4);
        this.getChildren().removeAll(this.visual);
    }
    protected void moveX(int value, boolean run){
        this.setTranslateX(this.getTranslateX() + value);
        rect.setTranslateX(this.getTranslateX() + WIDTH / 4);

        if (Collision.checkTranslteX(this, Flonarnia.flobjects, value)){
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

    protected void moveY(int value, boolean run) {
        animation.setWidthHeight(width,height);
        this.setTranslateY(this.getTranslateY() + value);
        rect.setTranslateY(this.getTranslateY() + HEIGHT / 2);
        if (Collision.checkTranslteY(this, Flonarnia.flobjects, value)){
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
            moveX(velocity, false);
            animation.play();
        }
        else if (moveCircleRadius < 0 && moveCircleRadius > -800){
            moveX(-velocity, false);
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
