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

    public Track(String name, List<Sector> sectors) {
        this.trackName = name;
        this.sectorList = sectors;
    }

    public String getTrackName() { return this.trackName;}
    public List<Sector> getSectorList() { return this.sectorList;}

    //Length will come from the sum of the lengths of all Sectors
    public double getTotalLength() {
        double length = 0;


        return length;
    }
    
}
