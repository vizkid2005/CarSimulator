package Helper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;

import BigMap.RoadMap;


public class Car {
	//DecimalFormat df = new DecimalFormat("###.##");
	DecimalFormat df = new DecimalFormat("#.00000");
	double TIME=Double.parseDouble(df.format(1.0/60.0)); // Time set to 10 Sec
	boolean reward=true;
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
    private boolean isLooping; //To be set if the car is to keep looping on the same road

	//RoadMap newMap=new RoadMap();          This should be in the Scenario

    public Car(){
		Date date=new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		makeDate=dateFormat.format(date);
		color="RED";
		currSpeed=0.0;
		maxSpeed=100; //mph
		rateOfAccl=100.0;//
		rateOfBraking=5.0;
		currentDist=0.0;
		xCoordinate=-1.0;
		yCoordinate=-1.0;
		type="REGULAR";  
		direction="EW";
		currentLane=1; 
		//currentAction="STOP";
		isControlled=true;
        isLooping = true;
	}
	
	/*********************** Setters *********************/
	
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
	
	public void setCarPosition(double xCoordinate,double yCoordinate){
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
		this.currentLane=currentLane;
	}
	
	public void setIsControlled(boolean isControlled){
		this.isControlled=isControlled;
	}
	
	public void setCurrentSegment(RoadSegment currentSegment){
    	this.currentSegment=currentSegment;
    }
	
	public boolean setCarInitialPosition(RoadSegment Segment,int laneNumber,String carDirection){
		double segmentXCoordinate=Segment.getPointInGrid().getX();
		double segmentYCoordinate=Segment.getPointInGrid().getY();
		if(carDirection=="north"||carDirection=="south"){
			this.yCoordinate=segmentYCoordinate+((laneNumber-1)*0.1667);  // 1/6 =0.1667
			this.xCoordinate=segmentXCoordinate;
			return true;
		}
		else if(carDirection=="east"||carDirection=="west"){
			this.xCoordinate=segmentXCoordinate+((6-laneNumber)*0.1667);  // 1/6 =0.1667
			this.yCoordinate=Segment.getPointInGrid().getY();
			return true;
		}
	return false;
	}
	
	/************************** Getter ************************/
	
	public int getCurrentLane(){
		System.out.println("Current Lane : "+this.currentLane);
		return this.currentLane;
	}
	
	public Coordinates getCarCoordinates(){

        Coordinates c = new Coordinates(xCoordinate, yCoordinate);
		System.out.println("***************** Car Co-ordinate Status *******************");
		System.out.println("xCoordinate : "+xCoordinate) ;
		System.out.println("yCoordinate : "+yCoordinate) ;
        return c;
    }
	
	public void getCurrentSpeed(){
		System.out.println("************** Car Current Speed *******************");
		System.out.println("Current Speed : "+currSpeed);
	}
	
	public RoadSegment getCurrentSegment(){
    	return this.currentSegment;
    }
	
	/*
	 * getCarDirection() - Depending on the Road orientation and lane of a car , this function will
	 * 					   calculate the car direction.
	 * */
	
	public String getCarDirection(String orientation,int lane){
		 if(orientation.equals("EW")){
			if(lane==1||lane==2||lane==3){
				return "east";
			}
			else{
				return "west";
			}
		 }
		 else if(orientation.equals("NS")){
			 if(lane==1||lane==2||lane==3){
					return "south";
				}
				else{
					return "north";
				}
		 }
		 else{
			 return null;
		 }
	 }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }
