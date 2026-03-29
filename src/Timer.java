import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class Timer extends AnimationTimer {

    long lastNano = 0;
    Race race;
    RaceView raceView;

    public Timer(Race race, RaceView raceView) {
        this.race = race;
        this.raceView = raceView;
    }

    @Override
    public void handle(long now) {
        if(lastNano == 0) {
            lastNano = now;
            return;
        }

        double deltaTime = (now - lastNano) / 1_000_000_000.0;
        lastNano = now;

        for(Car car : race.getCars()) {
            car.update(deltaTime, race.getTrack());

            // if(car.checkSector(race.getTrack().getSectorList())) {
            //     System.out.println("Car " + car + " entered Sector " + car.getCurrentSectorID());
            // }

            //System.out.println("Car on X: "+ car.getX() + "\nCar on Y: " + car.getY());
        }

        raceView.render(race);
    }
}
