public class TractionZone extends TrackSegment{

    public TractionZone(int segmentID, double startAngle, double endAngle) {
        super(segmentID, SegmentType.TractionZone, startAngle, endAngle);
    }

    @Override
    public double calculateSpeed(Engine engine, Tire tire, Aero aero) {
        return tire.getRating();
    }
    
}
