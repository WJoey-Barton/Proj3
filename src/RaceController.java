//Joey Barton & Cesar Pimentel

/*
Controller class for the Racing UI
Manages the interaction between the user, the race simulation data,
and the JavaFX rendering surface.
*/

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RaceController {

    @FXML private Canvas canvas;

    //F1 style start lights
    @FXML private Circle LightOne_Circle;
    @FXML private Circle LightTwo_Circle;
    @FXML private Circle LightThree_Circle;
    @FXML private Circle LightFour_Circle;
    @FXML private Circle LightFive_Circle;

    //All labels in a VBox
    //These can feature the quickest laps
    //or whatever we want for the user to see.
    @FXML private Label P1_Label;
    @FXML private Label P2_Label;
    @FXML private Label P3_Label;
    @FXML private Label P4_Label;


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

    private RaceView raceView;

    private List<Sector> sectorList;

    private Track track;
    private Race race;
    

    private final Random rand = new Random();

    private static final int NUM_CARS = 4;

    private List<String> driverNames = new ArrayList<>();

    
    public RaceController() {
        
    }
    

    //Called by JavaFX
    //Sets up the initial race environment and performs the first render.
    @FXML
    private void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        resetLights();
        buildRace();
        populateUILabels();

        raceView = new RaceView(graphicsContext);
        raceView.render(race);
        
    }

    //Start button
    @FXML
    private void StartButton_clicked() {
        startLightSequence();

    }

    //Reset button
    //Currently doesn't reset 
    @FXML
    private void ResetButton_clicked() {
        resetLights();
        buildRace();
        populateUILabels();

        raceView = new RaceView(graphicsContext);
        raceView.render(race);
    }

    //Populates the track with sectors and generates cars.
    private void buildRace() {

        driverNames.clear();
        driverNames.addAll(List.of("Max Verstappen", "Fernando Alonso", "Sebastian Vettel", "Lando Norris", 
                                "Lewis Hamilton", "Gabriel Bortoleto", "Charles Leclerc", "Arvid Lindblad", 
                                "Valtteri Bottas", "Carlos Sainz", "Oscar Piastri", "Isack Hadjar", "Alex Albon",
                                "Pierre Gasly", "Kimi Antonelli", "Kimi Raikkonen"
                            ));

        sectorList = new ArrayList<>();
        sectorList.add(new Sector(0, "Sector_1", 0, Math.PI / 2));
        sectorList.add(new Sector(1, "Sector_2", Math.PI / 2, Math.PI));
        sectorList.add(new Sector(2, "Sector_3", Math.PI, 3 * Math.PI / 2));
        sectorList.add(new Sector(3, "Sector_4", 3 * Math.PI / 2, 2 * Math.PI));

        cars = new ArrayList<>();
        cars.add(generateCars(sectorList.get(0).getStartAngle(), 15, Color.YELLOW));
        cars.add(generateCars(sectorList.get(1).getStartAngle(), 20, Color.BLUE));
        cars.add(generateCars(sectorList.get(2).getStartAngle(), 30, Color.RED));
        cars.add(generateCars(sectorList.get(3).getStartAngle(), 20, Color.SILVER));

        track = new Track("Oval", sectorList);
        race = new Race(track, cars, 10);
    }
     
    //Handles the visual countdown sequence.
    //Lights up five circles sequentially, then pauses for a random
    //interval before turning them off and starting the race.
    public void startLightSequence() {
        Circle[] lights = {LightOne_Circle, LightTwo_Circle, LightThree_Circle, LightFour_Circle, LightFive_Circle};
        Timeline timeline = new Timeline();
        countdownLights = 0;

        //Sequence for turning lights ON
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

        //KeyFrame for "Lights Out" and Race start
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

    //Transitions the race state to active and starts the game timer.
    private void startRace() {

        race.initCarsOnTrack();

        Timer timer = new Timer(race, raceView, this);
        timer.start();

    }

    //Resets all start light circles to their off state
    private void resetLights() {
        Circle[] lights = {LightOne_Circle, LightTwo_Circle, LightThree_Circle, LightFour_Circle, LightFive_Circle};
        for(Circle light : lights) {
            light.setFill(Color.DARKGREY);
        }
    }

    //Creates a car with randomized performance component values.
    private Car generateCars(double startAngle, double offset, Color color) {
        Engine engine = new Engine(createRandomPerformanceRating());
        Tire tire = new Tire(createRandomPerformanceRating());
        Aero aero = new Aero(createRandomPerformanceRating());

        //Example version. 
        //We can have a list of names and numbers to randomly choose from.
        Driver driver = new Driver(getDriverName(), 1);

        return new Car(startAngle, offset, color, engine, tire, aero, rand.nextInt(99) + 1, driver);
    }

    //Generates a random rating between 0.60 and 0.80
    private double createRandomPerformanceRating() {
        double rating = rand.nextInt(21) + 60 ;
        return rating / 100;
    }

    //Updates the UI labels
    //@TODO setup useful information for these
    private void populateUILabels() {
        P1_Label.setText(cars.get(0).getDriver().getName() + " | #" + cars.get(0).getCarNumber());
        P2_Label.setText(cars.get(1).getDriver().getName() + " | #" + cars.get(1).getCarNumber());
        P3_Label.setText(cars.get(2).getDriver().getName() + " | #" + cars.get(2).getCarNumber());
        P4_Label.setText(cars.get(3).getDriver().getName() + " | #" + cars.get(3).getCarNumber());
    }

    // Updates the time it's been so far and how many laps are left
    // Called every frame by Timer to keep race labels in sync with current car state
    protected void updateUILabels() {
        P1_Label.setText(cars.get(0).getDriver().getName() + " | #" + cars.get(0).getCarNumber()
                + "\nLap: " + (cars.get(0).getTotalLaps() - cars.get(0).getLapsRemaining()) + "/" + cars.get(0).getTotalLaps()
                + "\nTime: " + String.format("%.1f", cars.get(0).getTotalRaceTime()) + "s");

        P2_Label.setText(cars.get(1).getDriver().getName() + " | #" + cars.get(1).getCarNumber()
                + "\nLap: " + (cars.get(1).getTotalLaps() - cars.get(1).getLapsRemaining()) + "/" + cars.get(1).getTotalLaps()
                + "\nTime: " + String.format("%.1f", cars.get(1).getTotalRaceTime()) + "s");

        P3_Label.setText(cars.get(2).getDriver().getName() + " | #" + cars.get(2).getCarNumber()
                + "\nLap: " + (cars.get(2).getTotalLaps() - cars.get(2).getLapsRemaining()) + "/" + cars.get(2).getTotalLaps()
                + "\nTime: " + String.format("%.1f", cars.get(2).getTotalRaceTime()) + "s");

        P4_Label.setText(cars.get(3).getDriver().getName() + " | #" + cars.get(3).getCarNumber()
                + "\nLap: " + (cars.get(3).getTotalLaps() - cars.get(3).getLapsRemaining()) + "/" + cars.get(3).getTotalLaps()
                + "\nTime: " + String.format("%.1f", cars.get(3).getTotalRaceTime()) + "s");
    }

    private String getDriverName() {
        int selection = rand.nextInt(12);
        return driverNames.remove(selection);
        
    }
    


    
}
