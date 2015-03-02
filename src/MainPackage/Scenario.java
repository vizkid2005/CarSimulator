package MainPackage;

import BigMap.RoadMap;
import Helper.Car;
import Helper.FileUtilities;
import Helper.Policy;
import Helper.Road;
import Helper.RoadSegment;
import Initializers.ReadRoadInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This will be the object that will emulate time, distance and make everything work.
 *
 */
public class Scenario {
	
	ReadRoadInput r1=new ReadRoadInput();           // Reading coordinated from inputFile
	RoadMap newMap;						           // Creating RoadMap from Roads generated.
	ArrayList<Car> newCar;                                   // Creating a Car
	boolean reward=true;
	String action="wrong action";
	int id;           // Uniquely identifies each scenario
	int time;         // Time for Episode  
	double positiveReward=1.0; // Reward Values for positive Action
	double negativeReward=0.0; // Reward values for negative Action
	String predicatefile;

	//Gives the status of the entire environment in some form that is understandable
    public void showStatus(){

        //Throw a Status object
        Status s = new Status();
    }

    /*
     * initializeScenario() - This will initialize the scenario.Following task will be performed.
     * 						  1.Get Road details from input file.
     * 						  2.Build RoadMap
     * 						  3.Get number of cars as Input variable.
     * 						  4.Initialize all cars
     * */

    //Has to be modified a bit. To get the sequence of operations correct.
    
    public void setPredicatefile(String predicatefile){
    	this.predicatefile=predicatefile;
    }

    public void initializeScenario(RoadMap map, ArrayList<Car> carList, )
    public void initializeScenario(int numberOfCars,String fileName,String roadName,int laneNumber){
    	
    	time=1; // Initializing the time for Episode
    	id = (int) Math.round(Math.random()*100000); //5 digit random number
    	ArrayList<Road> roadList=r1.readRoadInput(fileName);    // and generating Road from it.
    	newMap=new RoadMap(roadList);
        //Even if using single car, try putting it in an ArrayList
        ArrayList<Car> allCars = new ArrayList<Car>();
    	/********* Creating a Car that can be controlled by user as per custom choice ************/
    	//newCar=new Car[numberOfCars];
    	
        newCar=new ArrayList<Car>();
    	for(int i=0;i<numberOfCars;i++){
    		newCar.add(new Car());
    	}
    	
    	newCar.get(0).setCurrentLane(laneNumber);
    	
    	RoadSegment currentSegment=newMap.getRoadSegmentFromRoadName(roadName,laneNumber); 
    	     /*Finding Current segment for a Car*/
    	newCar.get(0).setCurrentSegment(currentSegment);
    	
    	String roadOrientation=newMap.getRoadOrientation(roadName,laneNumber); 
    	     /*Getting Road orientation for Road selected by user to put his car*/
		System.out.println("roadOrientation : "+roadOrientation);
		if(roadOrientation==null){
			System.out.println("Road Do not Exist. Please enter Correct Road");
			return;
		}
		
		String carDirection=newCar.get(0).getCarDirection(roadOrientation,laneNumber);  
			/*Calculation direction of a car.*/
		if(carDirection==null){
			System.out.println("Wrong Direction.");
			return;
		}
		newCar.get(0).setDirection(carDirection);
		newCar.get(0).setCarInitialPosition(currentSegment,laneNumber,carDirection);
		//newCar.setCarPosition(4.0,2.0);
    	
		/******** Initialization Done **********/
		newCar.get(0).getCarStatus();
    }
    
