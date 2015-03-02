package BigMap;

import Helper.GridPoint;
import Helper.Road;
import Helper.RoadSegment;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class takes in an ArrayList of Road object and generates a map of RoadSegments.
 * Imagine this as a grid of RoadSegments. (Refer to RoadSegment.java for details on Road Segments)
 * Look at the GridPoint & Road classes before diving into this
 */
public class RoadMap {

    ArrayList<GridPoint> intersectionPoints; //Keep track of intersection points as gridpoints
    ArrayList<RoadSegment> RoadStartPointers; //Keep track of all starting points of the roads
    ArrayList<RoadSegment> RoadEndPointers; //Keep track of all ending points of the roads
    ArrayList<RoadSegment> IntersectionPointers; //Keep track of all intersection points
    ArrayList<RoadSegment> AllRoadSegmentPointers; //Keep pointers for each and every road segment
    ArrayList<Road> listOfRoads; //Maintain a local copy of the roads

    //The constructor is responsible for building everything.
    public RoadMap(ArrayList<Road> roads){
        //The constructor calls all the necessary methods to initialize the Road datastructure.
        //The order of method calls is crucial. Do not change
        listOfRoads = roads;
        AllRoadSegmentPointers = new ArrayList<RoadSegment>();
        ArrayList<RoadSegment> allvisited = new ArrayList<RoadSegment>(); //Just for debugging, remove when not needed
        checkDuplicates(roads); //Check for duplicates first, System exits if any duplicates are found.
        getIntersectionPoints(roads); //Calculate intersection points of all roads.
        //printIntersectionPoints(roads); //Debug method to check if all roads have appropriate intersection points
        buildRoadMap(roads); //The core method where all the magic happens
        System.out.println();
        recursivePrint(RoadStartPointers.get(0),2,allvisited); //Inorder printing of the roads
    }

    public RoadSegment getNextSegment(RoadSegment currSegment,String action ){
        return null;
    }

    //This method should be removed when everything is working
    public void printRoads(){
        System.out.println("pointers length : "+ IntersectionPointers.size());
        for(int i=0;i<RoadStartPointers.size();i++){
            System.out.println();
            RoadSegment start = RoadStartPointers.get(i);
            if(start.getEastSegment() != null) {
                while (start != null) {
                    System.out.print("(" + start.getPointInGrid().getX() + ", " + start.getPointInGrid().getY() + " ) --> ");
                    start = start.getEastSegment();
                }
            }
            else if(start.getSouthSegment() != null){
                while (start != null) {
                    System.out.print("(" + start.getPointInGrid().getX() + ", " + start.getPointInGrid().getY() + " ) --> ");
                    start = start.getSouthSegment();
                }
            }
        }
        System.out.println();
        for(int i=0;i<IntersectionPointers.size();i++){
            System.out.println(IntersectionPointers.get(i).getPointInGrid().getX()+","+IntersectionPointers.get(i).getPointInGrid().getY());
        }
    }

