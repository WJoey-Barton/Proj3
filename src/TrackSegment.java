//Joey Barton

/*
Abstract class representing a generic section of the Track.
This class provides the logic for angular positioning and
enforces specific speed calculations for different track types
through polymorphism.
NOTE: This simulation uses a clockwise coordinate system.
*/

public abstract class TrackSegment {

    //Categorizes each part of the Track.
    public enum SegmentType { STRAIGHT, CORNER, TractionZone};

    protected double startAngle;
    protected double endAngle;

    protected double length;
    protected double difficultyCoefficient;

    protected final int segmentID;
    protected final SegmentType type;

    protected TrackSegment(int segmentID, SegmentType type, double startAngle, double endAngle) {
        this.segmentID = segmentID;
        this.type = type;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    //Checks if a given angular position falls within the boundaries of
    //this segment. This method correctly handles segments that cross the 
    // 0/2PI wrap-around point. 
    public boolean containsAngle(double angle) {

        //Normal case. Start is greater than end angle.
        if(startAngle > endAngle) {
            return angle <= startAngle && angle >= endAngle;

        //Wrap-around case. If a segment starts at 45 degrees and ends at 315 degrees,
        //the angle wraps from 0 to 2PI.
        } else {
            return angle >= endAngle || angle <= startAngle;
        }
    }

    //Abstract method to be implemented by specific track types.
    //Defines how vehicle components interact with this specific Track Segment.
    public abstract double calculateSpeed(Engine engine, Tire tire, Aero aero);

    public double getStartAngle() { return this.startAngle;}
    public double getEndAngle() { return this.endAngle;}

    public double getLength() { return this.length;}
    public double getDifficultyCoefficient() { return this.difficultyCoefficient;}
    public int getSegmentID() { return this.segmentID;}
    public SegmentType getType() { return this.type;}


}
