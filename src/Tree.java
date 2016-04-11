/**
 * Created by Alexander on 04.04.2016.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tree  extends Flobject {
    private final double HEIGHT = 200;
    private final double WIDTH = 150;

    public Tree(double translateX, double translateY){
        super(translateX, translateY, 150, 200);

        rect.setWidth(WIDTH / 2);
        rect.setHeight(HEIGHT / 2);
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX + WIDTH / 4);
    }
}

