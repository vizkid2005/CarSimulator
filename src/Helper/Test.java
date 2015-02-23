package Helper;

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
		newCar.getCarCoordinate();
		//newCar.moveRight();
		newCar.getCarCoordinate();
		//newCar.moveStraight();
		newCar.getCarCoordinate();
		//newCar.stop();
		newCar.getCarCoordinate();
		
		newCar.getCarStatus();
	}
	
}
