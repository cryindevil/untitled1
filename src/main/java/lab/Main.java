package lab;

import javafx.application.Application;
import javafx.stage.Stage;
import lab.controller.SharedService;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SharedService sharedService = SharedService.getInstance();
        sharedService.init();
        sharedService.setStage(primaryStage);
        primaryStage.setScene(sharedService.startScene);
        primaryStage.setTitle("卡雷尔机器人");
        primaryStage.show();
    }
}