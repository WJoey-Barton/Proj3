//Joey Barton
/*
Inherits from the parent class, TrackSegment
Represents a curved section of the track.
Prioritizes Aerodynamic performance.
*/

public class Corner extends TrackSegment{

    public Corner(int segmentID, double startAngle, double endAngle) {
        super(segmentID, SegmentType.CORNER, startAngle, endAngle);
    }

    //Calculates the maximum possible speed for a car navigating this corner
    @Override
    public double calculateSpeed(Engine engine, Tire tire, Aero aero) {
        return aero.getRating();
    }
    
}
