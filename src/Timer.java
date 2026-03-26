import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Timer extends AnimationTimer {

    long lastNano = 0;
    List<Car> carList;
    GraphicsContext graphicsContext;

    public Timer(List<Car> carList, GraphicsContext graphicsContext) {
        this.carList = carList;
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
        for(Car car : carList) {
            car.update(deltaTime);

            graphicsContext.setFill(car.color);
            graphicsContext.fillOval(car.getX() - 10, car.getY() - 10, 20,20);
            System.out.println("Car on X: "+ car.getX() + "\nCar on Y: " + car.getY());
        }
    }
}
