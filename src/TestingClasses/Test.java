package TestingClasses;

import java.text.DecimalFormat;

public class Test {
	
	public static void main(String args[]){
		double y=1.0/3600.0;
		double x= Math.round((1.0/3600.0)*10000)/10000;
		System.out.println("y :"+Math.round(((1.0/3600.0)*10000)));
		System.out.println("x :"+x);
		
		DecimalFormat df = new DecimalFormat("#.00000");
		double TIME=Double.parseDouble(df.format(1.0/3600.0));
		System.out.println(TIME);
	}

}
