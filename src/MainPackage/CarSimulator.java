package MainPackage;

import BigMap.RoadMap;
import Helper.Car;
import Helper.Road;

import java.util.ArrayList;

import Helper.State;
import Initializers.ReadCarInput;

/**
 * Use this class and this class only to run your learner.
 * Implement your learner as an object that will call the methods of Scenario.
 * Do not attempt to directly access the methods of various objects. Use them through the Scenario Object.
 * This is the main class which will be used by the user to create scenarios and handle each scenario
 * independently.
 *
 */
public class CarSimulator {

    static RoadMap map;
    static ArrayList<Car> carList;
	public static void main(String[] args)throws Exception{
		String roadFile = new String("D:\\College - Spring 2015\\Independent Study\\CarSimulator\\src\\InitialFiles\\Roads.csv");
        String carFile = new String("D:\\College - Spring 2015\\Independent Study\\CarSimulator\\src\\InitialFiles\\Cars.txt");
        //Just this call should suffice creating the RoadMap, There are a lot of messed up function calls that have to be resolved.
        map = new RoadMap(roadFile);
        ReadCarInput rci = new ReadCarInput();
        carList = rci.readCarInput(map, carFile);
        Scenario s = new Scenario();


    }
	
	
    
    public State getCurrentState(){
		State currentState = new State(carList);
		return currentState;
	}
}
