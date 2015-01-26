package Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Car {
	private String makeDate;
	private String color;
	private double currSpeed;
	private double maxSpeed;
	private double rateOfAccl;
	private double rateOfBreakking;
	private double xCoordinate;
	private double yCoordinate;
	private String type; 
	private String direction;
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
		rateOfBreakking=5.0;
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
	
	void setRateOfBreaking(double rateOfBreakking){
		this.rateOfBreakking=rateOfBreakking;
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
		System.out.println("rateOfBreakking : "+rateOfBreakking) ;
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

