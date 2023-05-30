package lab.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lab.Things.*;
import lab.director.InputProcess;
import lab.map.Stage;

import java.util.ArrayList;

public class PlayController {

    public Button getPlayReturn() {
        return playReturn;
    }

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

    public GridPane getMap() {
        return map;
    }

    public PlayController() {
    }

    public ArrayList<String> enterCode(){
        ArrayList<String> list = new ArrayList<>();
        String[] s=codeArea.getText().split("\n");
        StringBuilder sb = new StringBuilder();

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
            }

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

    public Button getRun() {
        return run;
    }

    @FXML
    private Button playReturn;


    @FXML
    public TextArea wrong;

    @FXML
    public TextArea diary;


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
    }

    public void add(String s,int i,int j){
        Image image;
        ImageView imageView;
        image=new Image(s);
        imageView=new ImageView(image);
        map.add(imageView,j,i);
    }
}
