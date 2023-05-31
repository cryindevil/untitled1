package lab.map;

import javafx.scene.control.TextArea;
import lab.Things.*;
import lab.controller.StartController;
import lab.director.InputProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;

public class Stage implements Serializable {
	private static final long serialVersionUID = 1L;
	public ArrayList<Thing>list=new ArrayList<>();//list里面放三样东西：石头，墙，未填陷阱
	public ArrayList<FilledTrap>filledTraps=new ArrayList<>();
	public int x;
	public int y;
	public Robot robot;
	public boolean robotDefined=false;
	public boolean hasVisited;
	public String wrongPath="src/main/resources/store/新关卡wrong.txt";
	public String diaryPath="src/main/resources/store/新关卡diary.txt";

	public String status= "新关卡";

	public String functionName;
	public boolean functionDefined=false;
	public String[]commands;

	public boolean hasDefined=false;

	public boolean mapSizeDefined=false;

	public boolean win=false;
	public boolean lose=false;
	public Stage()  {

	}

	public void setRobot(int x,int y,int dire) {
		this.robot = new Robot(x,y,dire);
	}

	public void setStage1() throws IOException {
		wrongPath="src/main/resources/store/关卡1wrong.txt";
		diaryPath="src/main/resources/store/关卡1diary.txt";
		InputProcess.clearFile(wrongPath);
		InputProcess.clearFile(diaryPath);
		status="关卡1";
		x=3;
		y=6;
		list.clear();
		list.add(new Rock(1,5));
		robot=new Robot(1,0,0);
	}

	public void setStage2() throws IOException {
		wrongPath="src/main/resources/store/关卡2wrong.txt";
		diaryPath="src/main/resources/store/关卡2diary.txt";
		InputProcess.clearFile(wrongPath);
		InputProcess.clearFile(diaryPath);
		status="关卡2";
		x=3;
		y=6;
		list.clear();
		list.add(new Rock(1,5));
		list.add(new Wall(0,0));
		list.add(new Wall(1,0));
		list.add(new Wall(2,2));
		list.add(new Wall(2,3));
		list.add(new Wall(2,4));
		list.add(new Wall(2,5));
		robot=new Robot(2,0,0);
	}

	public void setStage3() throws IOException {
		wrongPath="src/main/resources/store/关卡3wrong.txt";
		diaryPath="src/main/resources/store/关卡3diary.txt";
		InputProcess.clearFile(wrongPath);
		InputProcess.clearFile(diaryPath);
		status="关卡3";
		x=5;
		y=8;
		list.clear();
		list.add(new Rock(0,7));
		list.add(new Rock(1,2));
		list.add(new Wall(0,4));
		list.add(new Wall(1,4));
		list.add(new Wall(3,4));
		list.add(new Wall(3,5));
		list.add(new Wall(3,6));
		list.add(new Wall(3,7));
		list.add(new Wall(4,4));
		list.add(new Wall(4,5));
		list.add(new Wall(4,6));
		list.add(new Wall(4,7));
		list.add(new Trap(2,4));
		robot=new Robot(4,0,0);
	}

	public Robot getRobot() {
		return robot;
	}

	public void lose(){
		status= status+"(已失败)";
	}

	public void win(){
		status=status+"(已通关)" ;
	}

	public class Robot implements Serializable {
		public int x;
		public int y;
		private int dire;
		private int rockInBag=0;
		private int rockPicked=0;

		public int getDire() {
			return dire;
		}

		public Robot(int x, int y, int dire) {
			this.x=x;
			this.y=y;
			this.setDire(dire);
		}

		public void setDire(int dire) {
			this.dire = dire;
		}

		public int facingBlockX(){
			int xFacing=x;
			if (dire==1) xFacing--;
			if (dire==3) xFacing++;
			return xFacing;
		}

		public int facingBlockY(){
			int yFacing=y;
			if (dire==0) yFacing++;
			if (dire==2) yFacing--;
			return yFacing;
		}

