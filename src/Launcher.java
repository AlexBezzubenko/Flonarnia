import Flonarnia.Scenes.Authorization;
import Flonarnia.Scenes.Flonarnia;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Alexander on 14.03.2016.
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Authorization authorization = new Authorization(primaryStage);
        //Scene scene = new Scene(authorization.createContent());
        //primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Flonarnia");
        //primaryStage.setFullScreen(true);
        Flonarnia game = new Flonarnia(primaryStage, "hero");
        game.run();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
