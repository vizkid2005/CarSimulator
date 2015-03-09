package Helper;

/*
  * The Road Object will be used by the user in the initialization step.
  * The user will create as many objects of the Road class as desired.
  * An Arraylist of all the Road objects will be passed to the VirtualMap constructor
  * which will initalize the LinkedList of road segments.
  */
//Cleaned
public class Road {

	GridPoint beginPoint;
	GridPoint endPoint;
	String roadName;
    String orientation;

	public Road(GridPoint bp, GridPoint ep, String name){
		beginPoint = bp;
		endPoint = ep;
		roadName = name;
        setOrientation();
	}
	public void setOrientation(){
        if(beginPoint.getY() == endPoint.getY()){
            orientation = new String("NS"); // North-South
        }
        else{
            orientation = new String("EW"); // East - West
        }
    }
    public String getOrientation(){
        return orientation;
    }
	public boolean setBeginPoint(GridPoint newBp){
		beginPoint = newBp;
		return true;
		//Return false if the above statement fails #Futurework
	}
    public GridPoint getBeginPoint(){
		return beginPoint;
	}
	public boolean setEndPoint(GridPoint newEp){
		endPoint = newEp;
		return true;
		//Return false if the above statement fails #Futurework
	}
	public GridPoint getEndPoint(){
		return endPoint;
	}
	public boolean setRoadName(String newName){
		roadName = newName;
		return true;
	}
	public String getRoadName(){
		return roadName;
	}
}
