import javafx.util.Duration;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RaceController {

    @FXML private Circle LightOne_Circle;
    @FXML private Circle LightTwo_Circle;
    @FXML private Circle LightThree_Circle;
    @FXML private Circle LightFour_Circle;
    @FXML private Circle LightFive_Circle;

    /*
        countdownLights goes:
        0-4 = all lights lit (one per LIGHTS_INTERVAL)
        5 = lights out, race begins
    */
    private int countdownLights = 0;

    //800 ms
    private static final int LIGHTS_INTERVAL = 800;

    private Track track;
    private Race race;
    private List<Sector> sectorList;

    //There will be 3 AIControllers
    private AIController[] AIController;

    private final Random rand = new Random();

    private static final int NUM_CARS = 4;

    Car car1 = generateCars();
    Car car2 = generateCars();
    Car car3 = generateCars();
    Car car4 = generateCars();

    Car[] cars = {car1, car2, car3, car4};

    public RaceController() {
        track = new Track("Oval", sectorList);
    }

    @FXML
    private void initialize() {
        resetLights();
        
    }

    @FXML
    private void StartButton_clicked() {
        startLightSequence();

    }

    @FXML
    private void ResetButton_clicked() {

        for(Car car : cars) {
            System.out.println(car.toString());
        }
    }

    public void startLightSequence() {
        Circle[] lights = {LightOne_Circle, LightTwo_Circle, LightThree_Circle, LightFour_Circle, LightFive_Circle};
        Timeline timeline = new Timeline();
        countdownLights = 0;

        for(int i = 0; i < lights.length; i++) {
            int lightIndex = i;
            KeyFrame turnOn = new KeyFrame(
                Duration.millis(LIGHTS_INTERVAL * (i + 1)),
                e -> {
                    lights[lightIndex].setFill(Color.RED);
                    countdownLights++;
                }
            );
            timeline.getKeyFrames().add(turnOn);
        }

        //In real racing, all 5 lights light up. Then, after a random amount of time
        //all lights go out at once. This will randomize the start between 0.2s and 1.7s.
        int randomDelay = rand.nextInt(1500) + 200;

        KeyFrame turnOff = new KeyFrame(
            Duration.millis((LIGHTS_INTERVAL * 5) + randomDelay),
            e -> {
                resetLights();
                countdownLights = 5;
            }
        );
        timeline.getKeyFrames().add(turnOff);

        timeline.play();
    }

    private void resetLights() {
        Circle[] lights = {LightOne_Circle, LightTwo_Circle, LightThree_Circle, LightFour_Circle, LightFive_Circle};
        for(Circle light : lights) {
            light.setFill(Color.DARKGREY);
        }
    }

    private Car generateCars() {
        int engineRating = createRandomPerformanceRating();
        Engine engine = new Engine(engineRating);
        int tireRating = createRandomPerformanceRating();
        Tire tire = new Tire(tireRating);
        int aeroRating = createRandomPerformanceRating();
        Aero aero = new Aero(aeroRating);

        //Example version. 
        //We can have a list of names and numbers to randomly choose from.
        Driver MaxVerstappen = new Driver("Max", 1);

        return new Car(engine, tire, aero, Color.RED, MaxVerstappen);
    }

    private int createRandomPerformanceRating() {
        int rating = rand.nextInt(21) + 60;
        return rating;
    }


    
}
