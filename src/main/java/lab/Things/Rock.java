package lab.Things;

import lab.map.Stage;

import java.io.Serializable;

public class Rock extends Thing{
    public static int count;

    public Rock(int x,int y){
        setX(x);
        setY(y);
        count++;
    }

//	static int minDistance(){
//		int min=Integer.MAX_VALUE;
//        int distance;
//        for (Rock rock : rocks) {
//            distance=Math.abs(Robot.robotx-rock.x)+Math.abs(Robot.roboty-rock.y);
//            if (distance<min) {
//                min=distance;
//            }
//        }
//        return min;
//	}

//    static void placeRock(){
//        for (Rock rock : rocks) {
//            Stage.map[rock.x][rock.y]='●';
//        }
//    }
//	static void rockPicked(){
//		rocks.remove(0);
//        Robot.rock++;
//		Robot.rockPicked++;
//        String message = String.format("Now you have %d %s in your bag", Robot.rock, Robot.rock <= 1 ? "rock" : "rocks");
//        System.out.println(message);
//	}

}
