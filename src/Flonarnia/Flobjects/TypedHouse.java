package Flonarnia.Flobjects;

import javafx.geometry.Rectangle2D;

/**
 * Created by Alexander on 24.04.2016.
 */
public class TypedHouse extends Flobject {
    private final double scale = 1.5;
    public TypedHouse(double translateX, double translateY){
        super(translateX, translateY, "Houses", "house_type_1");
        imageView.setViewport(new Rectangle2D(0, 0, 140, 160));
        bounds.setWidth(WIDTH / 2);
        bounds.setHeight(HEIGHT / 2 );
        bounds.setTranslateY(translateY + HEIGHT / 2);
        bounds.setTranslateX(translateX + WIDTH / 4);
    }

    public TypedHouse(double translateX, double translateY,String type){
        super(translateX, translateY, "Houses", type);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, WIDTH / scale, HEIGHT / scale));
        bounds.setWidth(WIDTH);
        bounds.setHeight(HEIGHT / 2 );
        bounds.setTranslateY(translateY + HEIGHT / 2);
        bounds.setTranslateX(translateX);
    }
}
