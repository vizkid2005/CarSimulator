package TestingClasses;

import BigMap.RoadMap;
import Helper.GridPoint;
import Helper.Road;

import java.util.ArrayList;

/**
 * Created by Huzefa on 2/7/15.
 */
public class TestIntersectionPoints {

    public static void main(String[] args) {

        Road road1 = new Road(new GridPoint(2,1),new GridPoint(2,4),"Road1");
        Road road2 = new Road(new GridPoint(4,1),new GridPoint(4,4),"Road2");
        Road road3 = new Road(new GridPoint(1,2),new GridPoint(6,2),"Road3");
        Road road4 = new Road(new GridPoint(1,4),new GridPoint(6,4),"Road4");
        ArrayList<Road> listOfRoads = new ArrayList<Road>();
        listOfRoads.add(road1);
        listOfRoads.add(road2);
        listOfRoads.add(road3);
        listOfRoads.add(road4);

        RoadMap map = new RoadMap(listOfRoads);
    }
}
