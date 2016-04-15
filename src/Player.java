import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by Alexander on 22.03.2016.
 */

public class Player  extends Character {
    public Player(double translateX, double translateY){
        super(translateX, translateY, "player");
    }

    @Override
    public void moveX(int value, boolean run){
        changeSize(WIDTH, HEIGHT);
        super.moveX(value, run);
    }
    @Override
    public void moveY(int value, boolean run){
        changeSize(WIDTH, HEIGHT);
        super.moveY(value, run);

    }


    public void attack(){
        changeSize(60, 70);
        animation.setColumns(5);
        animation.setOffsetX(2);
        animation.setOffsetY(513);

        animation.play();
        animation.setOnFinished(event -> {
            changeSize(WIDTH, HEIGHT);
            animation.interpolate(0);
        });
    }
    private void changeSize(double width, double height){
        this.width = width;
        this.height = height;
        this.setPrefSize(width,height);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        animation.setWidthHeight(width,height);
    }
}
