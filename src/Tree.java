/**
 * Created by Alexander on 04.04.2016.
 */

public class Tree  extends Flobject {
    public Tree(double translateX, double translateY){
        super(translateX, translateY, "Flobjects", "tree");

        rect.setWidth(WIDTH /2);
        rect.setHeight(HEIGHT/2 );
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX + WIDTH / 4);
    }
}

