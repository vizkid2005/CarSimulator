package TestingClasses;

import BigMap.RoadMap;
import Helper.GridPoint;
import Helper.Road;
import Helper.RoadSegment;
import Initializers.ReadRoadInput;

import java.util.ArrayList;

/**
 * Created by Huzefa on 2/7/15.
 */
public class TestIntersectionPoints {

    public static void main(String[] args) {

        ReadRoadInput r = new ReadRoadInput();
        //Change name to local filename
        ArrayList<Road> listOfRoads = r.readRoadInput("D:\\College - Spring 2015\\Independent Study\\CarSimulator\\src\\InitialFiles\\Roads.csv");
        /*Road road1 = new Road(new GridPoint(1,2),new GridPoint(1,5),"Road1");
        Road road5 = new Road(new GridPoint(1,3),new GridPoint(3,3),"Road5");
        Road road2 = new Road(new GridPoint(3,2),new GridPoint(3,6),"Road2");
        Road road3 = new Road(new GridPoint(3,2),new GridPoint(4,2),"Road3");
        Road road4 = new Road(new GridPoint(3,6),new GridPoint(4,6),"Road4");
        //Road road6 = new Road(new GridPoint(0,7),new GridPoint(5,7),"Road6");

        ArrayList<Road> listOfRoads = new ArrayList<Road>();
        listOfRoads.add(road1);
        listOfRoads.add(road2);
        listOfRoads.add(road3);
        listOfRoads.add(road4);
        listOfRoads.add(road5);
        //listOfRoads.add(road6);
        */
        RoadMap map = new RoadMap(listOfRoads);
        RoadSegment temp = map.getRoadSegmentFromRoadName("MG Road",1);
    }
}
