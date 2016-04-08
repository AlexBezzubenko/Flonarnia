import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Alexander on 04.04.2016.
 */

public class Object  extends Pane {

    private int HEIGHT = 200;
    private int WIDTH = 150;
    Image image = ImageManager.getInstance().getImage("TREE_0");
    ImageView imageView = new ImageView(image);
    Rectangle rect = new Rectangle();

    public Object(int translateX, int translateY){

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);

        this.setPrefSize(WIDTH,HEIGHT);

        //this.setStyle("-fx-border-color: red;\n" + "-fx-border-width: 5;\n");
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);


        rect.setWidth(WIDTH / 2);
        rect.setHeight(HEIGHT / 2);
        rect.setTranslateY(translateY + HEIGHT / 2);
        rect.setTranslateX(translateX + WIDTH / 4);

        Rectangle visual = new Rectangle(WIDTH/4, HEIGHT/2, WIDTH /2 , HEIGHT /2);

        visual.setStrokeWidth(3);
        visual.setFill(Color.TRANSPARENT);
        visual.setStroke(Color.YELLOW);

        getChildren().addAll(this.imageView, visual);
    }
}
