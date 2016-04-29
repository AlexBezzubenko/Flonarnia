import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Alexander on 10.04.2016.
 */
public class Flobject extends Pane {
    protected double HEIGHT;
    protected double WIDTH;
    protected double offsetX;
    protected double offsetY;
    protected int columns;
    Image image;
    ImageView imageView;
    Rectangle rect = new Rectangle();
    Rectangle visual;

    public Flobject(double translateX, double translateY, String group, String species){
        if (group == "Enemy") {
            image = ImageManager.getInstance().getImage(species.toUpperCase());
        }else{
            image = ImageManager.getInstance().getImage(this.getClass().getName().toUpperCase());
        }
        imageView = new ImageView(image);

        double[] params = ImageManager.getInstance().getParams(group, species);
        offsetX = params[0];
        offsetY = params[1];
        WIDTH = params[2];
        HEIGHT = params[3];
        columns = (int)params[4];

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.setPrefSize(WIDTH,HEIGHT);

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        visual = new Rectangle(WIDTH/4, HEIGHT/2, WIDTH /2 , HEIGHT /2);
        visual.setStrokeWidth(3);
        visual.setFill(Color.TRANSPARENT);
        visual.setStroke(Color.YELLOW);

        this.translateXProperty().addListener((obs, old, newValue)->{
            this.rect.setTranslateX(newValue.doubleValue() + WIDTH/4);
        });
        this.translateYProperty().addListener((obs, old, newValue)->{
            this.rect.setTranslateY(newValue.doubleValue() + HEIGHT/2);
        });

        setTarget();
        getChildren().addAll(this.imageView, visual);
    }

    protected void setTarget(){
        this.setOnMouseClicked(event -> {
            Flonarnia.player.changeTarget(this);
            Flonarnia.targetPanel.changeTarget(this.getClass().getName());
        });
    }
}
