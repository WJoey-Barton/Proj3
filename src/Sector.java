import java.util.List;

public class Sector {
    
    //0-3
    private final int sectorID;
    private final List<TrackSegment> segmentList;
    
    public Sector(int ID, List<TrackSegment> list) {
        this.sectorID = ID;
        this.segmentList = list;
    }

    public int getSectorID() { return this.sectorID;}
    public List<TrackSegment> getSegments() { return this.segmentList;}

    //Length will come from the sum of the length of all TrackSegments in this Sector
    public double getSectorLength() {
        double length = 0;

        return length;
    }
}
