public abstract class TrackSegment {

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

    public boolean containsAngle(double angle) {
        if(startAngle > endAngle) {
            return angle <= startAngle && angle >= endAngle;
        } else {
            return angle >= endAngle || angle <= startAngle;
        }
    }

    public abstract double calculateSpeed(Engine engine, Tire tire, Aero aero);

    public double getStartAngle() { return this.startAngle;}
    public double getEndAngle() { return this.endAngle;}

    public double getLength() { return this.length;}
    public double getDifficultyCoefficient() { return this.difficultyCoefficient;}
    public int getSegmentID() { return this.segmentID;}
    public SegmentType getType() { return this.type;}


}
