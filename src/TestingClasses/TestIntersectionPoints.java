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

        Road road1 = new Road(new GridPoint(0,0),new GridPoint(6,0),"Road1");
        Road road5 = new Road(new GridPoint(0,0),new GridPoint(0,4),"Road5");
        Road road2 = new Road(new GridPoint(5,0),new GridPoint(5,8),"Road2");
        Road road3 = new Road(new GridPoint(3,4),new GridPoint(3,9),"Road3");
        Road road4 = new Road(new GridPoint(0,4),new GridPoint(3,4),"Road4");
        Road road6 = new Road(new GridPoint(0,7),new GridPoint(5,7),"Road6");

        ArrayList<Road> listOfRoads = new ArrayList<Road>();
        listOfRoads.add(road1);
        listOfRoads.add(road2);
        listOfRoads.add(road3);
        listOfRoads.add(road4);
        listOfRoads.add(road5);
        listOfRoads.add(road6);

        RoadMap map = new RoadMap(listOfRoads);
    }
}
