//Joey Barton

/*
The Car class represents an individual vehicle within the simulation.
The Car moves around the track using angular logic and calculates its speed
based on mechanical components and track conditions.
*/

import java.util.List;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Car    {

    //Lives here for now. Definitely should live in Race.
    private static final int TOTAL_LAPS = 20;
    private final List<Integer> pathTaken = new ArrayList<>();
    private double totalRaceTime = 0.0;
    private double currentLapTime = 0.0;
    private double fastestLapTime = Double.MAX_VALUE;
    private int finishingPosition = 0;
    //Movement and Positioning
    double angle;
    double speed;
    double MAX_SPEED = 2.1;
    double BASE_SPEED = 1.0;
    Color color;
    double laneOffset;
    boolean isPlayable;

    // Speed modifiers / Performance Components
    private final Engine engine;
    private final Tire tire;
    private final Aero aero;

    // Identity
    private final int carNumber;
    private final Driver driver;
    private int currentSectorID = 0;
    private TrackSegment currentSegment;

    //Holds the start angle for referencing when a race is complete
    private final double startAngle;
    private double previousAngle;
    private int lapsRemaining;
    private boolean isFinished = false;

    public Car(double startAngle, double offset, Color color, Engine engine, Tire tire, Aero aero, int carNum, Driver driver) {
        this.isPlayable = false;
        this.color = color;
        this.angle = startAngle;
        this.laneOffset = offset;
        this.engine = engine;
        this.tire = tire;
        this.aero = aero;
        this.carNumber = carNum;
        this.driver = driver;

        this.startAngle = startAngle;
        this.previousAngle = startAngle;
        this.lapsRemaining = TOTAL_LAPS;

        this.speed = BASE_SPEED;
    }

    //Initializes the car's starting segment and calculates initial speed.
    public void initOnTrack(Track track) {
        this.currentSegment = track.getSegmentAtAngle(this.angle);
        calculateCarSpeed();
        this.pathTaken.add(this.currentSectorID);
    }

    //Flags this car as being controlled by the user
    public void setPlayer() {
        this.isPlayable = true;
    }

    //Returns the calculated X coordinate
    //based on track radius and lane offset.
    public double getX() {
        return Track.CX + (Track.RX + laneOffset) * Math.cos(angle);
    }

    //Returns the calculated Y coordinate
    //based on track radius and lane offset.
    public double getY() {
        return Track.CY + (Track.RY + laneOffset) * Math.sin(angle);
    }

    //Updates the car's position and race state for the current frame.
    public void update(double deltaTime, Track track) {

        //Brings the car to a slow stop
        if(isFinished) {
            speed = Math.max(0, speed - 0.3 * deltaTime);
            angle -= speed * deltaTime;
            wrapAngle();
            return;
        }

        previousAngle = angle;
        angle -= speed * deltaTime;
        wrapAngle();

        if (!isFinished) {
            totalRaceTime += deltaTime;
            currentLapTime += deltaTime;
        }

        //Checks for change in TrackSegment to recalculate speed
        TrackSegment newSegment = track.getSegmentAtAngle(angle);
        if(newSegment.getSegmentID() != currentSegment.getSegmentID()) {
            currentSegment = newSegment;
            calculateCarSpeed();
        }

        checkLapCompletion();
    }

    //Ensures the angle stays within the range of 0 to 2PI
    private void wrapAngle() {
        if(angle < 0 ) {
            angle += 2 * Math.PI;
        }
        if(angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
    }

    /*
    This method takes the Engine, Tire, and Aero rating and, based on which segment of the track the car is currently on
    will have a unique speed.
    */
    public void calculateCarSpeed() {
        double componentRating = currentSegment.calculateSpeed(engine, tire, aero);

        //double average = ((engine.getRating() + tire.getRating() + aero.getRating()) / 3);

        double normalized = (componentRating - PerformanceComponent.MIN_RATING) / (PerformanceComponent.MAX_RATING - PerformanceComponent.MIN_RATING);
        this.speed = BASE_SPEED + normalized + (0.5 * normalized);

        System.out.println("Base Speed " + BASE_SPEED + " normalized: " + normalized + " Component Rating: " + componentRating);

    }

    //Checks if the car has entered a new sector.
    public boolean checkSector(List<Sector> sectors) {

        for (Sector sector : sectors) {
            if (angle >= sector.getStartAngle() && angle < sector.getEndAngle()) {
                if (this.currentSectorID != sector.getSectorID()) {
                    this.currentSectorID = sector.getSectorID();
                    this.pathTaken.add(sector.getSectorID());
                    return true;
                }
            }
        }
        return false;
    }

    //Determines if the car has crossed the start/finish line by comparing
    //the current angle and previous angle relative to the startAngle.
    private void checkLapCompletion() {

        //The angle is wrapped when 2PI (the previous angle) is reset to 0 (the current)
        boolean wrapped = previousAngle > angle;
        boolean crossed;

        if(wrapped) {

            crossed = startAngle >= previousAngle || startAngle <= angle;
        } else {
            crossed = previousAngle < startAngle && angle >= startAngle;
        }
        if(crossed) {
            if (currentLapTime < fastestLapTime) {
                fastestLapTime = currentLapTime;
            }

        currentLapTime = 0.0;
        lapsRemaining--;
        System.out.println("Lap completed. Laps remaining: " + lapsRemaining);

        if(lapsRemaining <= 0) {
            isFinished = true;
        }
    }
        }


    //Getters
    public Color getColor() { return this.color;}
    public int getCurrentSectorID() { return this.currentSectorID;}
    public int getLapsRemaining() { return this.lapsRemaining;}
    public int getCarNumber() { return this.carNumber;}
    public double getAngle() { return this.angle;}
    public boolean isFinished() { return this.isFinished;}
    public double getTotalRaceTime() { return this.totalRaceTime; }
    public int getFinishingPosition() { return this.finishingPosition; }
    public void setFinishingPosition(int finishingPosition) { this.finishingPosition = finishingPosition; }
    public List<Integer> getPathTaken() { return this.pathTaken; }
    public double getFastestLapTime() { return this.fastestLapTime; }
    /*

    public Driver getDriver() { return this.driver;}
    public Engine getEngine() { return this.engine;}
    public Tire getTire() { return this.tire;}
    public Aero getAero() { return this.aero;}
    public double getCurrentSpeed() { return this.currentSpeed;}
    public boolean isPlayerCar() { return this.isPlayerCar;}
    */

    @Override
    public String toString() {
        //return "" + this.carNumber;
        return "Total Speed: " + speed + " Engine: " + engine.getRating() + " Tire: " + tire.getRating() + " Aero: " + aero.getRating();
    }
}
