import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.stream.Collectors;

public class RaceView {

    private final GraphicsContext graphicsContext;
    private static final double CAR_WIDTH = 20;
    private static final double CAR_HEIGHT = 12;

    public RaceView(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void render(Race race) {
        clearCanvas();

        for(Car car : race.getCars()) {
            drawCar(car);
        }

        if (race.isRaceFinished()) {
            drawResultsScreen(race);
        }
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, 640, 480);
    }

    //Cesar calculated the cars path
    //This method calculates which direction the car should point.
    private void drawCar(Car car) {
        double x = car.getX();
        double y = car.getY();

        /*
        We want the car to point in the direction of the track.
        We want the tangent angle of the oval.
        The following equation gives us the derivative vector
        Math.atan2 converts a direction vector into an angle
        */
        double carAngleDer = Math.atan2(Track.RY * Math.cos(car.getAngle()),
                                        -Track.RX * Math.sin(car.getAngle())
                                       );

        graphicsContext.save();

        //Translate will shift the point 0,0 to the car's center.
        //This ensures accurate rotation
        graphicsContext.translate(x, y);

        //Rotate will ensure that the car is facing the correct direction,
        //Along the tangent line
        graphicsContext.rotate(Math.toDegrees(carAngleDer));

        graphicsContext.setFill(car.getColor());

        /*
        We draw the car from the center, so it pivots correctly.
        */
        graphicsContext.fillRect(-CAR_WIDTH / 2, -CAR_HEIGHT / 2, CAR_WIDTH, CAR_HEIGHT);

        //Draws car outline
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(1);
        graphicsContext.strokeRect(-CAR_WIDTH / 2, -CAR_HEIGHT / 2, CAR_WIDTH, CAR_HEIGHT);

        //Draws car number
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font("System", FontWeight.BOLD, 9));
        String num = String.valueOf(car.getCarNumber());
        graphicsContext.fillText(num, -3 * num.length(), 4);

        graphicsContext.restore();
    }

    //Draw end results screen in 4 segments, one for each car
    private void drawResultsScreen(Race race) {
        graphicsContext.save();

        //Dark overlay over the race
        graphicsContext.setFill(new Color(0, 0, 0, 0.88));
        graphicsContext.fillRect(0, 0, 640, 480);

        List<Car> results = race.getFinishOrder();

        //Screen title
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font("System", FontWeight.BOLD, 24));
        graphicsContext.fillText("END RESULTS", 230, 35);

        //Winner declared clearly
        if (!results.isEmpty()) {
            Car winner = results.get(0);

            graphicsContext.setFill(Color.GOLD);
            graphicsContext.setFont(Font.font("System", FontWeight.BOLD, 16));
            graphicsContext.fillText(
                    "WINNER: " + winner.getDriver().getName() + "  #" + winner.getCarNumber(),
                    170,
                    60
            );
        }

        //4 horizontal result boxes
        int startY = 80;
        int boxHeight = 90;

        for (int i = 0; i < results.size(); i++) {
            Car car = results.get(i);
            int y = startY + (i * boxHeight);

            //Segment background
            graphicsContext.setFill(new Color(0.15, 0.15, 0.15, 0.95));
            graphicsContext.fillRect(20, y, 600, 75);

            //Segment outline
            graphicsContext.setStroke(Color.WHITE);
            graphicsContext.strokeRect(20, y, 600, 75);

            //Car color marker
            graphicsContext.setFill(car.getColor());
            graphicsContext.fillRect(30, y + 15, 25, 25);
            graphicsContext.setStroke(Color.WHITE);
            graphicsContext.strokeRect(30, y + 15, 25, 25);

            //Position, driver name, and car number
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setFont(Font.font("System", FontWeight.BOLD, 14));
            graphicsContext.fillText(
                    car.getFinishingPosition() + ". " + car.getDriver().getName() + "  #" + car.getCarNumber(),
                    70,
                    y + 22
            );

            //Path, speed, and total race time
            graphicsContext.setFont(Font.font("System", 12));
            graphicsContext.fillText("Path: " + formatPath(car.getPathTaken()), 70, y + 42);
            graphicsContext.fillText(String.format("Speed: %.2f", car.getSpeed()), 70, y + 60);
            graphicsContext.fillText(String.format("Time: %.2f s", car.getTotalRaceTime()), 250, y + 60);
        }

        graphicsContext.restore();
    }

    //Format sector list into readable path
    private String formatPath(List<Integer> pathTaken) {
        return pathTaken.stream()
                .map(id -> "S" + (id + 1))
                .collect(Collectors.joining(" -> "));
    }
}
