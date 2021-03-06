package Helper;
import java.text.DecimalFormat;
import BigMap.RoadMap;

//Cleaned
public class Car {
	DecimalFormat df = new DecimalFormat("#.00000");
	double TIME=1; // Time set to 1Sec used for laws of motion
	boolean reward=true;

    //Car Properties
    private int carId;
    private String type; //Civilian, Cop or Rogue cars
	private String color; //Property of the car
	private double currSpeed; //Imperial units
	private double maxSpeed; //Imperial units
	private double rateOfAccl; //Imperial units depending on RoadSegment Length #VeryImportant
	private double rateOfBraking; //Imperial units depending on RoadSegment Length #VeryImportant
    private  int currentLane; //Indicates Lane Number
    private boolean isControlled; //If false, follows the default policy, else has to be controlled by policy specified by user
    private boolean isLooping; //To be set if the car is to keep looping on the same road

    //Crucial variables, simulates movement of car.
    private double currentDist=0.0;
    private double prevDist=0.0;

	//Instead of the x y coordinate, we point to the roadsegment it is currently in
	private double xCoordinate;
	private double yCoordinate;

    private RoadSegment currentSegment;
    private RoadSegment nextSegment;
	private String direction; //North, South, East, West

    //Variables below are used to keep track of the starting segment.
    //Basically, we are keeping a screenshot of what parameters were used to initialize the car
    private RoadSegment startSegment;
    private RoadMap map;
    private int startLane;
    private String startRoad;

    //This constructor is for debug purposes
    public Car(){
		//Date date=new Date();
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//makeDate=dateFormat.format(date);
		color="RED";
		currSpeed=0.0;
		maxSpeed=100; //mph
		rateOfAccl=200.0;//
		rateOfBraking=5.0;
		currentDist=0.0;
		xCoordinate=-1.0;
		yCoordinate=-1.0;
		type="REGULAR";  
		direction="EW";
		currentLane=1;
		isControlled=true;
        isLooping = true;
	}

    //This constructor is called when creating the car object
    public Car(RoadMap map, String color, String type,
               double currSpeed, double rateOfAccl,
                    double rateOfBraking, double maxSpeed,
                        int currentLane, String initialRoad,
                            boolean isLooping, boolean isControlled,
                                int carListSize){

        this.setCarColor(color);
        this.setType(type);
        this.setCurrSpeed(currSpeed);
        this.setRateOfAccl(rateOfAccl);
        this.setRateOfBraking(rateOfBraking);
        this.setMaxSpeed(maxSpeed);
        this.setCurrentLane(currentLane);
        this.setIsControlled(isControlled);
        this.setLooping(isLooping);
        this.setDirection(this.getCarDirection(map.getRoadOrientation(initialRoad, currentLane), currentLane));
        this.currentSegment = map.getRoadSegmentFromRoadName(initialRoad,currentLane);
        this.startSegment = this.currentSegment;
        this.startLane = this.getCurrentLane();
        this.map = map;
        this.startRoad = initialRoad;
        this.nextSegment = this.getNextSegment();
        this.setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
        this.setCarId((int) carListSize);
}
	
	/*********************** Setters *********************/
    public void setCarId(int carId){
    	this.carId=carId;
    }
	public void setXCoordinate(double x){
		this.xCoordinate=x;
	}
	public void setYCoordinate(double y){
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
		/*
		This method sets the initial position for cars that are ON the Road.
		Given a RoadSegment and lane number.
		 */
        double segmentXCoordinate=Segment.getPointInGrid().getX();
		double segmentYCoordinate=Segment.getPointInGrid().getY();
		if(carDirection.equals("north")){
			this.yCoordinate=segmentYCoordinate+((laneNumber-1)*0.1667);  // 1/6 =0.1667
			//TODO - Notify Aniket about Change
            this.xCoordinate=segmentXCoordinate+1;
			return true;
		}
		else if(carDirection.equals("south")){
			this.yCoordinate=segmentYCoordinate+((laneNumber-1)*0.1667);  // 1/6 =0.1667
			this.xCoordinate=segmentXCoordinate;
			return true;
		}
		else if(carDirection.equals("east")){
			this.xCoordinate=segmentXCoordinate+((6-laneNumber)*0.1667);  // 1/6 =0.1667
			this.yCoordinate=segmentYCoordinate;
			return true;
		}
		else if(carDirection.equals("west")){
			this.xCoordinate=segmentXCoordinate+((6-laneNumber)*0.1667);  // 1/6 =0.1667
			//TODO - Notify Aniket of Change
            this.yCoordinate=segmentYCoordinate+1; //Changed on March 9
			return true;
		}
	return false;
	}
    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    /************************** Getter ************************/

