package lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lab.Things.*;

import java.io.IOException;

public class CreateController {
    SharedService sharedService=SharedService.getInstance();//创建共享类
    @FXML
    private Button trapConfirmedButton;
    @FXML
    private Label label;
    @FXML
    private Button wallConfirmedButton;
    @FXML
    private Button rockConfirmedButton;
    @FXML
    private Button lockNum;
    @FXML
    private Button nextButton;
    @FXML
    private Button robotConfirmedButton;
    @FXML
    private Button createFan;
    @FXML
    private TextField longField;
    @FXML
    private GridPane map;
    @FXML
    private TextField wideField;
    @FXML
    private GridPane robotFrame;
    @FXML
    private Button rotateButton;
    int robotDire=1;
    int drawMapX=0;
    int drawMapY=0;

    @FXML
    void next(ActionEvent event) {
        add("/pics/空白s.jpg",drawMapX,drawMapY);
        roll();
    }

    @FXML
    void robotConfirmed(ActionEvent event) {
        sharedService.newstage.setRobot(drawMapX,drawMapY,robotDire);
        setMap();
        roll();
        robotConfirmedButton.setVisible(false);
        rotateButton.setVisible(false);
    }

    @FXML
    void rockConfirmed(ActionEvent event) {
        sharedService.newstage.list.add(new Rock(drawMapX, drawMapY));
        setMap();
        roll();
    }

    @FXML
    void trapConfirmed(ActionEvent event) {
        sharedService.newstage.list.add(new Trap(drawMapX, drawMapY));
        setMap();
        roll();
    }

    @FXML
    void wallConfirmed(ActionEvent event) {
        sharedService.newstage.list.add(new Wall(drawMapX, drawMapY));
        setMap();
        roll();
    }

    @FXML
    void createFanButtonClicked(ActionEvent event) {
        sharedService.stage.setScene(sharedService.startScene);
        sharedService.playingNewStage=false;
    }

    @FXML
    void confirmButtonClicked(ActionEvent event) {
        String regex = "[2-9]|10";  // 正则表达式，匹配1到10的数字
        String textWide = wideField.getText();  // 获取TextField的文本
        String textLong = longField.getText();
        if (textWide.matches(regex)&&textWide.matches(regex)) {
            label.setText("已锁定");
            sharedService.newstage.x=Integer.parseInt(textWide);
            sharedService.newstage.y=Integer.parseInt(textLong);
            wideField.setEditable(false);
            longField.setEditable(false);
            for (int i = 0; i < sharedService.newstage.x; i++) {
                for (int j = 0; j < sharedService.newstage.y; j++) {
                    add("/pics/空白s.jpg",i,j);
                }
            }
            sharedService.newstage.mapSizeDefined=true;
            add("pics/选择.jpg",0,0);
            lockNum.setVisible(false);
        } else {
            label.setText("输入不正确");
        }
    }

    @FXML
    void rotateButtonClicked(ActionEvent event) {
        robotDire=robotDire+1;
        robotDire=(robotDire)%4;
        String picPath=switch (robotDire){
            case 0->"pics/机器0.jpg";
            case 1->"pics/机器1.jpg";
            case 2->"pics/机器2.jpg";
            default ->"pics/机器3.jpg";
        };
        Image image;
        ImageView imageView;
        image=new Image(picPath);
        imageView=new ImageView(image);
        robotFrame.add(imageView,0,0);
    }

    @FXML
    void startGame(ActionEvent event) {
        if (drawMapX==10&&drawMapY==10){
            drawMapX=0;drawMapY=0;
            sharedService.newstage.hasDefined=true;
            sharedService.playingNewStage=true;
            sharedService.playController.showDiaryWrong();
            sharedService.playController.setMap(sharedService.newstage);
            sharedService.stage.setScene(sharedService.playScene);
            map.getChildren().clear();
            nextButton.setVisible(true);
            rockConfirmedButton.setVisible(true);
            wallConfirmedButton.setVisible(true);
            trapConfirmedButton.setVisible(true);
            robotConfirmedButton.setVisible(true);
            rotateButton.setVisible(true);
            wideField.setEditable(true);
            longField.setEditable(true);
            lockNum.setVisible(true);
            label.setText("");
        }else label.setText("地图未配置完全");
    }

    void roll() {
        System.out.println(sharedService.newstage.robot==null);
        drawMapY++;
        System.out.println(drawMapX);
        System.out.println(drawMapY);
        if(drawMapY == sharedService.newstage.y-1&&drawMapX == sharedService.newstage.x-1&&sharedService.newstage.robot==null){
            nextButton.setVisible(false);
            rockConfirmedButton.setVisible(false);
            wallConfirmedButton.setVisible(false);
            trapConfirmedButton.setVisible(false);
            label.setText("必须要配置一个机器人");
        }
        if (drawMapY == sharedService.newstage.y) {
            if (drawMapX < sharedService.newstage.x - 1) {
                drawMapX++;
                drawMapY=0;
            }else {
                drawMapX=drawMapY=10;
                nextButton.setVisible(false);
                rockConfirmedButton.setVisible(false);
                wallConfirmedButton.setVisible(false);
                trapConfirmedButton.setVisible(false);
                robotConfirmedButton.setVisible(false);
                rotateButton.setVisible(false);
                return;
            }
        }
        add("pics/选择.jpg",drawMapX,drawMapY);
    }

    public CreateController() throws IOException {}

    void setMap() {
        for (int i = 0; i < sharedService.newstage.x; i++) {
            for (int j = 0; j < sharedService.newstage.y; j++) {
                add("/pics/空白s.jpg", i, j);
            }
        }

        for (Thing thing :
                sharedService.newstage.list) {
            if (thing instanceof Wall) {
                add("/pics/墙壁s.jpg",thing.getX(),thing.getY());
            } else if (thing instanceof Rock) {
                add("/pics/石块s.jpg",thing.getX(),thing.getY());
            } else {
                add("/pics/陷阱s.jpg",thing.getX(),thing.getY());
            }
        }

        for (FilledTrap filledtrap :
                sharedService.newstage.filledTraps) {
            add("/pics/填上的陷阱s.jpg",filledtrap.getX(),filledtrap.getY());
        }

        if(sharedService.newstage.robot!=null){
            String ro=switch (sharedService.newstage.getRobot().getDire()){
                case 0 ->"/pics/机器人0.jpg";
                case 1 ->"/pics/机器人1.jpg";
                case 2 ->"/pics/机器人2.jpg";
                case 3 ->"/pics/机器人3.jpg";
                default -> throw new IllegalStateException("Unexpected value: " + sharedService.newstage.getRobot().getDire());
            };
            add(ro,sharedService.newstage.getRobot().x,sharedService.newstage.getRobot().y);
        }
    }//设定地图

    public void add(String s,int i,int j){
        Image image;
        ImageView imageView;
        image=new Image(s);
        imageView=new ImageView(image);
        map.add(imageView,j,i);
    }//地图上的某个格子中加入图片
}
