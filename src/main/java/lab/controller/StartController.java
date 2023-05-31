package lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class StartController {
    SharedService sharedService=SharedService.getInstance();//创建共享类
    @FXML
    public Button createStageButton;
    @FXML
    public ChoiceBox<String> choosingList;
    @FXML
    public Button chooseStageButton;

    public String string="关卡1";
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
    @FXML
    void createStageButtonClicked(ActionEvent event) {
        sharedService.playingNewStage=true;
        if(sharedService.newstage.hasDefined){
            sharedService.stage.setScene(sharedService.playScene);
            sharedService.playController.setMap(sharedService.newstage);
        }else sharedService.stage.setScene(sharedService.createScene);

    }

    @FXML
    public void chooseStageButtonClicked(ActionEvent event) {
        sharedService.playController.setMap(sharedService.getStage());
        sharedService.stage.setScene(sharedService.playScene);
        sharedService.loadSave();
        sharedService.playController.showDiaryWrong();
    }
    public StartController() throws IOException {}
}
