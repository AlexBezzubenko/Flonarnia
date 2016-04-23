import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Alexander on 10.04.2016.
 */
public class Flobject extends Pane {
    protected double HEIGHT = 200;
    protected double WIDTH = 150;
    Image image;
    ImageView imageView;
    Rectangle rect = new Rectangle();
    Rectangle visual;

    public Flobject(double translateX, double translateY, double WIDTH, double HEIGHT){
        image = ImageManager.getInstance().getImage(this.getClass().getName().toUpperCase());
        imageView = new ImageView(image);
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.setPrefSize(WIDTH,HEIGHT);

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        visual = new Rectangle(WIDTH/4, HEIGHT/2, WIDTH /2 , HEIGHT /2);
        visual.setStrokeWidth(3);
        visual.setFill(Color.TRANSPARENT);
        visual.setStroke(Color.YELLOW);
        this.setOnMouseClicked(event -> {
            Flonarnia.targetPanel.changeTarget(this.getClass().getName());
        });

        getChildren().addAll(this.imageView, visual);

        this.translateXProperty().addListener((obs, old, newValue)->{
            this.rect.setTranslateX(newValue.doubleValue() + WIDTH/4);
        });
        this.translateYProperty().addListener((obs, old, newValue)->{
            this.rect.setTranslateY(newValue.doubleValue() + HEIGHT/2);
        });
    }
    public Flobject(){
        this.setOnMouseClicked(event -> {
            Flonarnia.targetPanel.changeTarget(this.getClass().getName());
        });
    }
}
