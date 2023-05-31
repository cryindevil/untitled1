package lab.controller;

import lab.map.Stage;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) {

        try {
            Stage stage=new Stage();
            String path="src/main/resources/ser/新关卡.ser";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(stage);
            out.close();
            fileOut.close();
            System.out.println("ha");
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}
