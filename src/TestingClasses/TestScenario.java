package TestingClasses;

import MainPackage.Scenario;

public class TestScenario {

	public static void main(String args[]){
		int episode=0;
		String fileName="E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\src\\InitialFiles\\Roads.csv";
		String predicateFile="E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\output\\FOL.txt";
		Scenario newScenario=new Scenario();
		newScenario.setPredicatefile(predicateFile);
		newScenario.initializeScenario(1,fileName,"Waazalwar Circle",5);
		newScenario.startScenario();
	}
	
}
