package lab.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.director.InputProcess;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.*;

public class SharedService {
    boolean playingNewStage=false;
    private static SharedService instance;
    public InputProcess inputProcess=new InputProcess();
    lab.map.Stage stage1=readSave("src/main/resources/ser/关卡1.ser");
    lab.map.Stage stage2=readSave("src/main/resources/ser/关卡2.ser");
    lab.map.Stage stage3=readSave("src/main/resources/ser/关卡3.ser");//将三个stage初始化
    lab.map.Stage newstage=readSave("src/main/resources/ser/新关卡.ser");
    FXMLLoader fx=new FXMLLoader(getClass().getResource("/test1/start.fxml"));
    Parent startRoot;
    public StartController startController;
    FXMLLoader fx1=new FXMLLoader(getClass().getResource("/test1/play.fxml"));//为什么不能一起
    Parent playRoot;
    public PlayController playController;
    FXMLLoader fx2=new FXMLLoader(getClass().getResource("/test1/createMap.fxml"));
    Parent createRoot;
    public CreateController createController;//获得三个控制器
    public Scene startScene;
    public Scene createScene;
    public Scene playScene;//获得三个scene
    public Stage stage;//显示图片的主stage


    public void init() throws IOException {
        startRoot= fx.load();
        startController=fx.getController();
        playRoot=fx1.load();
        playController=fx1.getController();
        createRoot=fx2.load();
        createController=fx2.getController();
        startScene=new Scene(startRoot);
        createScene=new Scene(createRoot);
        playScene=new Scene(playRoot);//获得三个scene
    }

    private SharedService() { }

    public static SharedService getInstance() {
        if (instance == null) {
            instance = new SharedService();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public lab.map.Stage getStage(){
        if (playingNewStage) return newstage;
        if(startController.string.equals("关卡1")){
            return stage1;
        }if (startController.string.equals("关卡2")){
            return stage2;
        }else return stage3;
    }//已添加newstage

    lab.map.Stage readSave(String filePath){
        lab.map.Stage stage=null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            stage = (lab.map.Stage) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stage;
    }//读取某个的存档
    public void loadSave(){
        stage1=readSave("src/main/resources/ser/关卡1.ser");
        stage2=readSave("src/main/resources/ser/关卡2.ser");
        stage3=readSave("src/main/resources/ser/关卡3.ser");//将三个stage初始化
        newstage=readSave("src/main/resources/ser/新关卡.ser");
        if (playingNewStage){
            playController.setMap(newstage);
        }else playController.setMap(getStage());
    }//读取全部的存档

}
