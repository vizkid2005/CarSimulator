package Initializers;
import java.io.*;
import java.util.ArrayList;

import Helper.GridPoint;
import Helper.Road;

public class ReadRoadInput {
	int x1,y1,x2,y2;
	String roadName=null;
	ArrayList<Road> gridList=new ArrayList<Road>();
	
	public  ArrayList<Road> readRoadInput(String fileName){
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
					GridPoint startGrid=new GridPoint(x1,y1);
					GridPoint endGrid=new GridPoint(x2,y2);
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
	}
	
}
