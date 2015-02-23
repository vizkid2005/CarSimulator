package Helper;

/**
 * Just like the GridPoint on a grid, the RoadSegment is the basic unit of a Road.
 * A Road consists of the multiple RoadSegments at the implementation level.
 * Each road is implemented as a linked list of RoadSegments.
 * #Write more.
 */
public class RoadSegment {

    RoadSegment northSegment;
    RoadSegment southSegment;
    RoadSegment eastSegment;
    RoadSegment westSegment;
    GridPoint pointInGrid;
    boolean isIntersection; //A car can change direction only if it is at an intersection
    int width; //Will be removed
    int height; // Will be removed
    int length; // Arbitrary length - Do not forget to intialize before running

    //Boilerplate constructor, the real work will be done by the get and set methods
    public RoadSegment(GridPoint gp, boolean isIntersection){
        northSegment = null;
        southSegment = null;
        eastSegment = null;
        westSegment = null;
        pointInGrid = gp;
        this.isIntersection = isIntersection;
        //Each lane is 12 feet wide and each road as 6 lanes, hence width of 72. Source: Google
        width = 72;
        //Height is same as width to make the grid block square.
        height = 72;
    }

    public RoadSegment getNorthSegment() {
        return northSegment;
    }
    public void setNorthSegment(RoadSegment northSegment) {
        this.northSegment = northSegment;
    }

    public RoadSegment getSouthSegment() {
        return southSegment;
    }
    public void setSouthSegment(RoadSegment southSegment) {
        this.southSegment = southSegment;
    }

    public RoadSegment getEastSegment() {
        return eastSegment;
    }
    public void setEastSegment(RoadSegment eastSegment) {
        this.eastSegment = eastSegment;
    }

    public RoadSegment getWestSegment() {
        return westSegment;
    }
    public void setWestSegment(RoadSegment westSegment) {
        this.westSegment = westSegment;
    }

    public GridPoint getPointInGrid() {
        return pointInGrid;
    }
    public void setPointInGrid(GridPoint pointInGrid) {
        this.pointInGrid = pointInGrid;
    }

    public boolean isIntersection() {
        return isIntersection;
    }
    public void setIsIntersection(boolean isIntersection) {
        this.isIntersection = isIntersection;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}

