import java.util.ArrayList;
import java.util.List;

public class Track {

    /*
    Track size, CX is the center x, and CY is the center y.
    This is to know where to draw the oval. Then RX is the radius
    along the x axis and RY is the radius along the Y. This is to know
    how far to stretch the circle along each axis.
     */
    static final double CX = 320, CY = 240;
    static final double RX = 200, RY = 120;

    private final String trackName;
    private final List<Sector> sectorList;
    private final List<TrackSegment> allSegments;

    public Track(String name, List<Sector> sectors) {
        this.trackName = name;
        this.sectorList = sectors;
        allSegments = new ArrayList<>();
        buildSegments();
    }

    private void buildSegments() {
        allSegments.add(new Straight(1, ((23 * Math.PI ) / 36), ((13 * Math.PI) / 36))); //Front Straight 115 degrees to 65
        allSegments.add(new Straight(2, ((59 * Math.PI ) / 36), ((49 * Math.PI) / 36))); // Back Straight 295 degrees to 245
        allSegments.add(new Corner(3, ((5 * Math.PI ) / 4), ((3 * Math.PI) / 4))); // Left Corner 225 degrees to 135
        allSegments.add(new Corner(4, (( Math.PI ) / 4), ((7 * Math.PI) / 4))); //Right Corner 45 degrees to 315
        allSegments.add(new TractionZone(5, ((3 * Math.PI ) / 4), ((23 * Math.PI) / 36))); //Left Corner to Front Straight 135 degrees to 115
        allSegments.add(new TractionZone(6, ((49 * Math.PI ) / 36), ((5 * Math.PI) / 4))); //Back Straight to Left Corner 245 degrees to 225
        allSegments.add(new TractionZone(7, ((7 * Math.PI ) / 4), ((59 * Math.PI) / 36))); //Right Corner to Back Straight 315 degrees to 295
        allSegments.add(new TractionZone(8, ((13 * Math.PI ) / 36), ((Math.PI) / 4))); //Front Straight to Right Corner 65 degrees to 45
    }

    public TrackSegment getSegmentAtAngle(double angle) {
        for(TrackSegment segment : allSegments) {
            if(segment.containsAngle(angle)) {
                return segment;
            }
        }

        return allSegments.get(0);
    }

    public List<TrackSegment> getAllSegments() { return this.allSegments;}
    public String getTrackName() { return this.trackName;}
    public List<Sector> getSectorList() { return this.sectorList;}

    //Length will come from the sum of the lengths of all Sectors
    public double getTotalLength() {
        double length = 0;


        return length;
    }
    
}
