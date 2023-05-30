package lab.director;

import lab.map.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InputProcess {


    private Stage stage=new Stage();

    public void setStage(Stage stage) {
        this.stage=stage;
    }

    public void input(String input){
        if (input.matches("if\\(noRockPresent\\(\\)\\)\\{[^\\}]+\\}else\\{[^\\}]+\\}")) {
            if (stage.getRobot().noRockPresent()) {
                String ifCode = input.substring(20, input.indexOf(";") );
                simpleFunction(ifCode);
            } else {
                String elseCode = input.substring(input.indexOf("else{") + 5, input.lastIndexOf(";"));
                simpleFunction(elseCode);
            }
        } else if (input.matches("if\\(noRockInBag\\(\\)\\)\\{[^\\}]+\\}else\\{[^\\}]+\\}")) {
            if (stage.getRobot().noRockInBag()) {
                String ifCode = input.substring(18, input.indexOf(";") );
                // System.out.println(ifCode);
                simpleFunction(ifCode);
            } else {
                String elseCode = input.substring(input.indexOf("else{") + 5, input.lastIndexOf(";"));
                System.out.println(elseCode);
                simpleFunction(elseCode);
            }
        }else if (isFunctionDefination(input)) {
            stage.functionDefined=true;
           String string=String.format("You have got %s function\n",stage.functionName);
           writeToFile(stage.diaryPath,string);
//            System.out.println("You have got "+functionName+" function");
        }else if(stage.functionDefined&&input.equals(stage.functionName)){
            for (String command:stage.commands) {
                simpleFunction(command);
            }
        }else simpleFunction(input);
    }


     boolean isFunctionDefination(String input){
        if (input.matches(".*\\(\\)\\s*\\{.*\\}")) {
            stage.functionName = input.substring(0, input.indexOf('{')).trim();
            String functionBody = input.substring(input.indexOf('{') + 1, input.lastIndexOf('}')).trim();
//            System.out.println(functionBody);
            stage.commands= functionBody.split(";");
            if (!functionBody.endsWith(";")) {
                return false;
            }
            for (String command:stage.commands) {
                if (!isSimpleFunction(command)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

     boolean isSimpleFunction(String i){
        boolean jud=false;
        if (i.equals("turnLeft()")||i.equals("pickRock()")||i.equals("putRock()")||i.equals("noRockPresent()")||i.equals("noRockInBag()")||i.equals("move()")||isMoveNum(i)||i.equals("showInformation()")){
            jud=true;
        }
        return jud;
    }

     boolean isMoveNum(String i){
        boolean jud=false;
        if(i.startsWith("move(") && i.endsWith(")")){
            String numStr = i.substring(5, i.length() - 1);
            int num;
            try {
                num = Integer.parseInt(numStr);
                jud=true;
            } catch (NumberFormatException e) {};
        }
        return jud;
    }

     void simpleFunction(String i){
        if(isMoveNum(i)){
            String numStr = i.substring(5, i.length() - 1);
            int num;
            try {
                num = Integer.parseInt(numStr);
                stage.getRobot().move(num);
            } catch (NumberFormatException e) {
                writeToFile(stage.wrongPath,"Invalid input. Please enter again.\n");
//                stageystem.out.println();
            }
        }else {
            switch (i) {
                case "Q"://case"q":
                    stage.lose();
                case "turnLeft()"://case"t":
                    stage.getRobot().turnLeft();
                    break;
                case "pickRock()"://case"p":
                    stage.getRobot().pickRock();
                    break;
                case "showInformation()"://case"s":
                    stage.getRobot().showInformation();
                    break;
                case "move()":
                    stage.getRobot().move();
                    break;
                case"putRock()"://case "pu":
                    stage.getRobot().putRock();
                    break;
                case"noRockPresent()"://case "np":
                    System.out.println(stage.getRobot().noRockPresent());
                    break;
                case"noRockInBag()"://case "nb":
                    System.out.println(stage.getRobot().noRockInBag());
                    break;
                default:writeToFile(stage.wrongPath,"Invalid input. Please enter again.\n");
                    break;
            }
        }
    }

    public static void writeToFile(String filePath, String content) {
        try {
            FileWriter writer = new FileWriter(filePath, true); // 追加写入模式
            writer.write(content);
            writer.close();
//            System.out.println("内容已经成功写入文件 " + filePath);
        } catch (IOException e) {
//            System.out.println("发生了IO异常：" + e);
        }
    }
//    public static void writeToFile(String content, File file) throws IOException {
//        FileWriter fw = new FileWriter(file);
//        PrintWriter pw = new PrintWriter(fw);
//        pw.write(content);
//        pw.close();
//        fw.close();
//    }

    public static void clearFile(String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath, false);
        fw.write("");
        fw.close();

}

}
