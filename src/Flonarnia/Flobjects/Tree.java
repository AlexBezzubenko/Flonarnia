package Flonarnia.Flobjects;


/**
 * Created by Alexander on 04.04.2016.
 */

public class Tree  extends Flobject {
    public Tree(double translateX, double translateY){
        super(translateX, translateY, "Flobjects", "tree");

        bounds.setWidth(WIDTH /2);
        bounds.setHeight(HEIGHT/2 );
        bounds.setTranslateY(translateY + HEIGHT / 2);
        bounds.setTranslateX(translateX + WIDTH / 4);
    }
}

