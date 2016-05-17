package Flonarnia.Panels;

import Flonarnia.Heroes.Strategy.Strategy;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.io.File;

/**
 * Created by Alexander on 14.05.2016.
 */
public class LogPanel extends Panel {
    private Text log = new Text();
    private ScrollPane scrollPane = new ScrollPane();
    private int linesAmount = 0;
    public LogPanel(double translateX, double translateY, Pane pane){
        super(translateX, translateY, pane);

        scrollPane.setTranslateY(10);
        File f = new File("src/Flonarnia/Panels/log.css");
        scrollPane.getStylesheets().clear();
        scrollPane.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        scrollPane.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");
        log.setWrappingWidth(180);
        log.setTranslateX(10);
        log.setFill(Color.GOLD);
        scrollPane.setContent(log);
        this.getChildren().add(scrollPane);

        scrollPane.setPrefSize(200, 100);
        this.setPrefSize(200, 120);
        this.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");

        pane.heightProperty().addListener((obs,old,newValue)->{
            this.setTranslateY(newValue.doubleValue() - this.getHeight() - 20);
        });
    }
    public void addLine(String line){
        String currentText = log.getText();
        if (currentText.isEmpty())
            log.setText(line);
        else
            log.setText(currentText + "\n" + line);
        linesAmount++;
        scrollPane.setVvalue(1.0);
    }
}
