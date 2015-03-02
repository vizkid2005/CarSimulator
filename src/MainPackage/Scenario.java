package MainPackage;

import BigMap.RoadMap;
import Helper.Car;
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
	Car newCar;                                   // Creating a Car
	boolean reward=true;
	int id;           //Uniquely identifies each scenario
	
	public Scenario(RoadMap map, ArrayList<Car> allCars, Policy defaultPolicy){

        id = (int) Math.random()*100000; //5 digit random number
    }

    public Scenario() {
		// TODO Auto-generated constructor stub
	}

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
    public void initializeScenario(int numberOfCars,String fileName,String roadName,int laneNumber){
    	
    	ArrayList<Road> roadList=r1.readRoadInput(fileName);    // and generating Road from it.
    	newMap=new RoadMap(roadList);
        //Even if using single car, try putting it in an ArrayList
        ArrayList<Car> allCars = new ArrayList<Car>();
    	/********* Creating a Car that can be controlled by user as per custom choice ************/
    	newCar=new Car();
    	allCars.add(newCar);
    	newCar.setCurrentLane(laneNumber);
    	
    	RoadSegment currentSegment=newMap.getRoadSegmentFromRoadName(roadName,laneNumber); 
    	     /*Finding Current segment for a Car*/
    	newCar.setCurrentSegment(currentSegment);
    	
    	String roadOrientation=newMap.getRoadOrientation(roadName,laneNumber); 
    	     /*Getting Road orientation for Road selected by user to put his car*/
		System.out.println("roadOrientation : "+roadOrientation);
		if(roadOrientation==null){
			System.out.println("Road Do not Exist. Please enter Correct Road");
			return;
		}
		
		String carDirection=newCar.getCarDirection(roadOrientation,laneNumber);  
			/*Calculation direction of a car.*/
		if(carDirection==null){
			System.out.println("Wrong Direction.");
			return;
		}
		newCar.setDirection(carDirection);
		newCar.setCarInitialPosition(currentSegment,laneNumber,carDirection);
		//newCar.setCarPosition(4.0,2.0);
    	
		/******** Initialization Done **********/
		newCar.getCarStatus();
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
    		
    		if(input.equals("accelerate")){
    			//System.out.println("Action : Accelerate");
    			if(continueScenario(newCar.accelerate())){
    				newCar.getCarStatus();
    			}
    			else{
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    				
    			}
    		}
    		else if(input.equals("brake")){
    			//System.out.println("Action : brake");
    			if(continueScenario(newCar.brake())){
    				newCar.getCarStatus();
    			}
    			else{
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else if(input.equals("doNothing")){
    			//System.out.println("Action : doNothing");
    			if(continueScenario(newCar.doNothing())){
    				newCar.getCarStatus();
    			}
    			else{
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else if(input.equals("moveRightLane")){
    			//System.out.println("Action : moveRightLane");
    			if(continueScenario(newCar.moveRightLane())){
    				newCar.getCarStatus();
    			}
    			else{
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else if(input.equals("moveLeftLane")){
    			//System.out.println("Action : moveLeftLane");
    			if(continueScenario(newCar.moveLeftLane())){
    				newCar.getCarStatus();
    			}
    			else{
    				System.out.println(" **** NEGATIVE REWARD **** ");
    				break;
    			}
    		}
    		else{
    			System.out.println(" Wrong Input Entered . Closing the simulator ");
    			break;
    		}
    		}
    		catch(Exception e){
    			System.out.println("Exception :"+e.getStackTrace());
    		}
    		
    	}while(true);

    }
    
    boolean continueScenario(boolean reward){
    	//newCar.getCarStatus();
    	return this.reward&reward;
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
    public boolean isCollision(ArrayList<Car> allCars){
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

    //How will time be managed ?
}