		public void showInformation(){
			String message = String.format("There %s %d %s on the map that you need to collect.\n",Rock.count-rockPicked <= 1 ? "is" : "are",Rock.count-rockPicked, Rock.count-rockPicked <= 1 ? "rock" : "rocks");
			InputProcess.writeToFile(diaryPath,message);
//			System.out.println(message);
			message = String.format("There %s %d %s in your bag.\n",rockInBag <= 1 ? "is" : "are",rockInBag, rockInBag <= 1 ? "rock" : "rocks");
//			System.out.println("There is "+rockInBag+" rock in your bag.");
			InputProcess.writeToFile(diaryPath,message);
			message = String.format("You are %d %s away from the nearest rock.\n", minDistance(), minDistance() <= 1 ? "step" : "steps");
			InputProcess.writeToFile(diaryPath,message);
//			System.out.println(message);
		}

		public void move(int x){
			for (int i = 0; i < x; i++) {
				move();
			}
		}

		public void turnLeft() {
			dire=(dire+1)%4;
		}

		public void move() {
			if(willRobotHitBoundary()){
				InputProcess.writeToFile(wrongPath,"You hit onto the boundary!\n");
			} else if (isFacingThing()) {
				for (Thing thing :
						list) {
					if (thing.getX() == this.facingBlockX() && thing.getY() == facingBlockY()) {
						if (thing instanceof Trap) lose();
						if (thing instanceof Wall) InputProcess.writeToFile(wrongPath,"You hit yourself onto the wall.\n");
						if (thing instanceof Rock) InputProcess.writeToFile(wrongPath,"You hit yourself onto a rock.\n");
					}
				}
			}else {
				x=facingBlockX();
				y=facingBlockY();
			}
		}

		boolean isFacingThing() {
			for (Thing thing :
					list) {
				if (thing.getX() == this.facingBlockX() && thing.getY() == facingBlockY()) {
					return true;
				}
			}
			return false;
		}

		boolean willRobotHitBoundary(){
			return facingBlockX() < 0 || facingBlockX() > Stage.this.x - 1 || facingBlockY() < 0 || facingBlockY() > Stage.this.y - 1;
		}

		public void putRock() {
			Thing trap = null;
			if (rockInBag > 0) {
				for (Thing thing :
						list) {
					if (thing.getX() == this.facingBlockX() && thing.getY() == facingBlockY() && thing instanceof Trap) {
						filledTraps.add(new FilledTrap(facingBlockX(),facingBlockY()));
						trap=thing;
						rockInBag--;
					}
				}
				if (trap!=null)list.remove(trap);
			} else InputProcess.writeToFile(wrongPath,"You don't have any rock in your bag.\n");

		}

		public int minDistance(){
			int distance=Integer.MAX_VALUE;
			for (Thing thing:
					list) {
				if (thing instanceof Rock){
					int dis=Math.abs(x-thing.getX())+Math.abs(y-thing.getY());
					if (dis<distance) {
						distance=dis;
					}
				}
			}
			return distance;
		}

		public void pickRock() {
			Thing rock = null;
			Thing rockInList = null;
			for (Thing thing :
					list) {
				if (thing.getX() == this.facingBlockX() && thing.getY() == facingBlockY() && thing instanceof Rock) {
					rock = thing;
					rockInBag++;
					rockPicked++;
					String message = String.format("Now you have %d %s in your bag", rockInBag, rockInBag <= 1 ? "rock" : "rocks");
					InputProcess.writeToFile(diaryPath,message);
//					System.out.println(message);

				}
			}
			if (rock != null) list.remove(rock);
			else {
				InputProcess.writeToFile(wrongPath,"There is no rock ahead! Please enter again.");
			}
			;
			for (Thing thing :
					list) {
				if (thing instanceof Rock) {
					rockInList = thing;
				}
			}
			if (rockInList == null) win();
		}

		public boolean noRockInBag(){
			return rockInBag == 0;
		}

		public boolean noRockPresent(){
			boolean flag=true;
			for (Thing thing:
					list) {
				if (thing.getX() == this.facingBlockX() && thing.getY() == facingBlockY() && thing instanceof Rock) flag=false;
			}
			return flag;
		}
	}


}