package Helper;

//Cleaned
public class Coordinates {
//Helper class to transfer X and Y coordinates to and from objects

    double x,y;
    public Coordinates(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
}
