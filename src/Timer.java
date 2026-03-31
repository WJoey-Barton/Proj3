// Cesar Pimentel

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer {

    long lastNano = 0;
    Race race;
    RaceView raceView;
    RaceController raceController;

    public Timer(Race race, RaceView raceView, RaceController raceController) {
        this.race = race;
        this.raceView = raceView;
        this.raceController = raceController;
    }

    @Override
    public void handle(long now) {
        if (lastNano == 0) {
            lastNano = now;
            return;
        }

        double deltaTime = (now - lastNano) / 1_000_000_000.0;
        lastNano = now;

        for (Car car : race.getCars()) {
            boolean wasFinished = car.isFinished();

            car.update(deltaTime, race.getTrack());

            if (!wasFinished && car.isFinished()) {
                race.recordFinish(car);
            }

            car.checkSector(race.getTrack().getSectorList());
        }
        raceController.updateUILabels();

        raceView.render(race);

        if (race.isRaceFinished()) {
            stop();
        }
    }
}