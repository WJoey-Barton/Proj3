public class Corner extends TrackSegment{

    public Corner(int segmentID, double startAngle, double endAngle) {
        super(segmentID, SegmentType.CORNER, startAngle, endAngle);
    }

    @Override
    public double calculateSpeed(Engine engine, Tire tire, Aero aero) {
        return aero.getRating();
    }
    
}
