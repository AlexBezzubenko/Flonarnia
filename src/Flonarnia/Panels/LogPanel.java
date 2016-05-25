package Flonarnia.Panels;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;

/**
 * Created by Alexander on 14.05.2016.
 */
public class LogPanel extends Panel {
    private TextFlow log = new TextFlow();
    private ScrollPane scrollPane = new ScrollPane();
    public LogPanel(double translateX, double translateY, Pane pane){
        super(translateX, translateY, pane);

        scrollPane.setTranslateY(10);
        scrollPane.getStylesheets().add("Flonarnia/Panels/log.css");
        scrollPane.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");

        log.setTranslateX(10);
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
        addLine(line, Color.GOLD);
    }
    public void addLine(String line, Color color){
        Text text = new Text();
        text.setWrappingWidth(180);
        text.setFill(color);

        if (log.getChildren().size() > 20)
            log.getChildren().clear();

        if (log.getChildren().isEmpty())
            text.setText(line);
        else
            text.setText("\n" + line);

        log.getChildren().addAll(text);
        scrollPane.setVvalue(scrollPane.getVmax());
    }
}
