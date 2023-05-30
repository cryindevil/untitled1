package lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import lab.map.Stage;

import java.io.IOException;

public class StartController {
    SharedService sharedService=SharedService.getInstance();//创建共享类

    FXMLLoader fx=new FXMLLoader(getClass().getResource("/test1/start.fxml"));
    Parent startRoot= fx.load();
    StartController startController=fx.getController();
    @FXML
    public Button createStageButton;

    @FXML
    public ChoiceBox<String> choosingList;

    @FXML
    public Button chooseStageButton;

    public String string="关卡1";

    public String getString() {
        return string;
    }

    @FXML
    void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "关卡1",
                "关卡2",
                "关卡3"
        );
        choosingList.setItems(options);
        choosingList.setValue("关卡1");
        choosingList.setOnAction((event) -> {
           string = choosingList.getValue();
        });
    }//choicebox中的选项和监视器



    public StartController() throws IOException {

    }

    @FXML
    void createStageButtonClicked(ActionEvent event) {
        sharedService.stage.setScene(sharedService.createScene);
    }



    @FXML
    public void chooseStageButtonClicked(ActionEvent event) {
        sharedService.playController.setMap(sharedService.getStage());
        sharedService.stage.setScene(sharedService.playScene);
    }
}
