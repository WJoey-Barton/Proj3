import java.util.List;

import javafx.scene.paint.Color;

public class Car {

    //Lives here for now. Definitely should live in Race.
    private static final int TOTAL_LAPS = 2;

    double angle;
    double speed;
    double MAX_SPEED = 2.1;
    double BASE_SPEED = 1;
    Color color;
    double laneOffset;
    boolean isPlayable;

    // Speed modifiers
    private final Engine engine;
    private final Tire tire;
    private final Aero aero;

    // Misc
    private final int carNumber;
    private final Driver driver;

    private int currentSectorID = 0;

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

        this.speed = calculateCarSpeed();
    }
    public void setPlayer() {
        this.isPlayable = true;
    }

    public double getX() {
        return Track.CX + (Track.RX + laneOffset) * Math.cos(angle);
    }
    public double getY() {
        return Track.CY + (Track.RY + laneOffset) * Math.sin(angle);
    }
    public void update(double deltaTime) {

        //Brings the car to a slow stop
        if(isFinished) {
            speed = Math.max(0, speed - 1.3 * deltaTime);
            angle += speed * deltaTime;
            return;
        }

        previousAngle = angle;
        angle += speed * deltaTime;

        if(angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }

        checkLapCompletion();

    }

    /*
    This method takes the Engine, Tire, and Aero rating and finds the average and adds it to the BASE_SPEED.
    It also finds the highest rating of the three, multiplies it by 0.5, and adds that to the totalSpeed.
    This will reward a car that has a high, single attribute.
    */
    private double calculateCarSpeed() {
        this.speed = BASE_SPEED + ((engine.getRating() + tire.getRating() + aero.getRating()) / 3) + 
        (0.5 * Math.max(engine.getRating(), Math.max(tire.getRating(), aero.getRating())));
        return this.speed;
    }


    public boolean checkSector(List<Sector> sectors) {
        
        for(Sector sector : sectors) {
            if(angle >= sector.getStartAngle() && angle < sector.getEndAngle()) {
                if(this.currentSectorID != sector.getSectorID()) {
                    this.currentSectorID = sector.getSectorID();
                    return true;
                }
            }
        }
        return false;
    }

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

    /*
    public int getCarNumber() { return this.carNumber;}
    public Driver getDriver() { return this.driver;}
    public Engine getEngine() { return this.engine;}
    public Tire getTire() { return this.tire;}
    public Aero getAero() { return this.aero;}
    public double getCurrentSpeed() { return this.currentSpeed;}
    public boolean isPlayerCar() { return this.isPlayerCar;}
    public boolean isFinished() { return this.isFinished;}
    */

    @Override
    public String toString() {
        return "" + this.carNumber;
        //return "Total Speed: " + speed + " Engine: " + engine.getRating() + " Tire: " + tire.getRating() + " Aero: " + aero.getRating();
    }
    
    
}
