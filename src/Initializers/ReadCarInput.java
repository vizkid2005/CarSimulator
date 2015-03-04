package Initializers;
import java.io.*;
import java.util.ArrayList;

import BigMap.RoadMap;
import Helper.Car;
/*
This class cannot be used unless a RoadMap is Initialized

 */
public class ReadCarInput {
    ArrayList<Car> carList = new ArrayList<Car>();

    public ArrayList<Car> readCarInput(RoadMap map, String fileName) {
        String line = null;
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader buf = new BufferedReader(reader);

            while ((line = buf.readLine()) != null) {
                if (line.contains("#")) {
                    continue;
                }
                String[] result = line.split(",");
                String color = result[0];
                String type = result[1];
                double currSpeed = Double.parseDouble(result[2]);
                double rateOfAccl = Double.parseDouble(result[3]);
                double rateOfBraking = Double.parseDouble(result[4]);
                double maxSpeed = Double.parseDouble(result[5]);
                int intialLane = Integer.parseInt(result[6]);
                String intialRoad = result[7];
                boolean isLooping = Boolean.parseBoolean(result[8]);
                boolean isControlled = Boolean.parseBoolean(result[9]);
                Car newCar = new Car(map, color, type,
                        currSpeed, rateOfAccl,
                        rateOfBraking, maxSpeed,
                        intialLane, intialRoad,
                        isLooping, isControlled,carList.size());
                carList.add(newCar);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found !!!");
        } catch (IOException e) {
            System.out.println("Error in reading file : ");
            e.printStackTrace();
        }

        for(int i =0;i<carList.size();i++){
            System.out.println();
            System.out.println(carList.get(i).getColor());
            System.out.println(carList.get(i).getType());
            System.out.println(carList.get(i).getCurrSpeed());
            System.out.println(carList.get(i).getRateOfAccl());
            System.out.println(carList.get(i).getRateOfBraking());
            System.out.println(carList.get(i).getMaxSpeed());
            System.out.println(carList.get(i).getCurrentLane());
            System.out.println(carList.get(i).getCurrentSegment());
            System.out.println(carList.get(i).getNextSegment());
            System.out.println(carList.get(i).getDirection());
            System.out.println(carList.get(i).getCarCoordinates().getX()+ " -- "+ carList.get(i).getCarCoordinates().getY());

        }

        return carList;

    }
}