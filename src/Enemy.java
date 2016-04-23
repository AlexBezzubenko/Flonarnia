import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    protected final double WIDTH;
    protected final double HEIGHT;
    protected final double offsetX;
    protected final double offsetY;

    protected int columns = 3;
    protected double width = 32;
    protected double height = 60;

    protected boolean right = true;
    protected boolean top = false;
    protected boolean left = false;
    protected boolean bottom = false;
    protected boolean isRunning = false;
    private static int moveCircleRadius = 100;

    protected SpriteAnimation animation;

    public Enemy(double translateX, double translateY, String name) {
        image = ImageManager.getInstance().getImage(name.toUpperCase());
        imageView = new ImageView(image);

        double[] params = ImageManager.getInstance().getEnemyImageParams(name);
        offsetX = params[0];
        offsetY = params[1];
        WIDTH = params[2];
        HEIGHT = params[3];
        columns = (int)params[4];
        width = WIDTH;
        height = HEIGHT;

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.setPrefSize(WIDTH,HEIGHT);

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,WIDTH, HEIGHT));

        visual = new Rectangle(WIDTH/4, HEIGHT/2, WIDTH /2 , HEIGHT /2);
        visual.setStrokeWidth(3);
        visual.setFill(Color.TRANSPARENT);
        visual.setStroke(Color.YELLOW);


        animation = new SpriteAnimation(this.imageView, Duration.millis(1000),columns,offsetX,offsetY,WIDTH,HEIGHT);
        animation.setCycleCount(1);
        rect.setWidth(WIDTH / 2);
        rect.setHeight(HEIGHT / 2);
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX + WIDTH /4);
        //this.getChildren().removeAll(this.visual);
        this.translateXProperty().addListener((obs, old, newValue)->{
            this.rect.setTranslateX(newValue.doubleValue() + WIDTH/4);
        });
        this.translateYProperty().addListener((obs, old, newValue)->{
            this.rect.setTranslateY(newValue.doubleValue() + HEIGHT/2);
        });


        getChildren().addAll(this.imageView, visual);
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
