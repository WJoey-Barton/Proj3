import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RaceController {

    @FXML private Canvas canvas;

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

    private GraphicsContext graphicsContext;
    private List<Car> cars;

    private List<Sector> sectorList;

    private Track track;
    private Race race;
    

    private final Random rand = new Random();

    private static final int NUM_CARS = 4;

    
    public RaceController() {
        
    }
    

    @FXML
    private void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        resetLights();

        buildRace();
        
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

    private void buildRace() {
        cars = new ArrayList<>();
        cars.add(generateCars(0, 0,Color.YELLOW));
        cars.add(generateCars(0, 20,Color.BLUE));
        cars.add(generateCars(0, 40,Color.RED));

        sectorList = new ArrayList<>();
        sectorList.add(new Sector(0, "Sector_1", 0, Math.PI / 2));
        sectorList.add(new Sector(1, "Sector_2", Math.PI / 2, Math.PI));
        sectorList.add(new Sector(2, "Sector_3", Math.PI, 3 * Math.PI / 2));
        sectorList.add(new Sector(3, "Sector_4", 3 * Math.PI / 2, 2 * Math.PI));

        track = new Track("Oval", sectorList);
        race = new Race(track, cars);
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
                startRace();
            }
        );
        timeline.getKeyFrames().add(turnOff);

        timeline.play();
    }

    private void startRace() {
        Timer timer = new Timer(cars, graphicsContext);
        timer.start();
    }

    private void resetLights() {
        Circle[] lights = {LightOne_Circle, LightTwo_Circle, LightThree_Circle, LightFour_Circle, LightFive_Circle};
        for(Circle light : lights) {
            light.setFill(Color.DARKGREY);
        }
    }

    private Car generateCars(double startAngle, double offset, Color color) {
        Engine engine = new Engine(createRandomPerformanceRating());
        Tire tire = new Tire(createRandomPerformanceRating());
        Aero aero = new Aero(createRandomPerformanceRating());

        //Example version. 
        //We can have a list of names and numbers to randomly choose from.
        Driver MaxVerstappen = new Driver("Max", 1);

        return new Car(startAngle, offset, color, engine, tire, aero, MaxVerstappen.getCarNumber(), MaxVerstappen);
    }

    private double createRandomPerformanceRating() {
        double rating = rand.nextInt(21) + 60 ;
        return rating / 100;
    }

    


    
}
