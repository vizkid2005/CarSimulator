package BigMap;

import Helper.GridPoint;
import Helper.Road;

import java.util.ArrayList;

/**
 * This class takes in an ArrayList of Road object and generates a map of RoadSegments.
 * Imagine this as a grid of RoadSegments.
 */
public class RoadMap {

    ArrayList<GridPoint> intersectionPoints;

    public RoadMap(ArrayList<Road> roads){
        this.getIntersectionPoints(roads);
        printIntersectionPoints(roads);
    }

    public void getIntersectionPoints(ArrayList<Road> roads){

        intersectionPoints = new ArrayList<GridPoint>();

        for(int i = 0;i< roads.size();i++){
            for(int j = i;j<roads.size();j++){
                if(roads.get(i) == roads.get(j)){
                    continue;
                }
                if(roads.get(i).getOrientation() == roads.get(j).getOrientation()){
                    continue;
                }
                int intersectionX,intersectionY;
                if(roads.get(i).getOrientation() == "NS"){
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

    public void checkDuplicates(ArrayList<Road> roads){
        //Write a function to check if there are duplicates in the ArrayList of roads.
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


