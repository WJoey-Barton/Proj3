import java.util.List;

public class Race {
    /*
    Track size, CX is the center x, and CY is the center y.
    This is to know where to draw the oval. Then RX is the radius
    along the x axis and RY is the radius along the Y. This is to know
    how far to stretch the circle along each axis.
     */
    static final double CX = 320, CY = 240;
    static final double RX = 200, RY = 120;

    //I don't think this is going to work the way I want.
    //public enum RaceStatus {STARTED, FINISHED};
    private final List<Car> carList;
    private final Track track;
    //private final RaceStatus raceStatus;
    //private final Timer timer;

    private boolean raceStarted = false;
    private boolean raceFinished = false;

    public Race(Track track, List<Car> cars) {
        this.track = track;
        this.carList = cars;
        //this.timer = new Timer();
    }

    public Track getTrack() { return this.track;}
    public List<Car> getCars() { return this.carList;}
    //public Timer getTimer() { return this.timer;}
    public boolean isRaceStarted() { return this.raceStarted;}
    public boolean isRaceFinished() { return this.raceFinished;}
    
}
