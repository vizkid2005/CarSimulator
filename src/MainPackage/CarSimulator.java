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
    static Scenario s;
	public static void main(String[] args)throws Exception{
		String roadFile = new String("D:\\College - Spring 2015\\Independent Study\\CarSimulator\\src\\InitialFiles\\RoadsSimple.csv");
        String carFile = new String("D:\\College - Spring 2015\\Independent Study\\CarSimulator\\src\\InitialFiles\\Cars.csv");
        map = new RoadMap(roadFile);
        ReadCarInput rci = new ReadCarInput();
        carList = rci.readCarInput(map, carFile);

        //Create a new Scenario object
        s = new Scenario();
        //Initialize the new Scenario object with the Desired RoadMap and CarList.
        s.initializeScenario(map,carList,"E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\output\\FOL.txt","E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\output\\logFile.txt");

        //Here is a demo policy that randomly takes any one of the actions accelerate,brake or doNothing.
        //Replace the code below with your learner.
        for(int i= 0;i<18;i++){
            double random = Math.random();
            if(random < 0.5){
                s.takeAction("accelerate",0);
            }
            else{
                s.takeAction("doNothing",0);
            }

        }
          /*for(int i = 0;i<5;i++){
                boolean success = s.takeAction("accelerate",0);
                if(!success){
                    System.out.println("Exiting");
                }
            }*/
        s.endScenario();
    }

    public State getCurrentState(){
		State currentState = new State(carList);
		return currentState;
	}
}
