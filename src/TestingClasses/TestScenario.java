package TestingClasses;

import MainPackage.Scenario;

public class TestScenario {

	public static void main(String args[]){
		String fileName="E:\\Spring 2015\\Indepenedent Study\\CarSimulator\\CarSimulator\\src\\InitialFiles\\Roads.csv";
		Scenario newScenario=new Scenario();
		newScenario.initializeScenario(1,fileName,"MG Road",1);
		newScenario.startScenario();
	}
	
}
