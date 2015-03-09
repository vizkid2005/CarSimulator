package TestingClasses;

import java.util.ArrayList;

import Helper.GridPoint;
import Helper.Road;
import Initializers.ReadRoadInput;

public class TestReadInput {

	public static void main(String args[]){
		String fileName="E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\src\\InitialFiles\\Roads.csv";
		ReadRoadInput r1=new ReadRoadInput();
		ArrayList<Road> roadList=r1.readRoadInput(fileName);
		int i=0;
		
		for(Road g:roadList){
			System.out.println();
			print(g);
		}
	}
	public static void print(Road g){
		System.out.print("\t Start Point : "+g.getBeginPoint().getX());
		System.out.print("\t"+g.getBeginPoint().getY());
		System.out.print("\t End Point : "+g.getEndPoint().getX());
		System.out.print("\t"+g.getEndPoint().getY());
		System.out.print("\t Road Name : "+g.getRoadName());
		System.out.print("\t Orientation : "+g.getOrientation());
	}
}