    public void startScenario(){
    	int i=0;
    	
    	InputStreamReader reader=new InputStreamReader(System.in);
    	BufferedReader buf=new BufferedReader(reader);
    	
    	do{
    		System.out.println("Enter Next Action");
    		try{
    		String input=buf.readLine();
    		System.out.println("You decided to take Action : "+input);
    		time++;
    		if(input.equals("accelerate")){
    			//System.out.println("Action : Accelerate");
    			this.action="accelerate";
    			if(continueScenario(newCar.get(0).accelerate())){
    				newCar.get(0).getCarStatus();
    			}
    			else{
    				reward=false;
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    				
    			}
    		}
    		else if(input.equals("brake")){
    			//System.out.println("Action : brake");
    			this.action="brake";
    			if(continueScenario(newCar.get(0).brake())){
    				newCar.get(0).getCarStatus();
    			}
    			else{
    				reward=false;
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else if(input.equals("doNothing")){
    			//System.out.println("Action : doNothing");
    			this.action="doNothing";
    			if(continueScenario(newCar.get(0).doNothing())){
    				newCar.get(0).getCarStatus();
    			}
    			else{
    				reward=false;
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else if(input.equals("moveRightLane")){
    			//System.out.println("Action : moveRightLane");
    			this.action="moveRightLane";
    			if(continueScenario(newCar.get(0).moveRightLane())){
    				newCar.get(0).getCarStatus();
    			}
    			else{
    				reward=false;
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else if(input.equals("moveLeftLane")){
    			this.action="moveLeftLane";
    			//System.out.println("Action : moveLeftLane");
    			if(continueScenario(newCar.get(0).moveLeftLane())){
    				newCar.get(0).getCarStatus();
    			}
    			else{
    				reward=false;
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else{
    			reward=false;
    			System.out.println(" Wrong Input Entered . Closing the simulator ");
    			break;
    		}
    		}
    		catch(Exception e){
    			System.out.println("Exception :"+e.getStackTrace());
    		}
    		
    		/******* Adding to First Order Logic ***********/
    		
    		firstOrderLogic();
    		
    		/***********************************************/
    		
    	}while(true);
    	
    	firstOrderLogic();
    }
    
    boolean continueScenario(boolean reward){
    	//newCar.getCarStatus();
    	return this.reward&reward;
    }
    

    void firstOrderLogic(){
    	System.out.println("Episode : "+id);
    	String predList=new String();
    	for(Car car:newCar){
    		predList+="LOCATED_AT_TIME("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
					car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
					car.getRateOfAccl()+")\n";
    		if(car.isControlled()){
    			predList+="TOOK_ACTION("+this.id+","+this.time+","+this.action+")\n";
    			predList+="GOT_REWARD("+this.id+","+this.time+","+this.action+","+getReward()+")\n";
    			for(Car anotherCar:newCar){
    				if(car!=anotherCar&&car.getCurrentLane()==anotherCar.getCurrentLane()
    						&&car.getDirection()==anotherCar.getDirection()){
    					if(car.getDirection()=="east"){
    						if(car.getCarCoordinates().getY()>anotherCar.getCarCoordinates().getY()){
    							predList+="IN_FRONT("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
    	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
    	    							   car.getRateOfAccl()+")\n";
    						}
    						else{
    							predList+="IN_BACK("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
  	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
  	    							   car.getRateOfAccl()+")\n";
    						}
    					  predList+="DIST_FROM("+this.id+","+this.time+","+
    							      getDistanceBetween(car.getCarCoordinates().getX(),car.getCarCoordinates().getY(),
    							    	anotherCar.getCarCoordinates().getX(),anotherCar.getCarCoordinates().getX())+")\n";
    					  
    					}
    					if(car.getDirection()=="west"){
    						if(car.getCarCoordinates().getY()<anotherCar.getCarCoordinates().getY()){
    							predList+="IN_FRONT("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
 	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
 	    							   car.getRateOfAccl()+")\n";
    						}
    						else{
    							predList+="IN_BACK("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
  	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
  	    							   car.getRateOfAccl()+")\n";
    						}
      					  predList+="DIST_FROM("+this.id+","+this.time+","+
							      getDistanceBetween(car.getCarCoordinates().getX(),car.getCarCoordinates().getY(),
							    	anotherCar.getCarCoordinates().getX(),anotherCar.getCarCoordinates().getX())+")\n";
    					  
    					}
    					if(car.getDirection()=="north"){
    						if(car.getCarCoordinates().getX()<anotherCar.getCarCoordinates().getX()){
    							predList+="IN_FRONT("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
 	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
 	    							   car.getRateOfAccl()+")\n";
    						}
    						else{
    							predList+="IN_BACK("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
  	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
  	    							   car.getRateOfAccl()+")\n";
    						}
      					  predList+="DIST_FROM("+this.id+","+this.time+","+
							      getDistanceBetween(car.getCarCoordinates().getX(),car.getCarCoordinates().getY(),
							    	anotherCar.getCarCoordinates().getX(),anotherCar.getCarCoordinates().getX())+")\n";
    					  
    					}
    					if(car.getDirection()=="south"){
    						if(car.getCarCoordinates().getX()>anotherCar.getCarCoordinates().getX()){
    							predList+="IN_FRONT("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
 	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
 	    							   car.getRateOfAccl()+")\n";
    						}
    						else{
    							predList+="IN_BACK("+this.id+","+this.time+","+car.getCurrentLane()+","+car.getDirection()+","+car.getCarCoordinates().getX()+","+
 	    							   car.getCarCoordinates().getY()+","+car.getColor()+","+car.getType()+","+car.getCurrSpeed()+","+
 	    							   car.getRateOfAccl()+")\n";
    						}
      					  predList+="DIST_FROM("+this.id+","+this.time+","+
							      getDistanceBetween(car.getCarCoordinates().getX(),car.getCarCoordinates().getY(),
							    	anotherCar.getCarCoordinates().getX(),anotherCar.getCarCoordinates().getX())+")\n";
    					  
    					}
    				}
    	    			
    			}
    			
    		}
    	}
    	FileUtilities.write2File(predList,predicatefile, true);
	}
    
    double getDistanceBetween(double x1,double y1,double x2,double y2){
    	return(Math.sqrt((Math.pow((x2-x1),2)+Math.sqrt(Math.pow((y2-y1),2)))));
    }
    double getReward(){
    	if(this.reward) return this.positiveReward;
    	return this.negativeReward;
    }
    
    void setReward(double positive,double negative){
    	this.positiveReward=positive;
    	this.negativeReward=negative;
    }
		
//		i=0;
//		while(i++!=10){
//			if(!newCar.brake()){
//				System.out.println(" *** Negative Reward at iteration : "+i);
//				break;
//			}
//			newCar.getCarStatus();
//		}
  

    /*
    2/26/2015
    To do - Huzefa
    Collision
    Almost Done - Car Repeatable
    Default policy
    Status Object
     */
/*    public boolean isCollision(ArrayList<Car> allCars){
        //We check the distance between every 2 cars say Car I and Car J
        Iterator<Car> carIter1 = allCars.iterator();
        while(carIter1.hasNext()){
            Car carI = carIter1.next();
            Iterator<Car> carIter2 = allCars.iterator();
            while(carIter2.hasNext()){
                Car carJ = carIter2.next();

                if(carI == carJ){
                    continue;
                }

                double carIX = carI.getCarCoordinates().getX();
                double carIY = carI.getCarCoordinates().getY();
                double carJX = carJ.getCarCoordinates().getX();
                double carJY = carJ.getCarCoordinates().getY();

                if(carIX > carJX - 5 && carIX < carJX + 5){
                    //Will continue tomorrow, too tired.. Going to sleep :)
                    if (carIY)
                }
                else{
                    if
                }
            }
        }
        return false;
    }
*/
    //How will time be managed ?
}
