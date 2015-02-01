package Helper;

/**
 * Documentation : Will try to provide good enough and simple
 * explanation for what the class represents and what was the intuition behind it.
 * It will be easier for the one who maintains this code or wants to add features.
 *
 * GridPoint is the basic unit of a grid
 * Each GridPoint will have an X and Y coordinate in the "Grid"
 * This will ease passing of GridPoints as objects.
 * The coordinates are whole numbers and cannot be negative.
 *
 * Future Work :
 * Validation of non negative integers being passed.
 * Check if the user specified roads lead to mappable GridPoints
 *
 */
public class GridPoint {

    int x,y;
    public GridPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean setX(int newX) {
        x = newX;
        return true;
        //Return false if the above statement fails #Futurework
    }

    public boolean setY(int newY) {
        y = newY;
        return true;
        //REturn false if the above statement fails #Futurework
    }

}
