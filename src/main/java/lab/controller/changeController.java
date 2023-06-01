package lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class changeController {
SharedService sharedService=SharedService.getInstance();
    @FXML
    void ChangeWithoutSave(ActionEvent event) {
        sharedService.playController.map.getChildren().clear();
        sharedService.playController.setMap(sharedService.getStage());
        sharedService.playController.showDiaryWrong();
        sharedService.playController.asdStage.close();

    }

    @FXML
    void changeWithSave(ActionEvent event) {
        sharedService.playController.initStage.hasSaved=true;
        sharedService.playController.map.getChildren().clear();
        sharedService.playController.setMap(sharedService.getStage());
        try {
            String path;
            if (sharedService.playingNewStage)path="src/main/resources/ser/新关卡.ser";
            else path="src/main/resources/ser/"+sharedService.startController.string+".ser";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            if (sharedService.playingNewStage) out.writeObject(sharedService.newstage);
            else out.writeObject(sharedService.getStage());
            out.close();
            fileOut.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        sharedService.playController.setMap(sharedService.getStage());
        sharedService.playController.showDiaryWrong();
        sharedService.playController.asdStage.close();
    }

}
