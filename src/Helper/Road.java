package Helper;

/*
  * The Road Object will be used by the user in the initialization step.
  * The user will create as many objects of the Road class as desired.
  * An Arraylist of all the Road objects will be passed to the VirtualMap constructor
  * which will initalize the LinkedList of road segments.
  *
  */
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
        if(beginPoint.getX() == endPoint.getX()){
            orientation = new String("NS"); // North-South
        }
        else{
            orientation = new String("EW"); // East - West
        }
    }

    public String getOrientation(){
        return orientation;
    }
    public GridPoint getBeginPoint(){
		return beginPoint;
	}
	public GridPoint getEndPoint(){
		return endPoint;
	}
	public String getRoadName(){
		return roadName;
	}
	public boolean setBeginPoint(GridPoint newBp){
		beginPoint = newBp;
		return true;
		//Return false if the above statement fails #Futurework
	}
	public boolean setEndPoint(GridPoint newEp){
		endPoint = newEp;
		return true;
		//Return false if the above statement fails #Futurework
	}
	public boolean setRoadName(String newName){
		roadName = newName;
		return true;
		//Return false if the above statement fails #Futurework_
	}

	/*
	 * I think we need to create the linked list of road segments here
	 * Lets see where this goes.
	 *
	 */
}
