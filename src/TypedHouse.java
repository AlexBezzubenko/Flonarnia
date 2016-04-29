import javafx.geometry.Rectangle2D;

/**
 * Created by Alexander on 24.04.2016.
 */
public class TypedHouse extends Flobject {
    //private final double height = 200;
    //private final double width = 225;

    public TypedHouse(double translateX, double translateY){
        super(translateX, translateY, "Houses", "house_type_1");
        imageView.setViewport(new Rectangle2D(0,0,140,160));
        rect.setWidth(WIDTH /2);
        rect.setHeight(HEIGHT/2 );
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX + WIDTH / 4);
    }

    public TypedHouse(double translateX, double translateY,String type){
        super(translateX, translateY, "Houses", type);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, WIDTH / 1.5, HEIGHT/1.5));
        //imageView.setScaleX(1.5);
        //imageView.setScaleY(1.5);

        rect.setWidth(WIDTH);
        rect.setHeight(HEIGHT/2 );
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX);
    }
}
