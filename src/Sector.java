//Joey Barton

/*
Represents a major portion of the Track.
Sectors divide the track into segments (quadrants in our game).
Used for tracking lap progress, sector times, and car positioning.
*/

public class Sector {
    
    //0-3
    //0 = A->B | 1 = B->C | 2 = C->D | 3 = D->A
    private final int sectorID;
    private final String name;

    //Angular position in radians.
    private final double startAngle;

    //Angular position in radians.
    private final double endAngle;
    
    public Sector(int ID, String name, double startAngle, double endAngle) {
        this.sectorID = ID;
        this.name = name;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    //Getters
    public int getSectorID() { return this.sectorID;}
    public String getName() { return this.name;}
    public double getStartAngle() { return this.startAngle;}
    public double getEndAngle() { return this.endAngle;}

    //Length will come from the sum of the length of all TrackSegments in this Sector
    public double getSectorLength() {
        double length = 0;

        return length;
    }
}
