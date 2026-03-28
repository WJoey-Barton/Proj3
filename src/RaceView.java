import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

        //Translate will 
        graphicsContext.translate(x, y);

        //Rotate will ensure that the car is facing the correct direction,
        //Along the tangent line
        graphicsContext.rotate(Math.toDegrees(carAngleDer));

        graphicsContext.setFill(car.color);

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
