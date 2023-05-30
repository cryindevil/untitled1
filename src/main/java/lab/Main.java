package lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.controller.CreateController;
import lab.controller.PlayController;
import lab.controller.StartController;
import lab.director.InputProcess;

import java.io.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        InputProcess inputProcess=new InputProcess();

        lab.map.Stage stage1=new lab.map.Stage();

//        stage1.setStage1();
        stage1=readSave("src/main/resources/ser/关卡1.ser");
//        InputProcess inputProcessStage1=new InputProcess();
//        inputProcessStage1.setStage(stage1);

        lab.map.Stage stage2=new lab.map.Stage();
        stage2=readSave("src/main/resources/ser/关卡2.ser");
//        stage2.setStage2();
//        InputProcess inputProcessStage2=new InputProcess();
//        inputProcessStage2.setStage(stage2);

        lab.map.Stage stage3=new lab.map.Stage();
        stage3=readSave("src/main/resources/ser/关卡3.ser");
//        stage3.setStage3();
//        InputProcess inputProcessStage3=new InputProcess();
//        inputProcessStage3.setStage(stage3);



        FXMLLoader fx=new FXMLLoader(getClass().getResource("/test1/start.fxml"));
        Parent startRoot= fx.load();
        StartController startController=fx.getController();

        FXMLLoader fx1=new FXMLLoader(getClass().getResource("/test1/play.fxml"));//为什么不能一起
        Parent playRoot=fx1.load();
        PlayController playController=fx1.getController();

        FXMLLoader fx2=new FXMLLoader(getClass().getResource("/test1/createMap.fxml"));
        Parent createRoot=fx2.load();
        CreateController createController=fx2.getController();

        Scene startScene=new Scene(startRoot);
        Scene createScene=new Scene(createRoot);
        Scene playScene=new Scene(playRoot);

        lab.map.Stage finalStage6 = stage2;
        lab.map.Stage finalStage7 = stage3;
        lab.map.Stage finalStage8 = stage1;
        startController.getXuanze().setOnAction(e->{//选择该关卡进行游戏
            lab.map.Stage stage=null;
           playController.getMap().getChildren().clear();
            primaryStage.setScene(playScene);
            if (startController.string.equals("关卡2")) {
            sendStage(stage2,);



                playController.setMap(finalStage6);
            }
            else if (startController.string.equals("关卡3")) {
                playController.setMap(finalStage7);
            }
            else {
                playController.setMap(finalStage8);
            }

        });

        startController.getChuangjian().setOnAction(e->{
            primaryStage.setScene(createScene);
        });

        createController.getCreateFan().setOnAction(e->{
            primaryStage.setScene(startScene);
        });

        playController.getPlayReturn().setOnAction(e->{
            primaryStage.setScene(startScene);
        });

        lab.map.Stage finalStage3 = stage2;
        lab.map.Stage finalStage4 = stage3;
        lab.map.Stage finalStage5 = stage1;
        playController.getRun().setOnAction(e->{
//           InputProcess inputProcess;
            lab.map.Stage stage;
            if (startController.string.equals("关卡2")) {
//                inputProcess = inputProcessStage2;
//                inputProcess.setStage(stage2);
                stage= finalStage3;
            }
            else if (startController.string.equals("关卡3")) {
//                inputProcess = inputProcessStage3;
                stage= finalStage4;
            }
            else {
//                inputProcess = inputProcessStage1;
                stage= finalStage5;
            }
            playController.process(playController.enterCode(),stage);

            File file = new File(stage.wrongPath);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String line;
            StringBuilder content = new StringBuilder();
            while (true) {
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                content.append(line).append("\n");
            }
            try {
                reader.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            playController.wrong.setText(content.toString());

            file = new File(stage.diaryPath);
            reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            content = new StringBuilder();
            while (true) {
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                content.append(line).append("\n");
            }
            try {
                reader.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            playController.diary.setText(content.toString());
        });

        lab.map.Stage finalStage = stage2;
        lab.map.Stage finalStage1 = stage3;
        lab.map.Stage finalStage2 = stage1;

        playController.save.setOnAction(e->{//保存游戏的按键 反序列化
            if (startController.string.equals("关卡2")) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("src/main/resources/ser/关卡2.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(finalStage);
                    out.close();
                    fileOut.close();
                    System.out.printf("Serialized data is saved in person.ser");
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            else if (startController.string.equals("关卡3")) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("src/main/resources/ser/关卡3.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(finalStage1);
                    out.close();
                    fileOut.close();
                    System.out.printf("Serialized data is saved in person.ser");
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            else {
                try {
                    FileOutputStream fileOut = new FileOutputStream("src/main/resources/ser/关卡1.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(finalStage2);

                    out.close();
                    fileOut.close();
                    System.out.printf("Serialized data is saved in person.ser");
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        });

        primaryStage.setScene(startScene);
        primaryStage.setTitle("卡雷尔机器人");
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        super.init();
    }
//

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
    }
    void setStage2(Stage old){
        s=old;
    }
}
