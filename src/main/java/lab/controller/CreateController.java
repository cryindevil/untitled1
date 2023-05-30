package lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CreateController {
    SharedService sharedService=SharedService.getInstance();//创建共享类
    public Button getCreateFan() {
        return createFan;
    }

    public CreateController() throws IOException {
    }
    @FXML
    private Button createFan;

    @FXML
    void createFanButtonClicked(ActionEvent event) {
        sharedService.stage.setScene(sharedService.startScene);
    }
}
