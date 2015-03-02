package Initializers;
import java.io.*;
import java.util.ArrayList;
import Helper.Car;

public class ReadCarInput {
    ArrayList<Car> carList=new ArrayList<Car>();

    public  ArrayList<Car> readCarInput(String fileName){
        String line=null;
        try{
            FileReader reader=new FileReader(fileName);
            BufferedReader buf=new BufferedReader(reader);

            while((line=buf.readLine())!=null){
                if(line.contains("#")){
                    continue;
                }
                String[] result=line.split(",");
                x1=Integer.parseInt(result[0]);
                y1=Integer.parseInt(result[1]);
                x2=Integer.parseInt(result[2]);
                y2=Integer.parseInt(result[3]);
                roadName=result[4];
                Road newRoad= new Road(startGrid,endGrid,roadName);
                gridList.add(newRoad);
            }
        }

        catch(FileNotFoundException e){
            System.out.println("File Not Found !!!");
        }
        catch(IOException e){
            System.out.println("Error in reading file : ");
            e.printStackTrace();
        }

        return gridList;

