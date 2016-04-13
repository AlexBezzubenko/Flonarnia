/**
 * Created by Notebook on 13.04.2016.
 */

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class House extends Flobject{

    private final double HEIGHT = 200;
    private final double WIDTH = 225;
    public House(double translateX, double translateY){
        super(translateX, translateY, 225, 300);

        imageView.setViewport(new Rectangle2D(0,0,140,160));

        rect.setWidth(WIDTH );
        rect.setHeight(HEIGHT );
        rect.setTranslateY(translateY+100);
        rect.setTranslateX(translateX);
    }

    public House(double translateX, double translateY,int type){
        super(translateX, translateY, 225, 300);
        switch (type){
            case 1:
                imageView.setViewport(new Rectangle2D(0,0,150,160));
            case 2:
                imageView.setViewport(new Rectangle2D(160,0,144,160));
        }
        rect.setWidth(WIDTH );
        rect.setHeight(HEIGHT );
        rect.setTranslateY(translateY+100);
        rect.setTranslateX(translateX);
    }
}
