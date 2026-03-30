//Joey Barton

/*
Inherits from the parent class, TrackSegment
Represents a straight section of the track.
Prioritizes Engine performance.
*/

public class Straight extends TrackSegment{
    
    public Straight(int segmentID, double startAngle, double endAngle) {
        super(segmentID, SegmentType.STRAIGHT, startAngle, endAngle);
    }

    //Calculates the maximum possible speed for a car navigating this straight.
    @Override
    public double calculateSpeed(Engine engine, Tire tire, Aero aero) {
        return engine.getRating();
    }
}
