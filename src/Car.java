import java.awt.Color;

public class Car {

    public static final double BASE_SPEED = 5.0;

    private final Engine engine;
    private final Tire tire;
    private final Aero aero;

    private final int carNumber;
    private final Color color;
    private final Driver driver;

    private double currentSpeed;

    private double currentX;
    private double currentY;

    private boolean isPlayerCar;
    private boolean isFinished;

    public Car(Engine engine, Tire tire, Aero aero, int carNumber, Color color, Driver driver) {
        this.engine = engine;
        this.tire = tire;
        this.aero = aero;
        this.carNumber = carNumber;
        this.color = color;
        this.driver = driver;
    }

    //Getters
    public int getCarNumber() { return this.carNumber;}
    public Color getColor() { return this.color;}
    public Driver getDriver() { return this.driver;}
    public Engine getEngine() { return this.engine;}
    public Tire getTire() { return this.tire;}
    public Aero getAero() { return this.aero;}
    public double getCurrentX() { return this.currentX;}
    public double getCurrentY() { return this.currentY;}
    public double getCurrentSpeed() { return this.currentSpeed;}
    public boolean isPlayerCar() { return this.isPlayerCar;}
    public boolean isFinished() { return this.isFinished;}

    
}
