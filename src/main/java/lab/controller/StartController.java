package lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import lab.map.Stage;

public class StartController {
    @FXML
    private Button chuangjian;

    @FXML
    private ChoiceBox<String> liebiao;

    @FXML
    private Button xuanze;

    public String string="关卡1";

    public String getString() {
        return string;
    }

    @FXML
    void initialize() {//choicebox中的选项和监视器
        ObservableList<String> options = FXCollections.observableArrayList(
                "关卡1",
                "关卡2",
                "关卡3"
        );
        liebiao.setItems(options);
        liebiao.setValue("关卡1");
//        System.out.println(liebiao.getValue());
        liebiao.setOnAction((event) -> {
           string = liebiao.getValue();
        });
    }

    public StartController(){
    }

    @FXML
    void chuangjian(ActionEvent event) {
    }


    public Button getChuangjian() {
        return chuangjian;
    }

    public Button getXuanze() {
        return xuanze;
    }

    @FXML
    void clickXuan(ActionEvent event) {

    }
}
