/**
 * Created by Notebook on 13.04.2016.
 */

import javafx.geometry.Rectangle2D;

public class House extends Flobject{

    private final double height = 200;
    private final double width = 225;
    public House(double translateX, double translateY){
        super(translateX, translateY, "Flobjects", "house");
        imageView.setViewport(new Rectangle2D(0,0,140,160));
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setTranslateY(translateY+100);
        rect.setTranslateX(translateX);
    }

    public House(double translateX, double translateY,int type){
        super(translateX, translateY, "Flobjects", "house");
        switch (type){
            case 1:
                imageView.setViewport(new Rectangle2D(0,0,150,160));
            case 2:
                imageView.setViewport(new Rectangle2D(160,0,144,160));
        }
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setTranslateY(translateY+100);
        rect.setTranslateX(translateX);
    }
}
