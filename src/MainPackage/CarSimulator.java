package MainPackage;

import BigMap.RoadMap;
import Helper.Car;
import Helper.Road;

import java.util.ArrayList;

import Helper.State;

/**
 * Use this class and this class only to run your learner.
 * Implement your learner as an object that will call the methods of Scenario.
 * Do not attempt to directly access the methods of various objects. Use them through the Scenario Object.
 * This is the main class which will be used by the user to create scenarios and handle each scenario
 * independently.
 *
 */
public class CarSimulator {

    public Car[] cars=new Car[10];
	
	public static void main(String[] args)throws Exception{
		String roadFile = new String();
        //Just this call should suffice creating the RoadMap, There are a lot of messed up function calls that have to be resolved.
         
        RoadMap map = new RoadMap(roadFile);
        
        

    }
    
    public State getCurrentState(){
		State currentState = new State(cars);
		return currentState;
	}
}
