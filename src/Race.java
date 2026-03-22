import java.util.List;

public class Race {

    //I don't think this is going to work the way I want.
    //public enum RaceStatus {STARTED, FINISHED};

    private final List<Car> carList;
    private final Track track;
    //private final RaceStatus raceStatus;
    private final Timer timer;

    private boolean raceStarted = false;
    private boolean raceFinished = false;

    public Race(Track track, List<Car> cars) {
        this.track = track;
        this.carList = cars;
        this.timer = new Timer();
    }

    public Track getTrack() { return this.track;}
    public List<Car> getCars() { return this.carList;}
    public Timer getTimer() { return this.timer;}
    public boolean isRaceStarted() { return this.raceStarted;}
    public boolean isRaceFinished() { return this.raceFinished;}
    
}