	public int getCarId(){
    	return this.carId;
    }
	public String getType(){
		return this.type;
	}
	public String getColor(){
		return this.color;
	}
	public double getCurrSpeed(){
		return this.currSpeed;
	}
	public double getMaxSpeed(){
		return this.maxSpeed;
	}
	public double getRateOfAccl(){
		return this.rateOfAccl;
	}
	public double getRateOfBraking(){
		return this.rateOfBraking;
	}
	public int getCurrentLane(){
		return this.currentLane;
	}
	public Coordinates getCarCoordinates(){

        Coordinates c = new Coordinates(xCoordinate, yCoordinate);
        return c;
    }
	public RoadSegment getCurrentSegment(){
    	return this.currentSegment;
    }
	public boolean isControlled(){
		return this.isControlled;
	}
	public String getDirection(){
		return this.direction;
	}
	public String getCarDirection(String orientation,int lane){
	/*
	 * getCarDirection() - Depending on the Road orientation and lane of a car , this function will
	 * 					   calculate the car direction.
	 * */
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

    //TODO - when max velocity reached then modify distance as s = ut and not ut + 0.5at^2
	public boolean accelerate(){

        /*
        * Accelerate : This action will accelerate Car depending upon its current direction and
        *              its current lane.Also, depending upon its current segment it will decide to change the road.
        *              Each time acceleration will result in increase in current speed.So car will travel greater
        *              distance.
        * */

		this.currentDist = this.currentDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0))); // S=ut+0.5at^2 Equation of Motion
        //this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
        this.prevDist=((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));

		if(this.currentDist>=RoadSegment.length){                      // End of Segment
	        	//this.prevDist=0.0;
				this.currentDist=0.0;
				this.currentSegment=getNextSegment();
	        	if(this.currentSegment==null){
                    if(isControlled){
                        reward=false;
                        return reward;}
                    else{
                        //If the car is looping then reset to initial position.
                        if(isLooping){
                            resetPosition();
                        }
                        else{
                            //What if the car is not looping ?
                            //Do we need to remove the car ?
                            //This has to be done from Scenario
                        }
                    }
	        	}
	        	else{
	        		//System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
	        	}
	        }
        else{
        	if(this.currentSegment!=null){
		        calculateNewCoordinates();
		    }

        }
		
        this.currSpeed = this.currSpeed+(this.rateOfAccl*TIME); //v = u + at (The world is progressing at the speed of 1 second)
        return reward;

    }
	public boolean accelerate(double rateOfAccl){
		this.currentDist = this.currentDist+((this.currSpeed*TIME) + (0.5*rateOfAccl*Math.pow(TIME, 2.0))); // S=ut+0.5at^2 Equation of Motion
        //this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
        this.prevDist=((this.currSpeed*TIME) + (0.5*rateOfAccl*Math.pow(TIME, 2.0)));

		if(this.currentDist>=RoadSegment.length){                      // End of Segment
	        	//this.prevDist=0.0;
				this.currentDist=0.0;
				this.currentSegment=getNextSegment();
	        	if(this.currentSegment==null){
                    if(isControlled){
                    reward=false;
	        		return reward;}
                    else{
                        //If the car is looping then reset to initial position.
                        if(isLooping){
                            resetPosition();
                        }
                        else{
                            //What if the car is not looping ?
                            //Do we need to remove the car ?
                            //This has to be done from Scenario
                        }
                    }
	        	}
	        	else{
	        		//System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
	        	}
	        }
        else{
        	if(this.currentSegment!=null){
		        calculateNewCoordinates();
		    }

        }
		
        this.currSpeed = this.currSpeed+(rateOfAccl*TIME); //v = u + at (The world is progressing at the speed of 1 second)
        return reward;

     }
	public boolean brake(){
	/*
	 * Brake : Brake will work opposite to the acceleration i.e. it will reduce speed by using equation of
	 *         Motion.It will reduce speed , however,it will not reduce speed to zero.So as per equation
	 *         of Motion car will cover some distance.
	 * */
        if(this.currSpeed<=0.0){
			reward=false;
		}
		else{
	        this.currentDist = this.currentDist+((this.currSpeed*TIME) - (0.5*this.rateOfAccl*Math.pow(TIME, 2.0))); // s = ut - 1/2 at^2
	        //this.prevDist=this.prevDist+((this.currSpeed*TIME) - (0.5*this.rateOfAccl*Math.pow(TIME, 2.0))); //TODO - @Aniket: Are you sure you don't want to use this ?
	        this.prevDist=((this.currSpeed*TIME) - (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	        if(this.currentDist>=RoadSegment.length){                      // End of Segment
	        	this.currentDist=0.0;
	        	this.currentSegment=getNextSegment();
	        	if(this.currentSegment==null){
                    if(isControlled){
                        reward=false;
                        return reward;}
                    else{
                        //If the car is looping then reset to initial position.
                        if(isLooping){
                            resetPosition();
                        }
                        else{
                            //What if the car is not looping ?
                            //Do we need to remove the car ?
                            //This has to be done from Scenario
                        }
                    }
	        	}
	        	else{
	        		//System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
	        	}
	        }
	        else{
		    	if(this.currentSegment!=null){
			        calculateNewCoordinates();
			    }
	        }
	        this.currSpeed = this.currSpeed-(this.rateOfAccl*TIME); //v = u - at
	        if(!isSpeedGreaterthanZero()){
	        	this.currSpeed=0.0;
	        }
		}
        return reward;
    }
	public boolean brake(double rateOfBreaking){
		if(this.currSpeed==0){
			reward=false;
		}
		else{
	        this.currentDist = this.currentDist+((this.currSpeed*TIME) - (0.5*rateOfBreaking*Math.pow(TIME, 2.0))); // s = ut - 1/2 at^2
	        //this.prevDist=this.prevDist+((this.currSpeed*TIME) - (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	        this.prevDist=((this.currSpeed*TIME) - (0.5*rateOfBreaking*Math.pow(TIME, 2.0)));
	        if(this.currentDist>=RoadSegment.length){                      // End of Segment
	        	this.currentDist=0.0;
	        	this.currentSegment=getNextSegment();
	        	if(this.currentSegment==null){
                    if(isControlled){
                        reward=false;
                        return reward;}
                    else{
                        //If the car is looping then reset to initial position.
                        if(isLooping){
                            resetPosition();
                        }
                        else{
                            //What if the car is not looping ?
                            //Do we need to remove the car ?
                            //This has to be done from Scenario
                        }
                    }
	        	}
	        	else{
	        		//System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
	        	}
	        }
	        else{
		    	if(this.currentSegment!=null){
			        calculateNewCoordinates();
			    }
	        }
	        this.currSpeed = this.currSpeed-(rateOfBreaking*TIME); //v = u - at
	        if(!isSpeedGreaterthanZero()){
	        	this.currSpeed=0.0;
	        }
		}
        return reward;
	}
	public boolean doNothing(){
		this.currentDist = this.currentDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));  // S=ut+0.5at^2 Equation of Motion
	    //this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
        this.prevDist=((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	    if(this.currentDist>=RoadSegment.length){                      // End of Segment
	    	this.currentDist=0.0;
	        	this.currentSegment=getNextSegment();
	        	if(this.currentSegment==null){
                    if(isControlled){
                        reward=false;
                        return reward;}
                    else{
                        //If the car is looping then reset to initial position.
                        if(isLooping){
                            resetPosition();
                        }
                        else{
                            //What if the car is not looping ?
                            //Do we need to remove the car ?
                            //This has to be done from Scenario
                        }
                    }
	        	}
	        	else{
	        		//System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
	        	}
	        }
	    else{
	    	if(this.currentSegment!=null){
		        calculateNewCoordinates();
		    }
	    }
	    return reward;
	}
	public boolean moveRightLane(){
    /*
	 * moveRightLane() : This action will change lane of car to right.If Car is already in the right
	 * 					 most lane, then if user tried using moveRightLane() , the car will run out of the
	 * 					 map and results in a negative reward.Please note that this action can not be
	 * 				     used for changing the direction of the car, but only changing the lane.
	 * */
		this.currentDist = this.currentDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));  // S=ut+0.5at^2 Equation of Motion
	    //this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
		this.prevDist=((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
	    if(this.currentDist>=RoadSegment.length){                      // End of Segment
	    	this.currentDist=0.0;
        	String prevDirection=this.direction;
        	this.currentSegment=getNextSegment();
        	if(this.direction.equals(prevDirection)){
        		return(getRightMovePosition());
        	}
        	if(this.currentSegment==null){
        		//The below code may cause the car to reset if it moves out of the designated road
                if(isControlled){
                    reward=false;
                    return reward;}
                else{
                    //If the car is looping then reset to initial position.
                    if(isLooping){
                        resetPosition();
                        return true;
                    }
                    else{
                        //What if the car is not looping ?
                        //Do we need to remove the car ?
                        //This has to be done from Scenario
                        return false;
                    }
                }
        	}
        	else{
        		//System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
        		reward=true;
        		return reward;
        	}
        }
	    
	    /************ Calculate the possible next lane ***********************/
	    else{
	    	return(getRightMovePosition());
	    }

	}
    public boolean moveLeftLane(){
	/*
	 * moveLeftLane() : This action will change lane of car to Left.If Car is already in the left
	 * 					 most lane, then if user tried using moveLeftLane() , the car will be run out of
	 * 					 map and results into negative reward.Please note that this action can not be
	 * 				     used for changing road but can be used before changing road to get car in
	 * 					 leftmost lane and then use either doNothing() or Accelerate() to change
	 * 					 the Road.So even if you are at intersection of Roads and try using moveRightLane()
	 * 				     this might give negative reward depending on the lane you are in.
	 * */
        //System.out.println("***** Move Left Lane ******");
        this.currentDist = this.currentDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0))); // S=ut+0.5at^2 Equation of Motion
        //this.prevDist=this.prevDist+((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
        this.prevDist=((this.currSpeed*TIME) + (0.5*this.rateOfAccl*Math.pow(TIME, 2.0)));
        if(this.currentDist>=RoadSegment.length){                      // End of Segment
            this.currentDist=0.0;
            String prevDirection=this.direction;
            this.currentSegment=getNextSegment();
            if(this.direction.equals(prevDirection)){
                return(getLeftMovePosition());
            }
            if(this.currentSegment==null){
                if(isControlled){
                    reward=false;
                    return reward;}
                else{
                    //If the car is looping then reset to initial position.
                    if(isLooping){
                        resetPosition();
                        return true;
                    }
                    else{
                        //What if the car is not looping ?
                        //Do we need to remove the car ?
                        //This has to be done from Scenario
                        return false;
                    }
                }
            }
            else{
                System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
                setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
                reward=true;
                return reward;
            }
        }
        else{
            /************ Calculate the possible next lane ***********************/
            return(getLeftMovePosition());
        }
    }

    public boolean getRightMovePosition(){

	    if(this.direction.equals("east")){
	    	if(this.currentLane==3||this.currentLane==2) {
	    		this.currentLane -= 1;
	    		calculateNewCoordinates();                /* Get New Y Coordinate */
	    		this.xCoordinate=this.xCoordinate+0.1667; /* Get New X Coordinate */
	    		reward=true;
	    	}
	    	else{
	    		reward=false;
	    		return reward;
	    	}
	    }
	    else if(this.direction.equals("west")){
	    	if(this.currentLane==4||this.currentLane==5) {
	    		this.currentLane+=1;
	    		calculateNewCoordinates();                /* Get New Y Coordinate */
	    		this.xCoordinate=this.xCoordinate-0.1667; /* Get New X Coordinate */
	    		reward=true;
	    	}
	    	else{
	    		reward=false;
	    		return reward;
	    	}
	    } 
	    else if(this.direction.equals("south")){
	    	if(this.currentLane==3||this.currentLane==2) {
	    		this.currentLane-=1;
	    		calculateNewCoordinates();                /* Get New X Coordinate */
	    		this.yCoordinate=this.yCoordinate-0.1667; /* Get New Y Coordinate */
	    		reward=true;
	    	}
	    	else{
	    		reward=false;
	    		return reward;
	    	}
	    }
	    else if(this.direction.equals("north")){
	    	if(this.currentLane==4||this.currentLane==5) {
	    		this.currentLane+=1;
	    		calculateNewCoordinates();                /* Get New X Coordinate */
	    		this.yCoordinate=this.yCoordinate+0.1667; /* Get New Y Coordinate */
	    		reward=true;
	    	}
	    	else{
	    		reward=false;
	    		return reward;
	    	}
	    }
	    return reward;
	}
	public boolean getLeftMovePosition(){
		if(this.direction.equals("east")){
	    	if(this.currentLane==1||this.currentLane==2) {
	    		this.currentLane+=1;
	    		calculateNewCoordinates();                /* Get New Y Coordinate */
	    		this.xCoordinate=this.xCoordinate-0.1667; /* Get New X Coordinate */
	    		reward=true;
	    	}
	    	else{
//	    		if(this.prevDist>=1 && getNextSegment()==null){      // its an intersection segment &
//	    			reward=false;                                    // there is no South segment
//	    		}
//	    		else{
//	    			this.prevDist=0.0;
//		        	this.currentSegment=getNextSegment();
//		        	System.out.println("\n ##### New Segment is : ####\n"+this.currentSegment.getPointInGrid().getX()+","+this.currentSegment.getPointInGrid().getY());
//	        		setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
//	        		reward=true;
//	    		}
	    		reward=false;
	    		return reward;
	    	}
	    }
	    else if(this.direction.equals("west")){
	    	if(this.currentLane==6||this.currentLane==5) {
	    		this.currentLane-=1;
	    		calculateNewCoordinates();                /* Get New Y Coordinate */
	    		this.xCoordinate=this.xCoordinate+0.1667; /* Get New X Coordinate */
	    		reward=true;
	    	}
	    	else{
	    		reward=false;
	    		return reward;
	    	}
	    } 
	    else if(this.direction.equals("south")){
	    	if(this.currentLane==1||this.currentLane==2) {
	    		this.currentLane+=1;
	    		calculateNewCoordinates();                /* Get New X Coordinate */
	    		this.yCoordinate=this.yCoordinate+0.1667; /* Get New Y Coordinate */
	    		reward=true;
	    	}
	    	else{
	    		reward=false;
	    		return reward;
	    	}
	    }
	    else if(this.direction.equals("north")){
	    	if(this.currentLane==6||this.currentLane==5) {
	    		this.currentLane-=1;
	    		calculateNewCoordinates();                /* Get New X Coordinate */
	    		this.yCoordinate=this.yCoordinate-0.1667; /* Get New Y Coordinate */
	    		reward=true;
	    	}
	    	else{
	    		reward=false;
	    		return reward;
	    	}
	    }
	    return reward;
	}
	public RoadSegment getNextSegment(){
    /*
	 * getNextSegment() : As each road is just the connection of different segments,this will give
	 *                    next segment of road.The next nextSegment can be part of another road or
	 *                    can be part of same road.This will depend on the direction,current lane
	 *                    and current segment if its an intersection segment.
	 *
	 * */

 		RoadSegment nextSegment;
		if(this.direction.equals("east")){
			if(this.currentSegment.eastSegment!=null){
				if(this.currentSegment.eastSegment.isIntersection){
					if(this.currentLane==1){
						nextSegment=this.currentSegment.eastSegment.southSegment;
						this.currentLane=1;
						this.direction=getCarDirection("NS", this.currentLane);
					}
					else if(this.currentLane==3){
						nextSegment=this.currentSegment.eastSegment.northSegment;
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
		  else{
				return null;
			}
		}
	  else if(this.direction.equals("west")){
		  if(this.currentSegment.westSegment!=null){
			  if(this.currentSegment.westSegment.isIntersection){
					if(this.currentLane==6){
						nextSegment=this.currentSegment.westSegment.northSegment;
						this.currentLane=6;
						this.direction=getCarDirection("NS", this.currentLane);
					}
					else if(this.currentLane==4){
						nextSegment=this.currentSegment.westSegment.southSegment;
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
		  else{
			  return null;
		  }
	  } 
	 else if(this.direction.equals("north")){
		 if(this.currentSegment.northSegment!=null){
			 if(this.currentSegment.northSegment.isIntersection){
					if(this.currentLane==6){
						nextSegment=this.currentSegment.northSegment.eastSegment;
						this.currentLane=1;
						this.direction=getCarDirection("EW", this.currentLane);
					}
					else if(this.currentLane==4){
						nextSegment=this.currentSegment.northSegment.westSegment;
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
		else{
			return null;
		}
	  }
	  else if(this.direction.equals("south")){
		  if(this.currentSegment.southSegment!=null){
			  if(this.currentSegment.southSegment.isIntersection){
					if(this.currentLane==1){
						nextSegment=this.currentSegment.southSegment.westSegment;
						this.currentLane=6;
						this.direction=getCarDirection("EW", this.currentLane);
					}
					else if(this.currentLane==3){
						nextSegment=this.currentSegment.southSegment.eastSegment;
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
			  return null;
		  }
		}
	 else{
		 nextSegment= null;
	 }
		return nextSegment;
	}
	public void calculateNewCoordinates(){
    /*
	 * calculateNewCoordinates() - Depending upon the direction and current segment, this will
	 * 							   calculate the next X and Y coordinates of a car.
	 * */
		if(this.direction.equals("west")){
			yCoordinate=yCoordinate - this.prevDist;
	    }
	    else if(this.direction.equals("east")){
	        yCoordinate=yCoordinate + this.prevDist;
	    }
	    else if(this.direction.equals("south")){
	    	xCoordinate=xCoordinate + this.prevDist;
	    }
	    else if(this.direction.equals("north")){
	    	xCoordinate=xCoordinate - this.prevDist;
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
		System.out.println("CarID : "+carId);
		System.out.println("Type : "+type);
		System.out.println("Is Controlled : "+isControlled);
		System.out.println("Color : "+color) ;
		System.out.println("CurrSpeed : "+currSpeed) ;
		//System.out.println("currentDist : "+currentDist);
		//System.out.println("prevDist : "+prevDist);
		System.out.println("MaxSpeed : "+maxSpeed) ;
	    System.out.println("RateOfAccl : "+rateOfAccl) ;
		System.out.println("RateOfBraking : "+rateOfBraking) ;
		System.out.println("XCoordinate : "+xCoordinate) ;
		System.out.println("YCoordinate : "+yCoordinate) ;
		System.out.println("Direction : "+direction) ;
		System.out.println("CurrentLane : "+currentLane) ;
        System.out.println("***************** Car Status Ends *******************");
	}
    public void resetPosition(){
        //Update : This should loop the car.
        if(this.isLooping){
        currentDist = 0;
        prevDist = 0;
        this.currentSegment = this.startSegment;
        this.currentLane = this.startLane;
        this.setDirection(this.getCarDirection(map.getRoadOrientation(startRoad, currentLane), currentLane));
        this.setCarInitialPosition(this.currentSegment,this.currentLane,this.direction);
        return;
        }
        //If the car is not supposed to loop
        else{
            return;
        }
        /*
        //Update 3/9: The below method fails when the default car has not followed a straight path towards the end.
        //Call this method when a car reaches the end of the road and its isLooping flag is set to true
        //@Huzefa : Remember to use this method to loop cars
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
            }*/
    }
}

