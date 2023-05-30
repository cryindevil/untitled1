package lab;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimerExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the AnchorPane and set its properties
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(400, 400);

        // Create the GridPane and set its properties
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the GridPane to the AnchorPane
        AnchorPane.setTopAnchor(gridPane, 50.0);
        AnchorPane.setLeftAnchor(gridPane, 50.0);
        AnchorPane.setRightAnchor(gridPane, 50.0);
        AnchorPane.setBottomAnchor(gridPane, 50.0);
        anchorPane.getChildren().add(gridPane);

        // Create the Label to display the timer
        Label timerLabel = new Label("00:00:00");

        // Add the timerLabel to the GridPane
        gridPane.add(timerLabel, 0, 0);

        // Create the Timeline to update the timer every second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int seconds = Integer.parseInt(timerLabel.getText().split(":")[2]);
                    int minutes = Integer.parseInt(timerLabel.getText().split(":")[1]);
                    int hours = Integer.parseInt(timerLabel.getText().split(":")[0]);

                    seconds++;

                    if (seconds == 60) {
                        seconds = 0;
                        minutes++;
                    }

                    if (minutes == 60) {
                        minutes = 0;
                        hours++;
                    }

                    timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Create the Scene and add it to the Stage
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
