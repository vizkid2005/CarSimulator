package BigMap;

import Helper.GridPoint;
import Helper.Road;
import Helper.RoadSegment;

import java.util.ArrayList;

/**
 * This class takes in an ArrayList of Road object and generates a map of RoadSegments.
 * Imagine this as a grid of RoadSegments.
 */
public class RoadMap {

    ArrayList<GridPoint> intersectionPoints;
    ArrayList<RoadSegment> RoadStartPointers;
    ArrayList<RoadSegment> RoadEndPointers;
    ArrayList<RoadSegment> IntersectionPointers;

    public RoadMap(ArrayList<Road> roads){
        checkDuplicates(roads);
        getIntersectionPoints(roads);
        printIntersectionPoints(roads);
        buildRoadMap(roads);
        printRoads();
    }

    public void printRoads(){
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
    public void buildRoadMap(ArrayList<Road> roads){

        RoadStartPointers = new ArrayList<RoadSegment>();
        RoadEndPointers = new ArrayList<RoadSegment>();
        IntersectionPointers = new ArrayList<RoadSegment>();

        for(int i =0;i<roads.size();i++){
            int beginX = roads.get(i).getBeginPoint().getX();
            int beginY = roads.get(i).getBeginPoint().getY();
            int endX = roads.get(i).getEndPoint().getX();
            int endY = roads.get(i).getEndPoint().getY();
            String roadOrientation = roads.get(i).getOrientation();

            RoadSegment beginRoad;

            //May break if the point where the road begins has an intersection
            if(intersectionPoints.contains(new GridPoint(beginX,beginY))) {
                beginRoad = new RoadSegment(new GridPoint(beginX, beginY), true);
            }
            else{
                beginRoad = new RoadSegment(new GridPoint(beginX, beginY), false);
            }

            RoadSegment current = beginRoad;

            //Handle intersection Points
            if(roadOrientation.equals("EW")) {
                while (beginX < endX) {
                    RoadSegment nextSegment;
                    if (intersectionPoints.contains(new GridPoint(beginX + 1, beginY))) {
                        nextSegment = new RoadSegment(new GridPoint((beginX + 1), beginY), true);
                        IntersectionPointers.add(nextSegment);
                    } else {
                        nextSegment = new RoadSegment(new GridPoint((beginX + 1), beginY), false);
                    }

                    current.setEastSegment(nextSegment);
                    nextSegment.setWestSegment(current);
                    beginX++;
                    current = nextSegment;
                }
                RoadStartPointers.add(beginRoad);
                RoadEndPointers.add(current);
            }
            else{
                while(beginY<endY){
                    RoadSegment nextSegment;
                    if(intersectionPoints.contains(new GridPoint(beginX, beginY+1))) {
                        nextSegment = new RoadSegment(new GridPoint(beginX, beginY+1), true);
                        IntersectionPointers.add(nextSegment);
                    } else {
                        nextSegment = new RoadSegment(new GridPoint(beginX, beginY+1), false);
                    }
                    current.setSouthSegment(nextSegment);
                    nextSegment.setNorthSegment(current);
                    beginY++;
                    current = nextSegment;
                }
                RoadStartPointers.add(beginRoad);
                RoadEndPointers.add(current);
            }
        }
    }

    //Done
    public void getIntersectionPoints(ArrayList<Road> roads){

        //Before this make sure, all road start points are valid
        //All beginpoints should be less than the end points
        //Write a method for that
        intersectionPoints = new ArrayList<GridPoint>();
        for(int i = 0;i<roads.size();i++){
            for(int j = i;j<roads.size();j++){
                if(roads.get(i) == roads.get(j)){
                    continue;
                }
                if(roads.get(i).getOrientation().equals(roads.get(j).getOrientation())){
                    continue;
                }
                int intersectionX,intersectionY;
                if(roads.get(i).getOrientation().equals("NS")){
                    intersectionX = roads.get(i).getBeginPoint().getX();
                    intersectionY = roads.get(j).getBeginPoint().getY();
                }
                else{
                    intersectionX = roads.get(j).getBeginPoint().getX();
                    intersectionY = roads.get(i).getBeginPoint().getY();
                }
                intersectionPoints.add(new GridPoint(intersectionX, intersectionY));
            }
        }
    }

    //Refine
    public void printIntersectionPoints(ArrayList<Road> roads){
        System.out.println("Roads : ");
        for(int i =0;i<roads.size();i++){
            System.out.println("BX : "+roads.get(i).getBeginPoint().getX()+ "  BY : "+roads.get(i).getBeginPoint().getY()
                              +"EX : "+roads.get(i).getEndPoint().getX()+ "  BY : "+roads.get(i).getEndPoint().getY()+" " +
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

    //Done
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
}


