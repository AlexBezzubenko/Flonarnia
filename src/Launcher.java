import Flonarnia.Scenes.Flonarnia;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Alexander on 14.03.2016.
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Authorization authorization = new Authorization(primaryStage);
        Scene scene = new Scene(authorization.createContent());*/
        //primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Flonarnia");
        //primaryStage.setResizable(false);
        //primaryStage.setFullScreen(true);
        Flonarnia game = new Flonarnia(primaryStage);
        game.run();
        /*File file = new File("src/file.txt");

        try (FileReader reader= new FileReader("src/file.txt")) {
            char [] a = new char[50];
            reader.read(a);
            System.out.println(a);
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
