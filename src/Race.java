//Joey Barton

/*
Acts as a data container for a single Race event
Holds the configuration for the race, including the Track being used
and the participating cars.
*/

import java.util.List;
import java.util.ArrayList;

public class Race {

    private final List<Car> carList;
    private final Track track;

    private final int totalLaps;

    private boolean raceStarted = false;
    private boolean raceFinished = false;

    private final List<Car> finishOrder = new ArrayList<>();

    public Race(Track track, List<Car> cars, int totalLaps) {
        this.track = track;
        this.carList = cars;
        this.totalLaps = totalLaps;
    }

    //Called at start of race so each Car gets the lap count.
    public void initCarsOnTrack() {
        for(Car car : carList) {
            car.initOnTrack(track, totalLaps);
        }
    }

    public Track getTrack() { return this.track; }
    public List<Car> getCars() { return this.carList; }
    public int getTotalLaps() { return this.totalLaps;}

    public boolean isRaceStarted() { return this.raceStarted; }
    public boolean isRaceFinished() { return this.raceFinished; }

    public void setRaceStarted(boolean started) {
        this.raceStarted = started;
    }

    public List<Car> getFinishOrder() {
        return finishOrder;
    }

    public void recordFinish(Car car) {
        if (!finishOrder.contains(car)) {
            finishOrder.add(car);
            car.setFinishingPosition(finishOrder.size());
        }

        if (finishOrder.size() == carList.size()) {
            raceFinished = true;
        }
    }
}

