public abstract class TrackSegment {

    public enum SegmentType { STRAIGHT, CORNER};

    protected double length;
    protected double difficultyCoefficient;

    protected final int segmentID;
    protected final SegmentType type;
    protected final int sectorIndex;

    protected TrackSegment(int segmentID, SegmentType type, int sectorIndex) {
        this.segmentID = segmentID;
        this.type = type;
        this.sectorIndex = sectorIndex;
    }

    public double getLength() { return this.length;}
    public double getDifficultyCoefficient() { return this.difficultyCoefficient;}
    public int getSegmentID() { return this.segmentID;}
    public SegmentType getType() { return this.type;}
    public int getSectorIndex() { return sectorIndex;}


}
