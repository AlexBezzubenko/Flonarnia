package Flonarnia.Scenes; /**
 * Created by Alexander on 24.03.2016.
 */

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Loader {
    private static final int APP_W = 800;
    private static final int APP_H = 600;

    private Line loadingBar = new Line();

    public Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        Image backgroundImg = new Image(getClass().getResourceAsStream("/Flonarnia/tools/res/loader_back.png"));
        ImageView background = new ImageView(backgroundImg);
        Image backgroundRectImage = new Image(getClass().getResourceAsStream("/Flonarnia/tools/res/rect_back.png"));
        ImageView backgroundRect = new ImageView(backgroundRectImage);
        background.setFitWidth(APP_W);
        background.setFitHeight(APP_H);

        LoadingCircle loadingCircle = new LoadingCircle();
        loadingCircle.setTranslateX(APP_W - 120);
        loadingCircle.setTranslateY(APP_H - 100);

        Line loadingBarBG = new Line(100, APP_H - 70, APP_W - 100, APP_H - 70);
        loadingBarBG.setStroke(Color.BLUE);
        loadingBar.setStartX(100);
        loadingBar.setStartY(APP_H - 70);
        loadingBar.setEndX(100);
        loadingBar.setEndY(APP_H - 70);
        loadingBar.setStroke(Color.WHITE);

        TranslateTransition rectAnimation = new TranslateTransition(Duration.seconds(2.5), backgroundRect);
        rectAnimation.setFromX(-400);
        rectAnimation.setToX(0);
        rectAnimation.setCycleCount(Animation.INDEFINITE);
        rectAnimation.setAutoReverse(true);
        rectAnimation.play();

        root.getChildren().addAll(backgroundRect, background, loadingCircle, loadingBarBG, loadingBar);
        return root;
    }

    private static class LoadingCircle extends Parent{
        private RotateTransition animation;
        public LoadingCircle(){
            Circle circle = new Circle(20);
            circle.setFill(null);
            circle.setStrokeWidth(2);
            circle.setStroke(Color.BLUE);

            Rectangle rect = new Rectangle(20,20);

            Shape shape = Shape.subtract(circle, rect);
            shape.setFill(Color.WHITE);
            getChildren().add(shape);

            animation = new RotateTransition(Duration.seconds(2.5),this);
            animation.setByAngle(-360);
            animation.setInterpolator(Interpolator.LINEAR);
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
        }
    }

    public void runLoaderTask(Stage stage){
        class ResourceLoadingTask extends Task<Void>{
            Flonarnia game;
            @Override
            protected Void call() throws Exception {

                for (int i = 0; i < 100; i++) {
                    Thread.sleep((int) (Math.random() * 100));
                    updateProgress(i + 1, 100);
                }
                game = new Flonarnia(stage);
                return null;
            }

            @Override
            protected void succeeded() {
                 game.run();
            }
        }
        ResourceLoadingTask task = new ResourceLoadingTask();
        task.progressProperty().addListener((obs,old,newValue) ->{
            double progress = newValue.doubleValue();
            loadingBar.setEndX(100 + progress * (APP_W - 200));
        });
        Thread t = new Thread(task);
        t.start();
    }
}