    //The most important method that creates a 4-way linked list of all road segments
    public void buildRoadMap (ArrayList<Road> roads){

        RoadStartPointers = new ArrayList<RoadSegment>();
        RoadEndPointers = new ArrayList<RoadSegment>();
        IntersectionPointers = new ArrayList<RoadSegment>();

        //For each road
        for (int i =0;i<roads.size();i++){
            int beginX = roads.get(i).getBeginPoint().getX();
            int beginY = roads.get(i).getBeginPoint().getY();
            int endX = roads.get(i).getEndPoint().getX();
            int endY = roads.get(i).getEndPoint().getY();
            String roadOrientation = roads.get(i).getOrientation();

            RoadSegment beginRoad; //First segment of the road
            RoadSegment current;

            //EW and NS roads are handled differently
            if(roadOrientation.equals("EW")) {

                //Is the beginning point an intersection ?
                if(this.isIntersectionPoint(intersectionPoints, new GridPoint(beginX, beginY))) {

                    //Has this intersection point been visited before ?
                    if (this.isIntersectionAlreadyVisited(new GridPoint(beginX, beginY)) != null) {
                        //If yes, we need to modify the existing RoadSegment.
                        RoadSegment temp = this.isIntersectionAlreadyVisited(new GridPoint(beginX, beginY));
                        beginRoad = temp;
                        current = beginRoad;
                    } else {

                        //Else create a new RoadSegment and add it to the IntersectionPointers for later reference, like the above case
                        beginRoad = new RoadSegment(new GridPoint(beginX, beginY), true);
                        IntersectionPointers.add(beginRoad);
                        current = beginRoad;
                    }
                }
                else {
                    //Nothing to be done if the beginning point is not an intersection
                    beginRoad = new RoadSegment(new GridPoint(beginX, beginY), false);
                    current = beginRoad;
                }

                //Create & append new segments to the road
                while (beginY < endY) {
                    RoadSegment nextSegment;

                    //If the next segment is an intersection
                    if (this.isIntersectionPoint(intersectionPoints,new GridPoint(beginX, beginY+1))) {
                        //If the intersection has been already created by another road, then append to the existing segment
                        if(isIntersectionAlreadyVisited(new GridPoint((beginX), beginY+1)) != null){
                            RoadSegment revisit = isIntersectionAlreadyVisited(new GridPoint((beginX), beginY+1));
                            current.setEastSegment(revisit);
                            revisit.setWestSegment(current);
                            beginY++;
                            current = revisit;
                            continue;
                        }
                        else{
                            //Else create a new Intersection segment and add to IntersectionPointers for future reference
                            nextSegment = new RoadSegment(new GridPoint((beginX), beginY+1), true);
                            IntersectionPointers.add(nextSegment);
                        }

                    }
                    else {
                        //No issues, if it is not an intersection
                        nextSegment = new RoadSegment(new GridPoint((beginX), beginY+1), false);
                    }
                    current.setEastSegment(nextSegment); //Set the next segment as an East Segment
                    nextSegment.setWestSegment(current); //Set the next segments West as the current segment
                    beginY++; //Increment counter
                    current = nextSegment;
                }

                RoadStartPointers.add(beginRoad);
                RoadEndPointers.add(current);
            }
            //If orientation is North-South
            else{
                if(this.isIntersectionPoint(intersectionPoints, new GridPoint(beginX, beginY))) {
                    if (this.isIntersectionAlreadyVisited(new GridPoint(beginX, beginY)) != null) {
                        RoadSegment temp = this.isIntersectionAlreadyVisited(new GridPoint(beginX, beginY));
                        beginRoad = temp;
                        current = beginRoad;
                    } else {
                        beginRoad = new RoadSegment(new GridPoint(beginX, beginY), true);
                        IntersectionPointers.add(beginRoad);
                        current = beginRoad;
                    }
                }
                else {
                    beginRoad = new RoadSegment(new GridPoint(beginX, beginY), false);
                    current = beginRoad;
                }

                while(beginX<endX){
                    RoadSegment nextSegment;
                    if(this.isIntersectionPoint(intersectionPoints,new GridPoint(beginX+1, beginY))) {
                        if(isIntersectionAlreadyVisited(new GridPoint((beginX+1), beginY)) != null){
                            RoadSegment revisit = isIntersectionAlreadyVisited(new GridPoint((beginX+1), beginY));
                            current.setSouthSegment(revisit);
                            revisit.setNorthSegment(current);
                            beginX++;
                            current = revisit;
                            continue;
                        }
                        else{
                            nextSegment = new RoadSegment(new GridPoint((beginX+1), beginY), true);
                            IntersectionPointers.add(nextSegment);
                        }
                    }
                    else {
                        nextSegment = new RoadSegment(new GridPoint(beginX+1, beginY), false);
                    }
                    current.setSouthSegment(nextSegment);
                    nextSegment.setNorthSegment(current);
                    beginX++;
                    current = nextSegment;
                }
                //If 2 roads end in the same segment, the same pointer is added 2wice, may break.
                RoadStartPointers.add(beginRoad);
                RoadEndPointers.add(current);
            }
        }
    }
    //Done
    public void getIntersectionPoints(ArrayList<Road> roads){

        //Before this make sure, all road start points are valid
        //All begin points should be less than the end points
        //Write a method for that
        intersectionPoints = new ArrayList<GridPoint>();
        for(int i = 0;i<roads.size();i++){
            for(int j = i;j<roads.size();j++){

                Road r1 = roads.get(i);
                Road r2 = roads.get(j);

                //A Road cannot intersect itself
                if (r1 == r2){
                    continue;
                }
                //Parallel roads do not intersect
                if (r1.getOrientation().equals(r2.getOrientation())){
                    continue;
                }

                int intersectionX,intersectionY;

                //A bit complicated but will make sense if you understand the conditions satisfied if 2 line segments intersect.
                //Using only their beginning and end points
                //It may take some time to understand but doesn't need to be understood for using this simulator
                if (r1.getOrientation().equals("NS")){
                   if (r1.getBeginPoint().getY() >= r2.getBeginPoint().getY()
                           && r1.getBeginPoint().getY() <= r2.getEndPoint().getY()
                               && r1.getBeginPoint().getX() <= r2.getBeginPoint().getX()
                                   && r1.getEndPoint().getX() >= r2.getBeginPoint().getX()){
                       intersectionX = r2.getBeginPoint().getX();
                       intersectionY = r1.getBeginPoint().getY();
                   }
                    else{
                       continue;
                   }
                }
                else {
                    if(r2.getBeginPoint().getX() <= r1.getBeginPoint().getX()
                            && r2.getEndPoint().getX() >= r1.getBeginPoint().getX()
                                && r2.getBeginPoint().getY() >= r1.getBeginPoint().getY()
                                    && r2.getBeginPoint().getY() <= r1.getEndPoint().getY()){
                        intersectionX = r1.getBeginPoint().getX();
                        intersectionY = r2.getBeginPoint().getY();
                    }
                    else{
                        continue;
                    }
                }

                intersectionPoints.add(new GridPoint(intersectionX, intersectionY));
            }
        }
    }
    //Printing all the intersection points for all the roads
    public void printIntersectionPoints(ArrayList<Road> roads){
        System.out.println("Roads : ");
        for(int i =0;i<roads.size();i++){
            System.out.println("BX : "+roads.get(i).getBeginPoint().getX()+ "  BY : "+roads.get(i).getBeginPoint().getY()
                              +"  EX : "+roads.get(i).getEndPoint().getX()+ "  BY : "+roads.get(i).getEndPoint().getY()+" " +
                    "Orientation : "+roads.get(i).getOrientation());
        }
        System.out.println("Intersection Points of the given roads.");
        if (intersectionPoints.isEmpty()) {
            System.out.println("Sad Map ");
            return;
        }
        for(int i =0;i<intersectionPoints.size();i++){
            System.out.println("X : "+intersectionPoints.get(i).getX()+ " Y : "+intersectionPoints.get(i).getY());
        }
    }
    //Check for duplicates
    public void checkDuplicates(ArrayList<Road> roads){
        for(int i = 0;i< roads.size();i++) {
            for (int j = i; j < roads.size(); j++) {
                if(i == j ){
                    continue;
                }
                if(roads.get(i) == roads.get(j)){
                    System.out.println("Found duplicates ");
                    System.exit(0);
                }
            }
        }
    }
    //Check if a gridpoint is present in the intersectionPoints arraylist
    public boolean isIntersectionPoint(ArrayList<GridPoint> intersectionPoints, GridPoint toCompare){
        int j;
        for(j = 0;j<intersectionPoints.size();j++){
            GridPoint temp = intersectionPoints.get(j);
            if(temp.getX() == toCompare.getX() && temp.getY() == toCompare.getY()){
                return true;
            }
        }
        return false;
    }
    //This method checks if the Intersection point has already been visited
    public RoadSegment isIntersectionAlreadyVisited(GridPoint visited){
        int j;
        for(j=0;j<IntersectionPointers.size();j++){
            RoadSegment temp = IntersectionPointers.get(j);
            GridPoint gp = temp.getPointInGrid();
            if(gp.getX() == visited.getX() && gp.getY() == visited.getY()){
                return temp;
            }
        }
        return null;
    }
    public void recursivePrint(RoadSegment startPoint, int direction, ArrayList<RoadSegment> allvisited) {
        if (startPoint == null || allvisited.contains(startPoint)) {
            return;
        }
        allvisited.add(startPoint);
        AllRoadSegmentPointers.add(startPoint);
        System.out.print("X: " + startPoint.getPointInGrid().getX() + " Y: " + startPoint.getPointInGrid().getY()+" -> ");
        if (direction == 1) {
            recursivePrint(startPoint.getNorthSegment(), 1, allvisited);
            recursivePrint(startPoint.getEastSegment(), 2, allvisited);
            recursivePrint(startPoint.getWestSegment(), 4, allvisited);
        } else if (direction == 2) {
            recursivePrint(startPoint.getNorthSegment(), 1, allvisited);
            recursivePrint(startPoint.getEastSegment(), 2, allvisited);
            recursivePrint(startPoint.getSouthSegment(), 3, allvisited);

        } else if (direction == 3) {
            recursivePrint(startPoint.getEastSegment(), 2, allvisited);
            recursivePrint(startPoint.getSouthSegment(), 3, allvisited);
            recursivePrint(startPoint.getWestSegment(), 4, allvisited);
        } else {
            recursivePrint(startPoint.getNorthSegment(), 1, allvisited);
            recursivePrint(startPoint.getSouthSegment(), 3, allvisited);
            recursivePrint(startPoint.getWestSegment(), 4, allvisited);
        }
    }
    public String getRoadOrientation(String roadName,int laneNumber){
    	
    	for(int i=0;i<listOfRoads.size();i++){
            if(listOfRoads.get(i).getRoadName().equals(roadName)){
               return listOfRoads.get(i).getOrientation(); 
            }
        }
    	return null;
    }
    public RoadSegment getRoadSegmentFromRoadName(String roadName, int laneNumber){
        GridPoint beginPoint = null;
        GridPoint endPoint = null;
        System.out.println("listOfRoads.size() :"+listOfRoads.size());
        for(int i=0;i<listOfRoads.size();i++){
            if(listOfRoads.get(i).getRoadName().equals(roadName)){
                beginPoint = listOfRoads.get(i).getBeginPoint();
                endPoint = listOfRoads.get(i).getEndPoint();
            }
        }
        //Some validation, the sizes of RoadStartPointers and RoadEndPointers should be same
        if(RoadStartPointers.size() != RoadEndPointers.size()){
            System.out.println("Problem with number of pointers for roads");
            System.exit(0);
        }
        for(int i =0;i<RoadStartPointers.size();i++){
            RoadSegment startTemp = RoadStartPointers.get(i);
            RoadSegment endTemp = RoadEndPointers.get(i);
            if(startTemp.getPointInGrid().getX() == beginPoint.getX()
                    && startTemp.getPointInGrid().getY() == beginPoint.getY()
                        && endTemp.getPointInGrid().getX() == endPoint.getX()
                            && endTemp.getPointInGrid().getY() == endPoint.getY() ){
                if(laneNumber > 3){
                    return endTemp;
                }
                else{
                    return startTemp;
                }
            }
        }
        return null;
    }
    //Method to retrieve RoadSegment from Coordinate
    public RoadSegment getRoadSegmentFromXY(double x, double y){
        int localX = (int) x/RoadSegment.length;
        int localY = (int) y/RoadSegment.length;

        //Using Iterator instead of for loop
        Iterator<RoadSegment> iter = AllRoadSegmentPointers.iterator();
        while(iter.hasNext()){
            RoadSegment rs = iter.next();
            int checkX = rs.getPointInGrid().getX();
            int checkY = rs.getPointInGrid().getY();
            //Return null if the desired RoadSegment is an intersection
            if(checkX == x && checkY == y && rs.isIntersection() == false){
                return rs;
            }
        }
        return null;
    }
}


