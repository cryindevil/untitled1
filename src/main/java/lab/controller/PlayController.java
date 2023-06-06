package lab.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lab.Things.*;
import lab.director.InputProcess;
import lab.map.Stage;

import java.io.*;
import java.util.ArrayList;

//import static jdk.internal.org.jline.utils.Colors.s;

public class PlayController {
    SharedService sharedService=SharedService.getInstance();//创建共享类
    @FXML
    public MenuItem help;
    @FXML
    public MenuItem loadGame;
    @FXML
    public MenuItem save;
    @FXML
    public GridPane map;
    @FXML
    private Button run;
    @FXML
    private TextArea codeArea;
    @FXML
    private Button playReturn;
    @FXML
//    public TextArea wrong;
    @FXML
//    public TextArea diary;
    @FXML
    public Pane smallPane;
    @FXML
    public AnchorPane bigPane;
    javafx.stage.Stage asdStage;

    public Stage initStage;
//    public boolean hasSaved=false;

    @FXML
    void loadGameItemClicked(ActionEvent event) {
        sharedService.loadSave();
    }

    @FXML
    void quitStageButtonClicked(ActionEvent event) {
        sharedService.playingNewStage=false;
        sharedService.stage.setScene(sharedService.startScene);
        sharedService.startController.string = sharedService.startController.choosingList.getValue();
        sharedService.refreshChoiceBox();
        map.getChildren().clear();
    }

    @FXML
    void runCodeButtonClicked(ActionEvent event) {
        process(enterCode(),sharedService.getStage());
        sharedService.inputProcess.setStage(sharedService.getStage());
        showDiaryWrong();
        sharedService.getStage().hasSaved=false;
    }//运行代码

