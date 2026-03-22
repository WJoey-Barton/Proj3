import java.util.List;

public class Track {

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
