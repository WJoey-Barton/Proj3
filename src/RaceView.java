//Joey Barton

/*
Handles all visual rendering for the race simulation.
*/

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


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
    public void render(Race race) {
        clearCanvas();
        drawTrack();

        for(Car car : race.getCars()) {
            drawCar(car);
        }
    }

    //Clears the canvas for the next frame of animation
    private void clearCanvas() {
        graphicsContext.setFill(Color.color(0.1, 0.1,0.1));
        graphicsContext.clearRect(0, 0, 640, 480);

    }

    //Draws the track layout using a series of overlapping oval "bands".
    //Layers are drawn from largest (outside) to smallest (inside)
    private void drawTrack() {
        double totalOuter = TRACK_WIDTH + CURB_WIDTH + GRASS_WIDTH;

        //Outside Grass
        drawOvalBand(Track.RX + totalOuter, Track.RY + totalOuter, Color.FORESTGREEN);

        //Orange Outside Curb
        drawOvalBand(Track.RX + TRACK_WIDTH + CURB_WIDTH, Track.RY + TRACK_WIDTH + CURB_WIDTH, Color.DARKORANGE);

        //On Track
        drawOvalBand(Track.RX + TRACK_WIDTH, Track.RY + TRACK_WIDTH, Color.DARKGRAY);

        //Inside Orange Curb
        drawOvalBand(Track.RX + CURB_WIDTH, Track.RY + CURB_WIDTH, Color.DARKORANGE);
        
        //Inside Grass
        drawOvalBand(Track.RX, Track.RY, Color.FORESTGREEN);
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
