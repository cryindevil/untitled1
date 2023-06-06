package lab.controller;

import lab.map.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class reset {
    public static void main(String[] args) throws IOException {
//        SharedService sharedService = SharedService.getInstance();
//        sharedService.stage1.status = "(未游玩)";
//        sharedService.stage2.status = "(未游玩)";
//        sharedService.stage3.status = "(未游玩)";
//        System.out.println("OK");
                    Stage stage=new Stage();
//            stage.setStage3();
        try {
            // 创建一个 FileOutputStream 对象来写入文件
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/ser/新关卡.ser");

            // 创建一个 ObjectOutputStream 对象来序列化对象
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // 将对象写入文件
            out.writeObject(stage);

            // 关闭输出流
            out.close();

            // 关闭文件流
            fileOut.close();

            System.out.println("Serialized data is saved in example.ser");
        } catch(IOException i) {
            i.printStackTrace();
        }
    }
    }

