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
    	String fileName="E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\src\\InitialFiles\\Roads_1.csv";
		
    	ReadRoadInput r1=new ReadRoadInput();
		ArrayList<Road> roadList=r1.readRoadInput(fileName);
		
		RoadMap newMap=new RoadMap(roadList);
		RoadSegment currentSegment=newMap.getRoadSegmentFromRoadName("MG Road",1);
		
		System.out.println("Current Segment :"+currentSegment.getPointInGrid().getX()+currentSegment.getPointInGrid().getY());
		
		Car newCar=new Car();
		newCar.getCarStatus();
	}
    
    //How will time be managed ?
}
