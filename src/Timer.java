import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class Timer extends AnimationTimer {

    long lastNano = 0;
    Race race;
    GraphicsContext graphicsContext;

    public Timer(Race race, GraphicsContext graphicsContext) {
        this.race = race;
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void handle(long now) {
        if(lastNano == 0) {
            lastNano = now;
            return;
        }

        double deltaTime = (now - lastNano) / 1_000_000_000.0;
        lastNano = now;

        graphicsContext.clearRect(0, 0, 640, 480);
        for(Car car : race.getCars()) {
            car.update(deltaTime);

            if(car.checkSector(race.getTrack().getSectorList())) {
                System.out.println("Car " + car + " entered Sector " + car.getCurrentSectorID());
            }

            graphicsContext.setFill(car.color);
            graphicsContext.fillOval(car.getX() - 10, car.getY() - 10, 20,20);
            //System.out.println("Car on X: "+ car.getX() + "\nCar on Y: " + car.getY());
        }
    }
}
