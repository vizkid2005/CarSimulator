package Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Car {
	private String makeDate;
	private String color;
	private double currSpeed; //Imperial units
	private double maxSpeed; //Imperial units
	private double rateOfAccl; //Imperial units
	private double rateOfBraking; //Imperial units

	//Instead of the x y coordinate, we point to the roadsegment it is currently in
	private double xCoordinate;
	private double yCoordinate;
	/*
	 * We have to make speeds relative to the grid size,
	 * It has to be in the order of 0.01 gridPoints per second.
	 */

	private String type; //Civilian, Cop or Rogue cars
	private String direction; //North, South, East, West
	private  int currentLane;
	private String currentAction;
	private boolean isControlled;

	Car(){
		Date date=new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		makeDate=dateFormat.format(date);
		color="RED";
		currSpeed=0.0;
		maxSpeed=100;
		rateOfAccl=5.0;
		rateOfBraking=5.0;
		xCoordinate=-1;
		yCoordinate=-1;
		type="REGULAR";  
		direction="NORTH"; 
		currentLane=1; 
		currentAction="STOP";
		isControlled=true;
	}
	
	void setCarColor(String color){
		this.color=color;
	}

	void setCurrSpeed(double currSpeed){
		this.currSpeed=currSpeed;
	}
	
	void setMaxSpeed(double maxSpeed){
		this.maxSpeed=maxSpeed;
	}
	
	void setRateOfAccl(double rateOfAccl){
		this.rateOfAccl=rateOfAccl;
	}
	
	void setRateOfBraking(double rateOfBraking){
		this.rateOfBraking=rateOfBraking;
	}
	void setCarPosition(double xCoordinate,double yCoordinate){
		this.xCoordinate=xCoordinate;
		this.yCoordinate=yCoordinate;
	}
	
	void setType(String type){
		this.type=type;
	}
	
	void setDirection(String direction){
		this.direction=direction;
	}
	
	void setCurrentLane(int currentLane){
		currentLane=this.currentLane;
	}
	
	void setIsControlled(boolean isControlled){
		this.isControlled=isControlled;
	}
	
	void getCarStatus(){
		System.out.println("***************** Car Status *******************");
		System.out.println("MakeDate : "+makeDate);
		System.out.println("color : "+color) ;
		System.out.println("currSpeed : "+currSpeed) ;
		System.out.println("maxSpeed : "+maxSpeed) ;
	    System.out.println("rateOfAccl : "+rateOfAccl) ;
		System.out.println("rateOfBreakking : "+rateOfBraking) ;
		System.out.println("xCoordinate : "+xCoordinate) ;
		System.out.println("yCoordinate : "+yCoordinate) ;
		System.out.println("type : "+type) ;  
		System.out.println("direction : "+direction) ; 
		System.out.println("currentLane : "+currentLane) ; 
		System.out.println("currentAction : "+currentAction);
		isControlled=true;
	}
	
	void getCarCoordinate(){
		System.out.println("***************** Car Co-ordinate Status *******************");
		System.out.println("xCoordinate : "+xCoordinate) ;
		System.out.println("yCoordinate : "+yCoordinate) ;
	}
	
	void stop(){
		this.currentAction="STOP";
	}
	void moveRight(){
		if(this.direction=="NORTH"){
			this.xCoordinate=this.xCoordinate+1;
			this.yCoordinate=this.yCoordinate+1;
		}else if(this.direction=="SOUTH"){
			this.xCoordinate=this.xCoordinate-1;
			this.yCoordinate=this.yCoordinate-1;
		}else if(this.direction=="WEST"){
			this.yCoordinate=this.yCoordinate+1;
			this.xCoordinate=this.xCoordinate-1;
		}else if(this.direction=="EAST"){
			this.yCoordinate=this.yCoordinate-1;
			this.xCoordinate=this.xCoordinate+1;
		}
		this.currentAction="MOVERIGHT";
	}
	
	void moveLeft(){
		if(this.direction=="NORTH"){
			this.xCoordinate=this.xCoordinate-1;
			this.yCoordinate=this.yCoordinate+1;
		}else if(this.direction=="SOUTH"){
			this.xCoordinate=this.xCoordinate+1;
			this.yCoordinate=this.yCoordinate-1;
		}else if(this.direction=="WEST"){
			this.yCoordinate=this.yCoordinate-1;
			this.xCoordinate=this.xCoordinate-1;
		}else if(this.direction=="EAST"){
			this.yCoordinate=this.yCoordinate+1;
			this.xCoordinate=this.xCoordinate+1;
		}
		this.currentAction="MOVELEFT";
	}
	
	void moveStraight(){
		if(this.direction=="NORTH"){
			this.yCoordinate=this.yCoordinate+1;
			this.xCoordinate=this.xCoordinate;
		}else if(this.direction=="SOUTH"){
			this.yCoordinate=this.yCoordinate-1;
			this.xCoordinate=this.xCoordinate;
		}else if(this.direction=="WEST"){
			this.xCoordinate=this.xCoordinate-1;
			this.yCoordinate=this.yCoordinate;
		}else if(this.direction=="EAST"){
			this.xCoordinate=this.xCoordinate+1;
			this.yCoordinate=this.yCoordinate;
		}
		this.currentAction="MOVESTRAIGHT";
	}
}

