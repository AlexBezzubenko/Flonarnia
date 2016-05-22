package Flonarnia.Flobjects;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Alexander on 18.05.2016.
 */
public class MountainH extends Flobject {
    public MountainH(double translateX, double translateY){
        super(translateX, translateY, "Flobjects", "mountainH");

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.setPrefSize(WIDTH,HEIGHT);

        imageView.setViewport(new Rectangle2D(offsetX, offsetY, WIDTH, HEIGHT));

        bounds.setWidth(WIDTH);
        bounds.setHeight(HEIGHT);
        bounds.setTranslateY(translateY);
        bounds.setTranslateX(translateX);

        this.translateXProperty().addListener((obs, old, newValue)->{
            this.bounds.setTranslateX(newValue.doubleValue());
        });
        this.translateYProperty().addListener((obs, old, newValue)->{
            this.bounds.setTranslateY(newValue.doubleValue());
        });
    }
}
