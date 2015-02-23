package Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;

import BigMap.RoadMap;

import com.sun.corba.se.impl.orbutil.closure.Constant;

public class Car {
	private double TIME=10; // Time set to 10 Sec
	private String makeDate; //Property of a car
	private String color; //Property of the car
	private double currSpeed; //Imperial units
	private double maxSpeed; //Imperial units
	private double rateOfAccl; //Imperial units
	private double rateOfBraking; //Imperial units

    //Crucial variable, simulates movement of car.
    private double currentDist;
    private double prevDist=0.0;
	//Instead of the x y coordinate, we point to the roadsegment it is currently in
	private double xCoordinate;
	private double yCoordinate;
    private RoadSegment currentSegment;
    private RoadSegment nextSegment;

	private String type; //Civilian, Cop or Rogue cars
	private String direction; //North, South, East, West
	private  int currentLane; //Indicates Lane Number
	private String currentAction; //Not needed
	private boolean isControlled; //If false, follows the default policy, else has to be controlled by policy specified by user

	//RoadMap newMap=new RoadMap();          This should be in the Scenario
	
	public Car(){
		Date date=new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		makeDate=dateFormat.format(date);
		color="RED";
		currSpeed=0.0;
		maxSpeed=100; //mph
		rateOfAccl=1.0;//
		rateOfBraking=5.0;
		currentDist=0.0;
		xCoordinate=-1.0;
		yCoordinate=-1.0;
		type="REGULAR";  
		direction="EW";
		currentLane=1; 
		//currentAction="STOP";
		isControlled=true;
	}
	
	public void xCoordinate(double x){
		this.xCoordinate=x;
	}
	
	public void yCoordinate(double y){
		this.yCoordinate=y;
	}
	
	public void setCarColor(String color){
		this.color=color;
	}

	public void setCurrSpeed(double currSpeed){
		this.currSpeed=currSpeed;
	}
	
	public void setMaxSpeed(double maxSpeed){
		this.maxSpeed=maxSpeed;
	}
	
	public void setRateOfAccl(double rateOfAccl){
		this.rateOfAccl=rateOfAccl;
	}
	
	public void setRateOfBraking(double rateOfBraking){
		this.rateOfBraking=rateOfBraking;
	}
	
	public void setCarPosition(int xCoordinate,int yCoordinate){
		this.xCoordinate=xCoordinate;
		this.yCoordinate=yCoordinate;
	}
	
	public void setType(String type){
		this.type=type;
	}
	

	public void setDirection(String direction){
		this.direction=direction;
	}
	
	
	
	public void setCurrentLane(int currentLane){
		currentLane=this.currentLane;
	}
	
	public int getCurrentLane(){
		return this.currentLane;
	}
	
	public void setIsControlled(boolean isControlled){
		this.isControlled=isControlled;
	}
	
	public void getCarStatus(){
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
	
	public void getCarCoordinate(){
		System.out.println("***************** Car Co-ordinate Status *******************");
		System.out.println("xCoordinate : "+xCoordinate) ;
		System.out.println("yCoordinate : "+yCoordinate) ;
	}

	public void accelerate(){

        this.currentDist = this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
        this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	        if(this.prevDist>=100){
	        	this.prevDist=0.0;
	        }	
	    calculateNewCoordinates();
        // NewDistance = old_Distance + (ut + 1/2 at^2)   Motion Formula considered : s = ut + 1/2 at^2
        this.currSpeed = this.currSpeed+(this.rateOfAccl*TIME); 
        //v = u + at (The world is progressing at the speed of 1 second)
    }

	public void brake(){
        this.currentDist = this.prevDist+((this.currSpeed*TIME) - (0.5*this.rateOfAccl*Math.pow(TIME, 2.0))); // s = ut - 1/2 at^2
        this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	        if(this.prevDist>=100){
	        	this.prevDist=0.0;
	        }
	    calculateNewCoordinates();
        this.currSpeed = this.currSpeed-(this.rateOfAccl*TIME); //v = u - at
    }
    
    
	public void calculateNewCoordinates(){
    	if(this.direction=="EW"){
	    	yCoordinate=this.getCurrentSegment().getPointInGrid().getY() - this.prevDist;
	    }
	    
	    if(this.direction=="WE"){
	    	yCoordinate=this.getCurrentSegment().getPointInGrid().getY() + this.prevDist;
	    }
	    
	    if(this.direction=="NS"){
	    	xCoordinate=this.getCurrentSegment().getPointInGrid().getX() + this.prevDist;
	    }
	    
	    if(this.direction=="SN"){
	    	xCoordinate=this.getCurrentSegment().getPointInGrid().getX() - this.prevDist;
	    }
    }
    
	public void setCurrentSegment(RoadSegment currentSegment){
    	currentSegment=this.currentSegment;
    }
    
    
    
	public RoadSegment getCurrentSegment(){
    	return this.currentSegment;
    }
    
//    void moveRight(){
//    	if(this.currentLane==1 
//    	
//    }

    /*
    Not needed as per new design
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

	void stop(){
		this.currentAction="STOP";
	}

    */
}

