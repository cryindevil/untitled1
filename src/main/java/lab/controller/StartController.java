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
    public ObservableList<String> options;
    @FXML
    void initialize() {
        String s;
        if (sharedService.newstage.hasDefined)s="(已定义)";else s="(未定义)";
         options = FXCollections.observableArrayList(
                "关卡1"+sharedService.stage1.status,
                "关卡2"+sharedService.stage2.status,
                "关卡3"+sharedService.stage3.status,
                 "新关卡"+s+sharedService.newstage.status
        );
        choosingList.setItems(options);
        choosingList.setValue("关卡1"+sharedService.stage1.status);
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
    public void chooseStageButtonClicked(ActionEvent event) throws IOException {
        if (sharedService.getStage().status.equals("(未游玩)")){
            sharedService.getStage().status="(已游玩)";
            sharedService.playController.saveClicked();
            }
        System.out.println(string);
        System.out.println(sharedService.stage2.status);
        System.out.println(sharedService.stage3.status);

        if(string.contains("(已通关)")||string.contains("已失败")) {
            sharedService.playController.resetThisStage();
            sharedService.getStage().status="(已游玩)";
            sharedService.playController.saveClicked();
        }

        if (string.contains("新关卡")){
            sharedService.playingNewStage=true;
            if(sharedService.newstage.hasDefined){
                sharedService.stage.setScene(sharedService.playScene);
                sharedService.playController.setMap(sharedService.newstage);
            }else sharedService.stage.setScene(sharedService.createScene);
        }

        sharedService.playController.setMap(sharedService.getStage());
        sharedService.stage.setScene(sharedService.playScene);
        sharedService.loadSave();
        sharedService.playController.showDiaryWrong();

    }

    public StartController() throws IOException {}
}
