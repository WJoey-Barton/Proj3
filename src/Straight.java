public class Straight extends TrackSegment{
    
    public Straight(int segmentID, double startAngle, double endAngle) {
        super(segmentID, SegmentType.STRAIGHT, startAngle, endAngle);
    }

    @Override
    public double calculateSpeed(Engine engine, Tire tire, Aero aero) {
        return engine.getRating();
    }
}
