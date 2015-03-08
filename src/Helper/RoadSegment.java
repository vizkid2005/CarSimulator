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
    //1 mile = 5280 ft
    //CurrSpeed, RateOfAccleration and RateOfBraking mus be adjusted according to this.
    public final int length = 1; // Arbitrary length in feet, miles , etc. - Do not forget to intialize before running

    //Boilerplate constructor, the real work will be done by the get and set methods
    public RoadSegment(GridPoint gp, boolean isIntersection){
        northSegment = null;
        southSegment = null;
        eastSegment = null;
        westSegment = null;
        pointInGrid = gp;
        this.isIntersection = isIntersection;
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


}