    @FXML
    void saveClicked(ActionEvent event) {
        try {
            String path;
            if (sharedService.playingNewStage)path="src/main/resources/ser/新关卡.ser";
            else path="src/main/resources/ser/"+sharedService.startController.string.substring(0,3)+".ser";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            if (sharedService.playingNewStage) out.writeObject(sharedService.newstage);
                else out.writeObject(sharedService.getStage());
            out.close();
            fileOut.close();
            sharedService.getStage().hasSaved=true;
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void showDiaryWrong(){
        File file = new File(sharedService.getStage().wrongPath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (
                FileNotFoundException ex) {
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
        wrong.setText(content.toString());

        file = new File(sharedService.getStage().diaryPath);
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
        diary.setText(content.toString());

    }

    @FXML
    void resetThisStage(ActionEvent event) throws IOException {
        if (sharedService.playingNewStage){
            sharedService.newstage=new Stage();
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
            sharedService.stage.setScene(sharedService.createScene);
        }else{if(sharedService.startController.string.equals("关卡1")){
            sharedService.stage1.setStage1();
        }if (sharedService.startController.string.equals("关卡2")){
            sharedService.stage2.setStage2();
        }else sharedService.stage3.setStage3();

        setMap(sharedService.getStage());}
}//!考虑new情况

    @FXML
    void resetAllStage(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setStage1();
        sharedService.stage1=stage;
        stage=new Stage();
        sharedService.stage2=stage;
        stage=new Stage();
        sharedService.stage3=stage;

        sharedService.newstage=new Stage();
        if (sharedService.playingNewStage){
                sharedService.newstage=new Stage();
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
            sharedService.stage.setScene(sharedService.createScene);
        }else {
            setMap(sharedService.getStage());
        }
    }//考虑new情况
    @FXML
    void changeNewStageMenu(ActionEvent event) throws FileNotFoundException {
        initStage=sharedService.getStage();
        sharedService.playingNewStage=true;

        System.out.println(sharedService.getStage().hasSaved);
        if(sharedService.playController.sharedService.getStage().hasSaved){
            map.getChildren().clear();
            sharedService.startController.string="新关卡";
            if (sharedService.newstage.hasDefined) {
                sharedService.playController.setMap(sharedService.getStage());
                sharedService.playController.showDiaryWrong();
            }
            else sharedService.stage.setScene(sharedService.createScene);

        }else {

            sharedService.startController.string="新关卡";

            asdStage.setScene(sharedService.changeScene);
            asdStage.show();



        }
    }

    @FXML
    void changeStage1Menu(ActionEvent event) throws FileNotFoundException {
        initStage=sharedService.getStage();
        sharedService.playingNewStage=false;

        System.out.println(sharedService.playController.sharedService.getStage().hasSaved);
        System.out.println(sharedService.getStage());
        System.out.println(sharedService.startController.string);
        if(sharedService.playController.sharedService.getStage().hasSaved){
            map.getChildren().clear();
            sharedService.startController.string="关卡1";
            sharedService.playController.setMap(sharedService.getStage());
            sharedService.playController.showDiaryWrong();
        }else {
            sharedService.startController.string="关卡1";
            asdStage = new javafx.stage.Stage();
            asdStage.setScene(sharedService.changeScene);
            asdStage.show();

        }
    }

    @FXML
    void changeStage2Menu(ActionEvent event) throws FileNotFoundException {
        initStage=sharedService.getStage();
        sharedService.playingNewStage=false;
        System.out.println(sharedService.getStage().hasSaved);
        System.out.println(sharedService.getStage());
        System.out.println(sharedService.startController.string);
        if(sharedService.playController.sharedService.getStage().hasSaved){
            map.getChildren().clear();
            sharedService.startController.string="关卡2";
            sharedService.playController.setMap(sharedService.getStage());
            sharedService.playController.showDiaryWrong();

        }else{   sharedService.startController.string="关卡2";
            asdStage = new javafx.stage.Stage();
            asdStage.setScene(sharedService.changeScene);
            asdStage.show();



        }
    }

    @FXML
    void changeStage3Menu(ActionEvent event) throws FileNotFoundException {
        initStage=sharedService.getStage();
        sharedService.playingNewStage=false;
        System.out.println(sharedService.getStage().hasSaved);
        System.out.println(sharedService.getStage());
        System.out.println(sharedService.startController.string);
        if(sharedService.playController.sharedService.getStage().hasSaved){
            map.getChildren().clear();

            sharedService.startController.string="关卡3";
            sharedService.playController.setMap(sharedService.getStage());
            sharedService.playController.showDiaryWrong();

        }else{
            sharedService.startController.string="关卡3";
            asdStage = new javafx.stage.Stage();
            asdStage.setScene(sharedService.changeScene);
            asdStage.show();


        }
    }

    public PlayController() throws IOException {}

    public ArrayList<String> enterCode(){
        ArrayList<String> list = new ArrayList<>();
        String[] s=codeArea.getText().split("\n");
        StringBuilder sb = new StringBuilder();
        if(s.length==1) {
            if(s[0].equals("")){
                list.add(s[0]);
                return list;
            }
            list.add(s[0].substring(0,s[0].length()-1));
            return list;
        }
        for (int i = 0; i < s.length; i++) {
            if (s[i].endsWith(";")) {
                list.add(s[i].substring(0,s[i].length()-1));
            } else {
                do { // 循环拼接直到遇到第一个“}”元素
                    sb.append(s[i]);
                    i++;
                } while (!s[i - 1].equals("}"));
                System.out.println(sb);
                list.add(sb.toString()); // 将拼接好的字符串添加到结果 ArrayList 中
                sb.setLength(0); // 清空 StringBuilder
                i--;
            }//报错
        }
        return list;
        }

    public void process(ArrayList<String>list,Stage stage){
            InputProcess inputProcess=new InputProcess();
            inputProcess.setStage(stage);
        for (String s:
                 list) {
                inputProcess.input(s);
            }
            setMap(stage);
        }

    public void setMap(Stage stage) {
        Image image = new Image("/pics/空白s.jpg");
        ImageView imageView = new ImageView(image);
        for (int i = 0; i < stage.x; i++) {
            for (int j = 0; j < stage.y; j++) {
                add("/pics/空白s.jpg", i, j);
            }
        }

        for (Thing thing :
                stage.list) {
            if (thing instanceof Wall) {
                add("/pics/墙壁s.jpg",thing.getX(),thing.getY());
            } else if (thing instanceof Rock) {
                add("/pics/石块s.jpg",thing.getX(),thing.getY());
            } else {
                add("/pics/陷阱s.jpg",thing.getX(),thing.getY());
            }
        }

        for (FilledTrap filledtrap :
                    stage.filledTraps) {
                add("/pics/填上的陷阱s.jpg",filledtrap.getX(),filledtrap.getY());
            }

        String ro=switch (stage.getRobot().getDire()){
                    case 0 ->"/pics/机器人0.jpg";
                    case 1 ->"/pics/机器人1.jpg";
                    case 2 ->"/pics/机器人2.jpg";
                    case 3 ->"/pics/机器人3.jpg";
                    default -> throw new IllegalStateException("Unexpected value: " + stage.getRobot().getDire());
                };
        add(ro,stage.getRobot().x,stage.getRobot().y);
    }//设定地图

    public void add(String s,int i,int j){
        Image image;
        ImageView imageView;
        image=new Image(s);
        imageView=new ImageView(image);
        map.add(imageView,j,i);
    }//地图上的某个格子中加入图片



}
