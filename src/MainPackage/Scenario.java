package MainPackage;

import BigMap.RoadMap;
import Helper.Car;
import Helper.Policy;
import Helper.Road;
import Helper.RoadSegment;
import Initializers.ReadRoadInput;

import java.util.ArrayList;

/**
 * This will be the object that will emulate time, distance and make everything work.
 *
 */
public class Scenario {

    public Scenario(RoadMap map, ArrayList<Car> allCars, Policy defaultPolicy){

    }

    public Scenario() {
		// TODO Auto-generated constructor stub
	}

	//Gives the status of the entire environment in some form that is understandable
    public void showStatus(){

        //Throw a Status object
        Status s = new Status();
    }

    public void startScenario(){
    	String fileName="E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\src\\InitialFiles\\Roads.csv";
		
    	ReadRoadInput r1=new ReadRoadInput();                   // Reading coordinated from inputFile
		ArrayList<Road> roadList=r1.readRoadInput(fileName);    // and generating Road from it.
		
		RoadMap newMap=new RoadMap(roadList);                   // Creating RoadMap from Roads generated.
		
		Car newCar=new Car();                                   // Creating a Car
		
		newCar.setCurrentLane(2);
		newCar.getCarStatus();
		
		RoadSegment currentSegment=newMap.getRoadSegmentFromRoadName("MG Road",2); // Finding Current segment for a Car
		System.out.println("Current Segment -  X : "+currentSegment.getPointInGrid().getX()+"\t Y : "+currentSegment.getPointInGrid().getY());
		newCar.setCurrentSegment(currentSegment);
		
		String roadOrientation=newMap.getRoadOrientation("MG Road",2); // Getting Road orientation for Road selected by user to put his car
		System.out.println("roadOrientation : "+roadOrientation);
		if(roadOrientation==null){
			System.out.println("Road Do not Exist. Please enter Correct Road");
			return;
		}
		
		String carDirection=newCar.getCarDirection(roadOrientation,2);  // Calculation direction of a car.
		if(carDirection==null){
			System.out.println("Wrong Direction.");
			return;
		}
		
		newCar.setDirection(carDirection);
		newCar.setCarPosition(0,0);
		newCar.getCarCoordinate();
		
		int i=0;
		while(i++!=35){
			if(!newCar.accelerate()){
				System.out.println(" *** Negative Reward at iteration : "+i);
				break;
			} 
			newCar.getCarStatus();
			//newCar.getCarCoordinate();
			//newCar.getCurrentSpeed();
			//newCar.getCurrentLane();
		}
		
		//newCar.brake();
		//newCar.accelerate();
		//newCar.getCarCoordinate();
		//newCar.getCurrentSpeed();
	}
    
     
    //How will time be managed ?
}
