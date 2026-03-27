
public class Sector {
    
    //0-3
    //0 = A->B | 1 = B->C | 2 = C->D | 3 = D->A
    private final int sectorID;
    private final String name;
    private final double startAngle;
    private final double endAngle;


    //We'll save the TrackSegments for potential upgrades later
    //private final List<TrackSegment> segmentList;
    
    public Sector(int ID, String name, double startAngle, double endAngle) {
        this.sectorID = ID;
        this.name = name;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        //this.segmentList = list;
    }

    public int getSectorID() { return this.sectorID;}
    public String getName() { return this.name;}
    public double getStartAngle() { return this.startAngle;}
    public double getEndAngle() { return this.endAngle;}
    //public List<TrackSegment> getSegments() { return this.segmentList;}

    //Length will come from the sum of the length of all TrackSegments in this Sector
    public double getSectorLength() {
        double length = 0;

        return length;
    }
}
