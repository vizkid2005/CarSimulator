package Helper;

import java.util.ArrayList;
/*
This class contains nothing but the ArrayList of all the cars so that all the related state information can be extracted as desired.
 */
public class State {
	public ArrayList<Car> cars; //any state of the driving simulator
	public State(ArrayList<Car> state){
		this.cars = state;
	}
	
}