/*
 * Accelerate : This action will accelerate Car in direction depending upon its current direction and
 *              its current lane.Also, depending upon its current segment it will decide to change the road.
 *              Each time acceleration will result in increase in current speed.So car will travel greater
 *              distance.
 * */
	
	public boolean accelerate(){             
		
		this.currentDist = this.currentDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
			// S=ut+0.5at^2 Equation of Motion
        this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	        
        if(this.prevDist>=0.5){                      // End of Segment
	        	this.prevDist=0.0;
	        	this.currentSegment=getNextSegment();
	        	if(this.currentSegment==null){
	        		reward=false;
	        	}
	        	else{
	        		System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
	        	}
	        }
        else{
        	if(this.currentSegment!=null){
		        calculateNewCoordinates();
		    }
        }
		
        this.currSpeed = this.currSpeed+(this.rateOfAccl*TIME); 
           //v = u + at (The world is progressing at the speed of 1 second)
        return reward;
    }
	
	/* 
	 * Brake : Brake will work opposite to the acceleration i.e. it will reduce speed by using equation of  
	 *         Motion.It will reduce speed , however,it will not reduce speed to zero.So as per equation
	 *         of Motion car will cover some distance. 
	 * */      
	
	public boolean brake(){
		System.out.println("*** Break ***");
		if(this.currSpeed==0){
			reward=false;
		}
		else{
	        this.currentDist = this.currentDist+((this.currSpeed*TIME) - (0.5*this.rateOfAccl*Math.pow(TIME, 2.0))); // s = ut - 1/2 at^2
	        this.prevDist=this.prevDist+((this.currSpeed*TIME) - (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	        if(this.prevDist>=0.5){                      // End of Segment
	        	this.prevDist=0.0;
	        	this.currentSegment=getNextSegment();
	        	if(this.currentSegment==null){
	        		reward=false;
	        	}
	        	else{
	        		System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
	        	}
	        }
	        else{
		    	if(this.currentSegment!=null){
			        calculateNewCoordinates();
			    }
	        }
	        this.currSpeed = this.currSpeed-(this.rateOfAccl*TIME);
	        if(!isSpeedGreaterthanZero()){
	        	this.currSpeed=0;
	        }
		}
           //v = u - at
        return reward;
    }
	
//	public boolean moveRightLane(){
//		System.out.println("***** Move Right Lane ******");
//		this.currentDist = this.currentDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
//		// S=ut+0.5at^2 Equation of Motion
//		this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
//		if(this.prevDist>=0.5){
//        	this.prevDist=0.0;
//        	this.currentSegment=getNextSegment();
//        	if(this.currentSegment==null){
//        		reward=false;
//        	}
//        	else{
//        		System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
//        	}
//        }	
//    if(this.currentSegment!=null){
//        calculateNewCoordinates();
//    }
//	}
	
	/*
	 * getNextSegment() : As each road is just the connection of different segments,this will give 
	 *                    next segment of road.The next nextSegment can be part of another road or 
	 *                    can be part of same road.This will depend on the direction,current lane 
	 *                    and current segment if its an intersection segment.
	 * 
	 * */
	
	public RoadSegment getNextSegment(){
		RoadSegment nextSegment;
		if(this.direction=="east"){
			if(this.currentSegment.isIntersection){
				if(this.currentLane==1){
					nextSegment=this.currentSegment.southSegment;
					this.currentLane=1;
					this.direction=getCarDirection("NS", this.currentLane);
				}
				else if(this.currentLane==3){
					nextSegment=this.currentSegment.northSegment;
					this.currentLane=4;
					this.direction=getCarDirection("NS", this.currentLane);
				}
				else if(this.currentLane==2){
					nextSegment=this.currentSegment.eastSegment;
					this.currentLane=2;
					this.direction=getCarDirection("EW", this.currentLane);
				}
				else{
					nextSegment= null;
				}
			}
		  else{
			  nextSegment=this.currentSegment.eastSegment;
		   }
		}
	  else if(this.direction=="west"){
		  if(this.currentSegment.isIntersection){
				if(this.currentLane==6){
					nextSegment=this.currentSegment.northSegment;
					this.currentLane=6;
					this.direction=getCarDirection("NS", this.currentLane);
				}
				else if(this.currentLane==4){
					nextSegment=this.currentSegment.southSegment;
					this.currentLane=3;
					this.direction=getCarDirection("NS", this.currentLane);
				}
				else if(this.currentLane==5){
					nextSegment=this.currentSegment.westSegment;
					this.currentLane=5;
					this.direction=getCarDirection("EW", this.currentLane);
				}
				else{
					nextSegment= null;
				}
			}
		  else{
			  nextSegment=this.currentSegment.westSegment;
		   }
	  } 
	 else if(this.direction=="north"){
		 if(this.currentSegment.isIntersection){
				if(this.currentLane==6){
					nextSegment=this.currentSegment.eastSegment;
					this.currentLane=1;
					this.direction=getCarDirection("EW", this.currentLane);
				}
				else if(this.currentLane==4){
					nextSegment=this.currentSegment.westSegment;
					this.currentLane=4;
					this.direction=getCarDirection("EW", this.currentLane);
				}
				else if(this.currentLane==5){
					nextSegment=this.currentSegment.northSegment;
					this.currentLane=5;
					this.direction=getCarDirection("NS", this.currentLane);
				}
				else{
					nextSegment= null;
				}
			}
		  else{
			  nextSegment=this.currentSegment.northSegment;
		   } 
	  }
	  else if(this.direction=="south"){
		  if(this.currentSegment.isIntersection){
				if(this.currentLane==1){
					nextSegment=this.currentSegment.westSegment;
					this.currentLane=6;
					this.direction=getCarDirection("EW", this.currentLane);
				}
				else if(this.currentLane==3){
					nextSegment=this.currentSegment.eastSegment;
					this.currentLane=3;
					this.direction=getCarDirection("EW", this.currentLane);
				}
				else if(this.currentLane==2){
					nextSegment=this.currentSegment.southSegment;
					this.currentLane=2;
					this.direction=getCarDirection("NS", this.currentLane);
				}
				else{
					nextSegment= null;
				}
			}
		  else{
			  nextSegment=this.currentSegment.southSegment;
		   }	
		}
	 else{
		 nextSegment= null;
	 }	
		
		return nextSegment;
	}

	
	/*
	 * calculateNewCoordinates() - Depending upon the direction and current segment, this will
	 * 							   calculate the next X and Y coordinates of a car.
	 *  
	 * */
	
	public void calculateNewCoordinates(){
    	
		double currentSegStartY=this.currentSegment.getPointInGrid().getY();
		double currentSegStartX=this.currentSegment.getPointInGrid().getX();
		
		if(this.direction=="west"){
	    	yCoordinate=currentSegStartY - this.prevDist;
	    	//yCoordinate=Double.parseDouble(df.format(yCoordinate));
	    }
	    
	    if(this.direction=="east"){
	    	//yCoordinate=this.getCurrentSegment().getPointInGrid().getY() + this.prevDist;
	    	yCoordinate=currentSegStartY + this.prevDist;
	    	 
	    }
	    
	    if(this.direction=="south"){
	    	xCoordinate=currentSegStartX + this.prevDist;
	    	 
	    }
	    
	    if(this.direction=="north"){
	    	xCoordinate=currentSegStartX - this.prevDist;
	    	 
	    }
    }
	
/*************** Validation Procedure *************************/
	
	boolean isSpeedGreaterthanZero(){
		if(this.currSpeed<0){
			return false;
		}
		return true;
	}
    
	public void getCarStatus(){
		System.out.println("***************** Car Status *******************");
		System.out.println("MakeDate : "+makeDate);
		System.out.println("color : "+color) ;
		System.out.println("currSpeed : "+currSpeed) ;
		System.out.println("currentDist : "+currentDist);
		System.out.println("prevDist : "+prevDist);
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

    //Call this method when a car reaches the end of the road and its isLooping flag is set to true
    public void resetPosition(RoadSegment rs){
        if (this.isLooping){
            if (this.direction.equals("north")){
                RoadSegment current = this.getCurrentSegment();
                while(current.getSouthSegment() != null){
                    current = current.getSouthSegment();
                }
                this.setCurrentSegment(current);
            }
            else if (this.direction.equals("east")){
                RoadSegment current = this.getCurrentSegment();
                while(current.getWestSegment() != null){
                    current = current.getWestSegment();
                }
                this.setCurrentSegment(current);
            }
            else if (this.direction.equals("south")){
                RoadSegment current = this.getCurrentSegment();
                while(current.getNorthSegment() != null){
                    current = current.getNorthSegment();
                }
                this.setCurrentSegment(current);
            }
            else{
                RoadSegment current = this.getCurrentSegment();
                while(current.getEastSegment() != null){
                    current = current.getEastSegment();
                }
                this.setCurrentSegment(current);
            }
            //Now rest everything, except speed, let it move with currentspeed
            //Not sure how to handle the coordinates.
            //@Aniket, I am not calculating how the coordinates will handle, I dont want to break your code

            currentDist = 0;
            prevDist = 0;

            //Handle coordinates here
        }
        //If the car is not supposed to loop
        else{
            return;
        }
    }

    
}

