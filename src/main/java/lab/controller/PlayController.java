package lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lab.Things.*;
import lab.director.InputProcess;
import lab.map.Stage;

import java.io.*;
import java.util.ArrayList;

public class PlayController {
    SharedService sharedService=SharedService.getInstance();//创建共享类

    @FXML
    public MenuItem help;
    @FXML
    public MenuItem loadGame;
    @FXML
    public MenuItem save;
    @FXML
    private GridPane map;
    @FXML
    private Button run;
    @FXML
    private TextArea codeArea;
    @FXML
    private Button playReturn;
    @FXML
    public TextArea wrong;
    @FXML
    public TextArea diary;

    @FXML
    void loadGameItemClicked(ActionEvent event) {
        sharedService.loadSave();
    }

    @FXML
    void quitStageButtonClicked(ActionEvent event) {
        sharedService.stage.setScene(sharedService.startScene);
    }

    @FXML
    void runCodeButtonClicked(ActionEvent event) {
        sharedService.inputProcess.setStage(sharedService.getStage());
        process(enterCode(),sharedService.getStage());
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

    }//运行代码

    public PlayController() throws IOException {
    }

    public ArrayList<String> enterCode(){
        ArrayList<String> list = new ArrayList<>();
        String[] s=codeArea.getText().split("\n");
        StringBuilder sb = new StringBuilder();
        if(s.length==1) {
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

    @FXML
    void saveClicked(ActionEvent event) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/ser/"+sharedService.startController.string+".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(sharedService.getStage());
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in person.ser");
        } catch (Exception ee) {
            ee.printStackTrace();
        }
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

    @FXML
    void resetThisStage(ActionEvent event) throws IOException {
        if(sharedService.startController.string.equals("关卡1")){
            sharedService.stage1.setStage1();
        }if (sharedService.startController.string.equals("关卡2")){
            sharedService.stage2.setStage2();
        }else sharedService.stage3.setStage3();
        setMap(sharedService.getStage());
    }//!考虑new情况

    @FXML
    void resetThreeStage(ActionEvent event) throws IOException {
        sharedService.stage1.setStage1();
        sharedService.stage2.setStage2();
        sharedService.stage3.setStage3();
        setMap(sharedService.getStage());
    }//考虑new情况
}
