package Helper;

//Should be removed
public class Test {

	public static void main(String args[]){
		Car newCar=new Car();
		
		newCar.getCarStatus();
		
		//newCar.setCarPosition(2.0,0.5);
		newCar.setCarColor("BLACK");
		newCar.setType("POLICE");
		newCar.setDirection("NORTH");
		newCar.setCurrentLane(3);
		newCar.setIsControlled(true);
		
		newCar.getCarStatus();
		
		//newCar.moveLeft();
		newCar.getCarCoordinates();
		//newCar.moveRight();
		newCar.getCarCoordinates();
		//newCar.moveStraight();
		newCar.getCarCoordinates();
		//newCar.stop();
		newCar.getCarCoordinates();
		
		newCar.getCarStatus();
	}
	
}
