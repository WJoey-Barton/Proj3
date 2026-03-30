//Joey Barton

/*
Inherits from the parent class, TrackSegment
Represents a accelerating or braking zone on the track.
Prioritizes Tire performance.
*/

public class TractionZone extends TrackSegment{

    public TractionZone(int segmentID, double startAngle, double endAngle) {
        super(segmentID, SegmentType.TractionZone, startAngle, endAngle);
    }

    //Calculates the maximum possible speed for a car navigating this Traction Zone.
    @Override
    public double calculateSpeed(Engine engine, Tire tire, Aero aero) {
        return tire.getRating();
    }
    
}
