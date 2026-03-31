//Joey Barton

/*
Handles all visual rendering for the race simulation.
*/

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class RaceView {

    private final GraphicsContext graphicsContext;

    //Rendering constants
    private static final double CAR_WIDTH = 20;
    private static final double CAR_HEIGHT = 12;
    private static final double TRACK_WIDTH = 50;
    private static final double CURB_WIDTH = 8;
    private static final double GRASS_WIDTH = 20;

    public RaceView(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    //Clears the frame and redraws the entire race state.
    //Added to create results screen as well.
    public void render(Race race) {
        clearCanvas();

        if (race.isRaceFinished()) {
            drawResults(race);
            return;
        }

        drawTrack();

        for (Car car : race.getCars()) {
            drawCar(car);
        }
    }




    private void drawResults(Race race) {
        graphicsContext.setFill(Color.web("#0d0d12"));
        graphicsContext.fillRect(0, 0, 840, 594);

        graphicsContext.setFill(Color.web("#ff073a"));
        graphicsContext.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        graphicsContext.fillText("Race Results", 300, 40);

        if (!race.getFinishOrder().isEmpty()) {
            Car winner = race.getFinishOrder().get(0);

            graphicsContext.setFill(Color.web("#171721"));
            graphicsContext.setStroke(Color.web("#2a2a3b"));
            graphicsContext.setLineWidth(1);

            graphicsContext.setFill(Color.web("#e0e0e0"));
            graphicsContext.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
            graphicsContext.fillText(
                    "Winner: Car #" + winner.getCarNumber() + " - " + winner.getDriver().getName(),
                    220,
                    75
            );
        }

        int y = 115;

        for (Car car : race.getFinishOrder()) {

            graphicsContext.setFill(Color.web("#171721"));
            graphicsContext.setStroke(Color.web("#2a2a3b"));
            graphicsContext.setLineWidth(1);

            graphicsContext.setFill(Color.web("#ff073a"));
            graphicsContext.setFont(Font.font("System", FontWeight.BOLD, 16));
            graphicsContext.fillText(
                    car.getFinishingPosition() + ". Car #" + car.getCarNumber()
                            + " - " + car.getDriver().getName(),
                    60,
                    y
            );

            graphicsContext.setStroke(Color.web("#2a2a3b"));
            graphicsContext.setLineWidth(1);
            graphicsContext.setFont(Font.font("System", FontWeight.NORMAL, 11));

            int pathLines = drawWrappedText(
                    "Path taken: " + formatPath(car.getPathTaken()),
                    80,
                    y + 20,
                    720,
                    14
            );

            graphicsContext.setFill(Color.web("#a3a3c2"));
            graphicsContext.setFont(Font.font("System", FontWeight.NORMAL, 14));

            double infoY = y + 22 + pathLines * 18;

            graphicsContext.fillText(
                    "Avg speed: " + String.format("%.2f px/s", car.getAverageSpeed()),
                    80,
                    infoY + 18
            );

            graphicsContext.fillText(
                    "Total race time: " + String.format("%.2f s", car.getTotalRaceTime()),
                    80,
                    infoY + 36
            );

            graphicsContext.fillText(
                    "Fastest lap: " + String.format("%.2f s", car.getFastestLapTime()),
                    80,
                    infoY + 54
            );

        y = (int) (infoY + 70);
        }
    }


    private int drawWrappedText(String text, double x, double y, double maxWidth, double lineHeight) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int linesDrawn = 0;

        for (String word : words) {
            String testLine = line.isEmpty() ? word : line + " " + word;

            Text helper = new Text(testLine);
            helper.setFont(graphicsContext.getFont());

            if (helper.getLayoutBounds().getWidth() > maxWidth) {
                graphicsContext.fillText(line.toString(), x, y + linesDrawn * lineHeight);
                linesDrawn++;
                line = new StringBuilder(word);
            } else {
                line = new StringBuilder(testLine);
            }
        }

    if (!line.isEmpty()) {
        graphicsContext.fillText(line.toString(), x, y + linesDrawn * lineHeight);
        linesDrawn++;
    }

    return linesDrawn;
}

        private String formatPath(java.util.List<Integer> pathTaken) {
            if (pathTaken.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < pathTaken.size(); i++) {
            builder.append(pathTaken.get(i) + 1);

            if (i < pathTaken.size() - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }
    //Clears the canvas for the next frame of animation
    private void clearCanvas() {
        graphicsContext.setFill(Color.color(0.1, 0.1,0.1));
        graphicsContext.clearRect(0, 0, 640, 594);

    }

    //Draws the track layout using a series of overlapping oval "bands".
    //Layers are drawn from largest (outside) to smallest (inside)
    private void drawTrack() {
        double totalOuter = TRACK_WIDTH + CURB_WIDTH + GRASS_WIDTH;

        //Outside Grass
        drawOvalBand(Track.RX + totalOuter, Track.RY + totalOuter, Color.web("#0f4c5c"));

        //Orange Outside Curb
        drawOvalBand(Track.RX + TRACK_WIDTH + CURB_WIDTH, Track.RY + TRACK_WIDTH + CURB_WIDTH, Color.DARKORANGE);

        //On Track
        drawOvalBand(Track.RX + TRACK_WIDTH, Track.RY + TRACK_WIDTH, Color.DARKGRAY);

        //Inside Orange Curb
        drawOvalBand(Track.RX + CURB_WIDTH, Track.RY + CURB_WIDTH, Color.DARKORANGE);
        
        //Inside Grass
        drawOvalBand(Track.RX, Track.RY, Color.web("#0f4c5c"));
    }

    //Helper method to draw a filled oval centered on the track's center.
    private void drawOvalBand(double rx, double ry, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillOval(
            Track.CX - rx, 
            Track.CY - ry, 
            rx * 2, 
            ry * 2);
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
}
